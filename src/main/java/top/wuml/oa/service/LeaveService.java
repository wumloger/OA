package top.wuml.oa.service;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.entity.Notice;
import top.wuml.oa.entity.ProcessFlow;
import top.wuml.oa.mapper.EmployeeMapper;
import top.wuml.oa.mapper.LeaveFormMapper;
import top.wuml.oa.mapper.NoticeMapper;
import top.wuml.oa.mapper.ProcessFlowMapper;
import top.wuml.oa.util.MybatisUtils;
import top.wuml.oa.util.TimeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<Map<String,Object>> getLeaveFormList(String state,Long operatorId){
        return (List<Map<String, Object>>) MybatisUtils.executeQuery(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            List<Map<String, Object>> map = mapper.selectByParams(state, operatorId);
            return map;
        });
    }
    public void audit(Long formId,Long operatorId,String result,String reason){
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> flowList = processFlowMapper.selectByFormId(formId);
            if (flowList.size() == 0){
                System.out.println("无效的审批");
            }
            List<ProcessFlow> processList = flowList.stream()
                    .filter(p -> Objects.equals(p.getOperatorId(),operatorId) && "process".equals(p.getState())).collect(Collectors.toList());
            ProcessFlow process = null;
            if (processList.size() == 0){
                System.out.println("未找到待处理任务节点");
            }else{
                process = processList.get(0);
                process.setState("complete");
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowMapper.update(process);
            }
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            LeaveForm form = leaveFormMapper.selectById(formId);

            Employee employee = employeeService.getEmployee(form.getEmployeeId());
            Employee operator = employeeService.getEmployee(operatorId);
            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");

            if (process.getIsLast() == 1){
                form.setState(result);
                leaveFormMapper.update(form);
                String strResult = null;
                if ("approved".equals(result)){
                    strResult = "批准";
                }else if("refused".equals(result)){
                    strResult = "驳回";
                }
                String notice1 = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结束",
                                                sdf.format(form.getStartTime()),
                                                sdf.format(form.getEndTime()),
                                                operator.getTitle(),
                                                operator.getName(),
                                                strResult,
                                                reason);
                Notice notice = new Notice();
                notice.setReceiverId(form.getEmployeeId());
                notice.setContent(notice1);
                notice.setCreateTime(new Date());
                noticeMapper.insert(notice);

                String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束",
                        employee.getTitle(),
                        employee.getName(),
                        sdf.format(form.getStartTime()),
                        sdf.format(form.getEndTime()),
                        strResult,
                        reason);
                Notice ntc = new Notice();
                ntc.setReceiverId(operator.getEmployeeId());
                ntc.setContent(notice2);
                ntc.setCreateTime(new Date());
                noticeMapper.insert(ntc);

            }else{
                List<ProcessFlow> readyList = flowList.stream().filter(p -> "ready".equals(p.getState())).collect(Collectors.toList());

                if ("approved".equals(result)){
                    ProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowMapper.update(readyProcess);

                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s,请继续等待上级审批",
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),
                            reason);
                    Notice notice = new Notice();
                    notice.setReceiverId(form.getEmployeeId());
                    notice.setContent(notice1);
                    notice.setCreateTime(new Date());
                    noticeMapper.insert(notice);
                    String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,申请转至上级领导继续批准",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            reason);
                    Notice ntc = new Notice();
                    ntc.setReceiverId(operator.getEmployeeId());
                    ntc.setContent(notice2);
                    ntc.setCreateTime(new Date());
                    noticeMapper.insert(ntc);
                    String notice3 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()));
                    Notice ntc2 = new Notice();
                    ntc2.setReceiverId(readyProcess.getOperatorId());
                    ntc2.setContent(notice3);
                    ntc2.setCreateTime(new Date());
                    noticeMapper.insert(ntc2);
                }else if ("refused".equals(result)){
                    for (ProcessFlow p :readyList){
                        p.setState("cancel");
                        processFlowMapper.update(p);
                    }
                    form.setState("refused");
                    leaveFormMapper.update(form);

                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已已驳回,审批意见:%s,审批流程已结束",
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),
                            reason);
                    Notice notice = new Notice();
                    notice.setReceiverId(form.getEmployeeId());
                    notice.setContent(notice1);
                    notice.setCreateTime(new Date());
                    noticeMapper.insert(notice);

                    String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已驳回,审批意见:%s,审批流程已结束",
                            employee.getTitle(),
                            employee.getName(),
                            sdf.format(form.getStartTime()),
                            sdf.format(form.getEndTime()),
                            reason);
                    Notice ntc = new Notice();
                    ntc.setReceiverId(operator.getEmployeeId());
                    ntc.setContent(notice2);
                    ntc.setCreateTime(new Date());
                    noticeMapper.insert(ntc);
                }
            }
            return null;
        });
    }
}