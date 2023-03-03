import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.Employee;
import top.wuml.oa.mapper.EmployeeMapper;
import top.wuml.oa.service.EmployeeService;
import top.wuml.oa.util.MybatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeTest {
    private EmployeeService employeeService = new EmployeeService();

    @Test
    public void testGetEmp(){
        Employee employee = employeeService.getEmployee(1L);
        System.out.println(employee);
    }

    @Test
    void selectByParams(){
       Employee emp = (Employee) MybatisUtils.executeQuery(sqlSession -> {
           Map<String,Object> params = new HashMap<>();
           params.put("departmentId",2);
           params.put("level",7);
           EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
           List<Employee> leaders = employeeMapper.selectByParams(params);
           System.out.println(leaders.get(0));
           return leaders.get(0);
       });


    }
}
