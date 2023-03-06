package top.wuml.oa.filter;




import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;



//设置拦截器，拦截器的名字是loginerFilter，要拦截的路径写urlPattern里
//@WebFilter(filterName = "loginFilter", urlPatterns = {"/api/*","/department","/emp","/node","/api/*/*","/audit.html","/index.html","/notice.html","/leave_form.html"})
public class Filter implements javax.servlet.Filter {
    //初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    //拦截后做的操作，什么时候放行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();



        //如果session中有user对象就放行，否则返回登录界面
        if (request.getSession().getAttribute("userId") != null){
            filterChain.doFilter(request, response);
            return;
        }else {
            response.sendRedirect("login.html");
        }
    }
    //拦截器销毁后执行的方法
    @Override
    public void destroy() {

    }

}
