package top.wuml.oa.service;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.entity.ProcessFlow;
import top.wuml.oa.mapper.EmployeeMapper;
import top.wuml.oa.mapper.LeaveFormMapper;
import top.wuml.oa.mapper.ProcessFlowMapper;
import top.wuml.oa.util.MybatisUtils;
import top.wuml.oa.util.TimeHelper;

import java.util.Date;

public class LeaveService {
    private final EmployeeService employeeService = new EmployeeService();
    public LeaveForm createLeaveForm(LeaveForm form){
        return (LeaveForm) MybatisUtils.executeUpdate(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.selectByEId(form.getEmployeeId());

            if (employee.getLevel() == 8){
                form.setState("approved");
            }else {
                form.setState("processing");
            }
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            leaveFormMapper.insert(form);

            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow flow1 = new ProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(form.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0);
            processFlowMapper.insert(flow1);

            int level = employee.getLevel();
            switch (level){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6: {
                    Employee leader = employeeService.selectLeader(employee.getEmployeeId());
                    System.out.println(leader);
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(form.getFormId());
                    flow2.setOperatorId(leader.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setCreateTime(new Date());
                    flow2.setOrderNo(2);
                    flow2.setState("process");

                    long hours = TimeHelper.getDiffHours(form.getStartTime(),form.getEndTime());
                    System.out.println(hours);

                    if (hours >= 72){
                        flow2.setIsLast(0);
                        processFlowMapper.insert(flow2);
                        Employee boss = employeeService.selectLeader(leader.getEmployeeId());
                        System.out.println(boss);

                        ProcessFlow flow3 = new ProcessFlow();
                        flow3.setFormId(form.getFormId());
                        flow3.setOperatorId(boss.getEmployeeId());
                        flow3.setAction("dudit");
                        flow3.setCreateTime(new Date());
                        flow3.setState("ready");
                        flow3.setOrderNo(3);
                        flow3.setIsLast(1);
                        processFlowMapper.insert(flow3);
                    }else{
                        flow2.setIsLast(1);
                        processFlowMapper.insert(flow2);
                    }
                };break;
                case 7: {
                        Employee boss = employeeService.selectLeader(employee.getEmployeeId());
                        ProcessFlow flow2 = new ProcessFlow();
                        flow2.setFormId(form.getFormId());
                        flow2.setOperatorId(boss.getEmployeeId());
                        flow2.setAction("audit");
                        flow2.setCreateTime(new Date());
                        flow2.setState("process");
                        flow2.setOrderNo(2);
                        flow2.setIsLast(1);
                        processFlowMapper.insert(flow2);
                };break;
                case 8:{
                    ProcessFlow flow2 = new ProcessFlow();
                    flow2.setFormId(form.getFormId());
                    flow2.setOperatorId(employee.getEmployeeId());
                    flow2.setAction("audit");
                    flow2.setResult("approved");
                    flow2.setReason("自动通过");
                    flow2.setCreateTime(new Date());
                    flow2.setAuditTime(new Date());
                    flow2.setState("complete");
                    flow2.setOrderNo(2);
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);
                };break;
                default:
                    System.out.println("无此等级员工");
            }
            return form;
        });
    }
}