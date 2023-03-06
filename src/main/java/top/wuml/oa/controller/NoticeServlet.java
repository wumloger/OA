package top.wuml.oa.controller;

import com.alibaba.fastjson.JSONObject;
import top.wuml.oa.entity.Notice;
import top.wuml.oa.service.NoticeService;
import top.wuml.oa.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/notice/list")
public class NoticeServlet extends HttpServlet {
    private final NoticeService noticeService = new NoticeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String employeeId = req.getParameter("eid");
        List<Notice> noticeList = noticeService.getNoticeList(Long.parseLong(employeeId));
        Result success = Result.success(noticeList);
        String jsonString = JSONObject.toJSONString(success);
        resp.getWriter().write(jsonString);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
