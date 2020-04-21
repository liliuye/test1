package controller;

import base.common.RequestMapping;

/**
 * ������:
 * 	������ҵ���߼���
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println(
				"HelloController's hello");
		/*
		 * ������ͼ����
		 * DispatcherServlet��������ͼ����λ��
		 * �����jspҳ��("/WEB-INF/" + ��ͼ��+".jsp")��
		 */
		 System.out.println("hi");
		return "hello";
	    
	}
	
}
