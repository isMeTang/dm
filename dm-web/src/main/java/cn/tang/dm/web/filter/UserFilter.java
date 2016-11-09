package cn.tang.dm.web.filter;

import com.sun.org.apache.xalan.internal.xsltc.dom.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户过滤器
 *
 * @author tangzeqian
 * @create 2016-03-12  13:51
 */
public class UserFilter implements Filter, javax.servlet.Filter {

    /**
     * 忽略掉的请求后缀
     */
    private static final String[] ignorUrlPattern = new String[]{".htm", ".html", ".js", ".jpg", ".gif", ".png",
            ".css", ".swf",".ico","jpeg"};

    @Override
    public boolean test(int node) {
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRep = (HttpServletResponse) resp;
        resp.setContentType("text/html; charset=UTF-8");
        httpReq.setCharacterEncoding("UTF-8");

        String servletPath = httpReq.getServletPath();
        // 不包含参数的URL
        StringBuffer subUrl = httpReq.getRequestURL();
        // 若请求URL+参数超长则直接返回
        if (subUrl.append(httpReq.getQueryString()).length() >= 150000) {
            return;
        }
        // 若请求URL包含ignorUrlPattern中的内容，则直接转向，不进入过滤器。
        if (endWith(ignorUrlPattern, servletPath) == true) {
            chain.doFilter(req, resp);
            return;
        }
        // 截取客户请求路径url
        StringBuffer urlBuf = httpReq.getRequestURL();
        String str = (String) httpReq.getSession().getAttribute("USERINFO");
        if(str == null){
            //req.getRequestDispatcher("/login.html").forward(req,resp);
            try{
                httpRep.getWriter().append("error");
            }catch(IOException e){
                e.printStackTrace();
            }
            return;
        }
        chain.doFilter(httpReq, httpRep);
    }

    @Override
    public void destroy() {

    }

    /*
	 * 验target字符串是否以 array 数组中的字符结尾
	 */
    private boolean endWith(String[] array, String target) {
        String lowerStr = target.toLowerCase();
        for (String tmp : array) {
            if (endsWith(lowerStr, tmp) == true) {
                return true;
            }
        }
        return false;
    }
    /*
	 * 验str字符串是否以 suffix中的字符结尾
	 */
    private boolean endsWith(String str, String suffix) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(true, strOffset, suffix, 0, suffix.length());
    }


}
