package top.wuml.oa.mapper;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.entity.User;
import top.wuml.oa.util.MybatisUtils;

public class EmployeeMapper {
    public Employee selectByEId(Long employeeId){
        Employee employee =(Employee) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("top.wuml.oa.mapper.EmployeeMapper.selectByEId",employeeId));
        return employee;
    }
}
