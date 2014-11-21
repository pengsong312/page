package com.haedrig.action.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.haedrig.action.PublicAction;
import com.haedrig.service.IUserService;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends PublicAction  {

	private static final long serialVersionUID = -487806416102619461L;
	@Autowired
	private IUserService iUserService;

	public String execute() throws Exception {
		map = ActionContext.getContext().getParameters();
		pageBean = iUserService.findUsersByPage(map);
		request.setAttribute("pageBean", pageBean);
		return SUCCESS;
	}
	
}
