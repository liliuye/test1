package controller;

import javax.servlet.http.HttpServletRequest;

import base.common.RequestMapping;

public class LoginController {
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		System.out.println(
				"LoginController's toLogin");
		//添加注释
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(
			HttpServletRequest request){
		System.out.println(
				"LoginController's login");
		//��ȡ�û���������
		String username = 
				request.getParameter("username");
		String pwd = 
				request.getParameter("pwd");
		System.out.println(
				"username:" + username);
		if("Tom".equals(username) && 
				"1234".equals(pwd)){
			//��¼�ɹ�
			/*
			 * �����ͼ������"redirect:"��ͷ����ʾ
			 * �ض���
			 */
			return "redirect:toSuccess.do";
		}else{
			//��¼ʧ��
			request.setAttribute(
					"login_failed", 
					"�û������������");
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




