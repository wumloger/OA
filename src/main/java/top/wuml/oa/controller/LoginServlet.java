package top.wuml.oa.controller;

import com.alibaba.fastjson.JSONObject;
import top.wuml.oa.entity.User;
import top.wuml.oa.service.UserService;
import top.wuml.oa.service.exception.LoginException;
import top.wuml.oa.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        //  request.getReader();方法返回一个包含body体数据的BufferedReader；
        BufferedReader reader = req.getReader();
        String readerStr = "";// 接收用户端传来的JSON字符串（body体里的数据）
        String line;
        while ((line = reader.readLine()) != null) {
            readerStr = readerStr.concat(line);
        }
        User user = JSONObject.parseObject(readerStr, User.class);
        if (user != null){
            String username = user.getUsername();
            String password = user.getPassword();
            if(username != null&&password != null){
                User loginUser = userService.login(username, password);
                if (loginUser != null){
                    HttpSession session = req.getSession();
                    session.setAttribute("userId",loginUser.getUserId());
                    session.setAttribute("employeeId",loginUser.getEmployeeId());
                    Result result = Result.success(loginUser);
                    String jsonString = JSONObject.toJSONString(result);
                    resp.getWriter().write(jsonString);
                }else{
                    Result result = Result.error("账号或密码错误");
                    String jsonString = JSONObject.toJSONString(result);
                    resp.getWriter().write(jsonString);
                }
            }else{
                Result result = Result.error("账号或密码未填写");
                String jsonString = JSONObject.toJSONString(result);
                resp.getWriter().write(jsonString);
            }
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
