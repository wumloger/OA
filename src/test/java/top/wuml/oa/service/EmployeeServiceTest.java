package top.wuml.oa.service;

import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.Employee;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private final  EmployeeService employeeService = new EmployeeService();
    @Test
    void selectLeader(){
        Employee leader = employeeService.selectLeader(4L);
        System.out.println(leader);
    }

}