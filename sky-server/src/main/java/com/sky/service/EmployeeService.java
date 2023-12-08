package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

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
