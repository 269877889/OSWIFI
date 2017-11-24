package com.yixuan.oswifi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang3.StringUtils;

public class LoginSessionFilter implements Filter{

	public FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public static boolean isContains(String container,String contextpath,String[] stringz){
		boolean result = false;
		
		if(container.equals(contextpath)){
			result = true;
			return result;
		}
		
		for(int i=0;i<stringz.length;i++){
			if(container.indexOf(stringz[i]) != -1){
				result = true;
			}
		}
		
		return result;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsrequest = (HttpServletRequest)request;
		HttpServletResponseWrapper warpper = new HttpServletResponseWrapper((HttpServletResponse)response);
//		hsrequest.getServletContext();
		String logonStrings = filterConfig.getInitParameter("logonStrings");
		String[] logonz = logonStrings.split(";");
		String redirectPath = filterConfig.getInitParameter("redirectPath");
		// 对登录页面、alipay的请求不进行过滤
		 if (this.isContains(hsrequest.getRequestURI(),hsrequest.getContextPath()+"/", logonz)) {
	            chain.doFilter(request, response);
	            return;
	        }
		
		String username = (String) hsrequest.getSession().getAttribute("username");
		if(StringUtils.isBlank(username)){
			String initpath = hsrequest.getScheme()+ "://"+ hsrequest.getServerName() + ":" + hsrequest.getServerPort()+hsrequest.getContextPath()+redirectPath;
			warpper.sendRedirect(initpath);
            return;
		}else{
			 chain.doFilter(request, response);
	            return;
		}
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}

}
