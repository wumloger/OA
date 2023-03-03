package top.wuml.oa.mapper;

import org.junit.jupiter.api.Test;
import top.wuml.oa.util.MybatisUtils;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentMapperTest {

    @Test
    void getDepartment(){
        Object o = MybatisUtils.executeQuery(sqlSession -> {
            String department = sqlSession.getMapper(DepartmentMapper.class).getDepartment(1L);
            System.out.println(department);
            return department;
        });
    }

}