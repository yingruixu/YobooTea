package com.yoboo.service;

import com.yoboo.dto.EmployeeDTO;
import com.yoboo.dto.EmployeeLoginDTO;
import com.yoboo.dto.EmployeePageQueryDTO;
import com.yoboo.entity.Employee;
import com.yoboo.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * ADD NEW EMPLOYEE
     * @param employeeDTO
     * @return
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * Display list of employee
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Start or stop employee account
     * @param status,id
     *
     */
    void startOrStop(Integer status, Long id);

    /**
     * Get employee by id
     * @param id
     * @return
     */
    Employee getEmployeeByID(Long id);

    /**
     * Edit employee info
     * @param
     *
     */
    void update(EmployeeDTO employeeDTO);
}
