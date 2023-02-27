import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.User;
import top.wuml.oa.service.UserService;
import top.wuml.oa.util.MybatisUtils;

public class MyBatisUtilsTest {
    private UserService userService = new UserService();
    @Test
    public void testCase1(){
        Object str = (String)MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("test.sample"));
        System.out.println(str);
    }

    @Test
    public void testCase2(){
        User login = userService.login("m8", "test");
        System.out.println(login);
    }

}
