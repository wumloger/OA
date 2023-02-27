import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.Employee;
import top.wuml.oa.service.EmployeeService;

public class EmployeeTest {
    private EmployeeService employeeService = new EmployeeService();
    @Test
    public void testGetEmp(){
        Employee employee = employeeService.getEmployee(1L);
        System.out.println(employee);
    }
}
