package top.wuml.oa.service;

import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.Employee;
import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.mapper.EmployeeMapper;
import top.wuml.oa.mapper.LeaveFormMapper;
import top.wuml.oa.util.MybatisUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LeaveServiceTest {
    private LeaveService leaveService = new LeaveService();
    @Test
    void insert(){
        MybatisUtils.executeUpdate(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            LeaveForm form = new LeaveForm();
            form.setEmployeeId(3L);
            form.setFormType(1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;
            Date endTime = null;
            try{
                startTime = simpleDateFormat.parse("2022-03-03 08:00:00");
                endTime = simpleDateFormat.parse("2022-03-06 18:00:00");
            }catch (ParseException e){
                e.printStackTrace();
            }
            form.setStartTime(startTime);
            form.setEndTime(endTime);
            form.setReason("回家");
            form.setCreateTime(new Date());
            form.setState("processing");
            mapper.insert(form);
            return null;
        });

    }

    @Test
    void createLeaveForm() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(8L);
        form.setStartTime(sdf.parse("2023030308"));
        form.setEndTime(sdf.parse("2023030418"));
        form.setFormType(1);
        form.setReason("市场部员工请假单（72）小时以上");
        form.setCreateTime(new Date());
        LeaveForm leaveForm = leaveService.createLeaveForm(form);
        System.out.println(leaveForm);

    }
    @Test
    void getByParams(){
        List<Map<String, Object>> pocessing = leaveService.getLeaveFormList("complete", 1L);
        System.out.println(pocessing);
    }


}