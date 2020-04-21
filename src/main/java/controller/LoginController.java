package controller;

import javax.servlet.http.HttpServletRequest;

import base.common.RequestMapping;

public class LoginController {
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		System.out.println(
				"LoginController's toLogin");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(
			HttpServletRequest request){
		System.out.println(
				"LoginController's login");
		//读取用户名和密码
		String username = 
				request.getParameter("username");
		String pwd = 
				request.getParameter("pwd");
		System.out.println(
				"username:" + username);
		if("Tom".equals(username) && 
				"1234".equals(pwd)){
			//登录成功
			/*
			 * 如果视图名是以"redirect:"开头，表示
			 * 重定向。
			 */
			return "redirect:toSuccess.do";
		}else{
			//登录失败
			request.setAttribute(
					"login_failed", 
					"用户名或密码错误");
			return "login";
		}
		
	}
	
	
	
	@RequestMapping("/toSuccess.do")
	public String toSuccess(){
		System.out.println(
				"LoginController's toSuccess");
		return "success";
	}
}




