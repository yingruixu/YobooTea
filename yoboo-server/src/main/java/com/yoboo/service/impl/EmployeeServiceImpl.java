package com.yoboo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yoboo.constant.MessageConstant;
import com.yoboo.constant.PasswordConstant;
import com.yoboo.constant.StatusConstant;
import com.yoboo.context.BaseContext;
import com.yoboo.dto.EmployeeDTO;
import com.yoboo.dto.EmployeeLoginDTO;
import com.yoboo.dto.EmployeePageQueryDTO;
import com.yoboo.entity.Employee;
import com.yoboo.exception.AccountLockedException;
import com.yoboo.exception.AccountNotFoundException;
import com.yoboo.exception.PasswordErrorException;
import com.yoboo.mapper.EmployeeMapper;
import com.yoboo.result.PageResult;
import com.yoboo.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //MD5 encode password
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * ADD NEW EMPLOYEE
     * @param employeeDTO
     *
     */
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //Copy Object
        BeanUtils.copyProperties(employeeDTO,employee);

        //Set account state, default 1 which is normal. 0 is lock.
        employee.setStatus(StatusConstant.ENABLE);

        //Set password, default = 123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
//
//        // Set date time
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        // Set creator id and editor id
//        Long empID = BaseContext.getCurrentId();
//        employee.setCreateUser(empID);
//        employee.setUpdateUser(empID);
//        BaseContext.removeCurrentId();

        employeeMapper.insert(employee);
    }
    /**
     * Display list of employee
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //pagenation
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total,records);
    }

    /**
     * Strat or stop employee account
     * @param status,id
     *
     */
    public void startOrStop(Integer status, Long id) {
        //Update employee table set status = ? where id = ?
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);

        employeeMapper.update(employee);
    }

    /**
     * Get employee by id
     * @param id
     * @return
     */
    public Employee getEmployeeByID(Long id) {
        Employee employee = employeeMapper.getEmployeeByID(id);
        employee.setPassword("******");
        return employee;
    }

    /**
     * Edit employee info
     * @param
     *
     */
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        // Set date time
        //employee.setUpdateTime(LocalDateTime.now());
        // Set creator id and editor id
        //Long empID = BaseContext.getCurrentId();
        //employee.setUpdateUser(empID);
        //employeeMapper.update(employee);
    }

}
