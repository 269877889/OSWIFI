package com.yixuan.oswifi.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.yixuan.oswifi.model.PCuserLoginEntity;
import com.yixuan.oswifi.service.IPCUserLoginService;




public class PCUserLoginAction extends ActionSupport implements ServletRequestAware{

	private String username;
	
	private String password;
	
	private IPCUserLoginService  pcuserloginservice;

	private boolean loginsuccess;
	
	private HttpServletRequest request;
	
//	public IPCUserLoginService getPcuserloginservice() {
//		return pcuserloginservice;
//	}

	/**
	 * 登录验证
	 * @return
	 */
	public String login(){
		PCuserLoginEntity entity = new PCuserLoginEntity();
		entity.setUsername(username);
		entity.setPassword(password);
		
		int i = pcuserloginservice.queryCountUser(entity);
		if(i> 0){
			loginsuccess = true;
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setMaxInactiveInterval(36000);
		}else{
			loginsuccess = false;
		}
		
		return SUCCESS;	
	}

	/**
	 * 退出
	 * @return
	 */
	public String logout(){
		HttpSession session = request.getSession();
		session.setAttribute(username, null);
		return SUCCESS;
	}
	
	public void setPcuserloginservice(IPCUserLoginService pcuserloginservice) {
		this.pcuserloginservice = pcuserloginservice;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoginsuccess() {
		return loginsuccess;
	}

	public void setLoginsuccess(boolean loginsuccess) {
		this.loginsuccess = loginsuccess;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
