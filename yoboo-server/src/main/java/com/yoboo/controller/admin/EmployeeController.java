package com.yoboo.controller.admin;

import com.yoboo.constant.JwtClaimsConstant;
import com.yoboo.dto.EmployeeDTO;
import com.yoboo.dto.EmployeeLoginDTO;
import com.yoboo.dto.EmployeePageQueryDTO;
import com.yoboo.entity.Employee;
import com.yoboo.properties.JwtProperties;
import com.yoboo.result.PageResult;
import com.yoboo.result.Result;
import com.yoboo.service.EmployeeService;
import com.yoboo.utils.JwtUtil;
import com.yoboo.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "Employee related ")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "Employee Login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("Employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * ADD EMPLOYEE
     *
     *
     */
    @PostMapping
    @ApiOperation("Add new employee")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * Display list of employee
     *
     *
     */
    @GetMapping("/page")
    @ApiOperation("Display list of employee")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Start or Stop employee account
     *
     *
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start or Stop employee account")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * Get employee by id
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    @ApiOperation("Get employee by id")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeByID(id);
        return Result.success(employee);
    }

    /**
     * Edit employee info
     * @param
     *
     */
    @PutMapping
    @ApiOperation("Edit employee info")
    public Result editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(employeeDTO);
        return Result.success();
    }

}
