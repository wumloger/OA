package top.wuml.oa.controller;

import com.alibaba.fastjson.JSONObject;
import top.wuml.oa.entity.Employee;
import top.wuml.oa.service.EmployeeService;
import top.wuml.oa.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/emp")
public class EmployeeServlet extends HttpServlet {
    private EmployeeService employeeService = new EmployeeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        long eid = Long.parseLong(req.getParameter("eid"));
        System.out.println(eid);
        Employee employee = employeeService.getEmployee(eid);
        if(employee != null){
            Result result = Result.success(employee);
            String jsonString = JSONObject.toJSONString(result);
            resp.getWriter().write(jsonString);
        }else{
            Result result = Result.error("不存在");
            String jsonString = JSONObject.toJSONString(result);
            resp.getWriter().write(jsonString);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
