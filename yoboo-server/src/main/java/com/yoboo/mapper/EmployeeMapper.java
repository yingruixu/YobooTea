package com.yoboo.mapper;

import com.github.pagehelper.Page;
import com.yoboo.annotation.AutoFill;
import com.yoboo.dto.EmployeePageQueryDTO;
import com.yoboo.entity.Employee;
import com.yoboo.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * Select employee by username
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * insert employee data
     * @param employee
     *
     */
    @Insert("insert into employee (name,username,password,phone,sex,id_number,create_time,update_time,create_user,update_user,status)" +
            "values " +
    "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * get employee list
     * @param employeePageQueryDTO
     * @return
     */

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Update employee status
     * @param employee
     *
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * Get employee by id
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getEmployeeByID(Long id);
}
