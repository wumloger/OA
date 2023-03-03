package top.wuml.oa.mapper;

import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.util.MybatisUtils;

import java.util.Date;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

class LeaveFormMapperTest {

    @Test
    void update() {
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setFormId(12L);
        leaveForm.setEmployeeId(1L);
        leaveForm.setFormType(1);
        leaveForm.setStartTime(new Date());
        leaveForm.setEndTime(new Date());
        leaveForm.setReason("遛狗");
        leaveForm.setCreateTime(new Date());
        leaveForm.setState("approved");
        MybatisUtils.executeUpdate(sqlSession -> {
            sqlSession.getMapper(LeaveFormMapper.class).update(leaveForm);
            sqlSession.commit();
            return null;
        });
    }

    @Test
    void selectById() {
        MybatisUtils.executeQuery(sqlSession -> {
            LeaveForm form = sqlSession.getMapper(LeaveFormMapper.class).selectById(13L);
            System.out.println(form);
            return null;
        });
    }
}