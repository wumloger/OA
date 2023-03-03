package top.wuml.oa.mapper;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.entity.User;
import top.wuml.oa.util.MybatisUtils;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    public Employee selectByEId(Long employeeId);


    public List<Employee> selectByParams(Map<String, Object> params);

}