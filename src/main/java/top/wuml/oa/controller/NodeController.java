package top.wuml.oa.controller;

import com.alibaba.fastjson.JSONObject;
import top.wuml.oa.service.NodeService;
import top.wuml.oa.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

@WebServlet("/node")
public class NodeController extends HttpServlet {
    private NodeService nodeService = new NodeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long uid = Long.parseLong(req.getParameter("uid"));
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        if (uid != null){
            List<Map<String, Object>> node = nodeService.getNode(uid);
            Result success = Result.success(node);
            String jsonString = JSONObject.toJSONString(success);
            resp.getWriter().write(jsonString);
        }else{
            Result uidError = Result.error("用户id出错");
            String jsonString = JSONObject.toJSONString(uidError);
            resp.getWriter().write(jsonString);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req,resp);
    }
}
