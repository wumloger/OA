package top.wuml.oa.service;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.mapper.EmployeeMapper;
import top.wuml.oa.util.MybatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    public Employee getEmployee(Long employeeId){
      Employee employeevalue =  (Employee) MybatisUtils.executeQuery(sqlSession -> {
            Employee employee = sqlSession.getMapper(EmployeeMapper.class).selectByEId(employeeId);
            return employee;
        });
            return employeevalue;

    }

    public Employee selectLeader(Long employeeId){
        return (Employee) MybatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.selectByEId(employeeId);
            Map<String, Object> params = new HashMap<>();
            Employee leader = null;

            if (employee.getLevel() < 7){
                params.put("level", 7);
                params.put("departmentId",employee.getDepartmentId());
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            }else if(employee.getLevel() == 7){
                params.put("level", 8);
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            }else if(employee.getLevel() == 8){
                leader = employee;
            }
            return leader;
        });
    }
}
