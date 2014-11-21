package com.haedrig.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.haedrig.util.Page;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class PublicAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware, Serializable {

	private static final long serialVersionUID = -8399801810608284799L;
	protected Page pageBean;
	protected Map<String, Object> map;
	protected List<?> list;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}
	
}

