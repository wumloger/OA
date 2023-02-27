package top.wuml.oa.service;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.mapper.EmployeeMapper;

public class EmployeeService {
    private EmployeeMapper employeeMapper = new EmployeeMapper();
    public Employee getEmployee(Long employeeId){
        return employeeMapper.selectByEId(employeeId);
    }
}
