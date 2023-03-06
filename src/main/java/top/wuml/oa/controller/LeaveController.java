package top.wuml.oa.controller;

import com.alibaba.fastjson.JSONObject;
import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.service.LeaveService;
import top.wuml.oa.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ResponseCache;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/api/leave/*")
public class LeaveController extends HttpServlet {
    private final LeaveService leaveService = new LeaveService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        switch (methodName){
            case "create": this.create(req,resp);break;
            case "list":
                this.list(req,resp);break;
            case "audit":
               this.audit(req,resp);break;
            default:
                System.out.println("请求错误");    
        }
    }

    private void audit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String formId = req.getParameter("formId");
        String result = req.getParameter("result");
        String reason = req.getParameter("reason");
        String eid = req.getParameter("eid");

        leaveService.audit(Long.parseLong(formId),Long.parseLong(eid),result,reason);
        Result success = Result.success("success");
        String jsonString = JSONObject.toJSONString(success);
        resp.getWriter().write(jsonString);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String employeeId = req.getParameter("eid");
        List<Map<String, Object>> formList = leaveService.getLeaveFormList("process", Long.parseLong(employeeId));
        Result success = Result.success(formList);
        String jsonString = JSONObject.toJSONString(success);
        resp.getWriter().write(jsonString);

    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String employeeId = req.getParameter("eid");
        String formType = req.getParameter("formType");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String reason = req.getParameter("reason");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(Long.parseLong(employeeId));
        form.setFormType(Integer.parseInt(formType));
        form.setStartTime(new Date(Long.parseLong(startTime)));
        form.setEndTime(new Date(Long.parseLong(endTime)));
        form.setReason(reason);
        form.setCreateTime(new Date());

        LeaveForm leaveForm = leaveService.createLeaveForm(form);
        Result success = Result.success(leaveForm);
        String jsonString = JSONObject.toJSONString(success);
        resp.getWriter().write(jsonString);


    }
}
