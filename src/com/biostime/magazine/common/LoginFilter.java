/**
 * 
 */
package com.biostime.magazine.common;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆过滤器
 * 
 * @author cjl 新增日期：2014-1-4上午09:36:16
 * @version 1.0
 */
public class LoginFilter implements Filter {

	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		Map userinfo = (Map) request.getSession().getAttribute("userinfo");
		if (userinfo == null) {
			String uri = request.getRequestURI();
			if (uri.contains("/page/") && !uri.contains("login.jsp")) {
				response.sendRedirect("login.action");
			} else if (uri.contains(".action") && !uri.contains("login.action")) {
				response.sendRedirect("login.action");
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
