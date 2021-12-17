package cn.enncy.funny.aspect;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 解决 request 请求流只能读一次的问题
 * @see RequestWrapper
 * <br/>Created in 15:56 2021/12/17
 *
 * @author enncy
 */

@WebFilter(filterName="bodyReaderFilter",urlPatterns="/*")
public class BodyReaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper=null;
        if(request instanceof HttpServletRequest) {
            requestWrapper=new RequestWrapper((HttpServletRequest)request);
        }
        if(requestWrapper==null) {
            chain.doFilter(request, response);
        }else {
            chain.doFilter(requestWrapper, response);
        }
    }
    @Override
    public void destroy() {
        // do nothing
    }
}
