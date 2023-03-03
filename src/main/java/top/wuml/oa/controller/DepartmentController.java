package top.wuml.oa.controller;

import com.alibaba.fastjson.JSONObject;
import top.wuml.oa.mapper.DepartmentMapper;
import top.wuml.oa.util.MybatisUtils;
import top.wuml.oa.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/department")
public class DepartmentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");
       resp.setCharacterEncoding("utf-8");
        Long did = Long.valueOf(req.getParameter("did"));
        String depart = (String) MybatisUtils.executeQuery(sqlSession -> {
            String department = sqlSession.getMapper(DepartmentMapper.class).getDepartment(did);
            return department;
        });
        Result success = Result.success(depart);
        String jsonString = JSONObject.toJSONString(success);
        resp.getWriter().write(jsonString);
    }
}
