package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;


public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HandlerMapping handlerMapping;
	
	public void init() 
			throws ServletException {
		/*
		 * ��ȡsmartmvc�����ļ��д�������������Ϣ��
		 * Ȼ������java���佫������ʵ������
		 */
		SAXReader reader = new SAXReader();
		InputStream in = 
				getClass().getClassLoader()
				.getResourceAsStream(
						"smartmvc.xml");	
		try {
			Document doc = 
					reader.read(in);
			Element root = 
					doc.getRootElement();
			List<Element> elements = 
					root.elements();
			//beans������Ŵ�����ʵ��
			List beans = new ArrayList();
			for(Element ele : elements){
				//��ȡ����������
				String className = 
					ele.attributeValue("class");
				System.out.println(
					"className:" + className);
				//��������ʵ����
				Object bean = 
					Class.forName(className)
					.newInstance();
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			
			//����ӳ�䴦����ʵ��
			handlerMapping = new HandlerMapping();
			/*
			 * ����ӳ�䴦������process����,�÷���
			 * ����java�����ȡ@RequestMapping��
			 * ������·����Ȼ��������·���봦����
			 * �Ķ�Ӧ��ϵ��
			 */
			handlerMapping.process(beans);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void service(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException,
					IOException {
		request.setCharacterEncoding("utf-8");
		/*
		 * �Ȼ��������Դ·����Ȼ���ȡ������Դ·��
		 * ��һ������������·��(path),��������
		 * ����HandlerMapping��getHandler������
		 * ���Handler�����������Handler����
		 * �����ô������ķ�����
		 */
		//���������Դ·��
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);
		//���Ӧ����
		String contextPath = 
				request.getContextPath();
		System.out.println("contextPath:"
				+ contextPath);
		//��ȡ������Դ·����һ����(����Ӧ����)��
		//��������·����
		String path = 
				uri.substring(
						contextPath.length());
		System.out.println("path:" + path);
		
		//��������·���ҵ���Ӧ��Handler����
		Handler handler = 
				handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);
		
		//����handler���󣬵��ô������ķ���
		Method mh = handler.getMh();
		Object bean = handler.getObj();
		//rv�Ǵ����������ķ���ֵ(����ͼ��)��
		Object rv = null;
		try {
			
			/*
			 * ���ô������ķ�����
			 * ���жϴ�����������������(����
			 * ͨ��Method�����ṩ��
			 * getParameterTypes��������֪)��
			 */
			Class[] types = 
					mh.getParameterTypes();
			if(types.length == 0){
				//���ò����εķ���
				rv = mh.invoke(bean);
			}else{
				//params�������ڴ��ʵ�ʲ���ֵ
				Object[] params = 
						new Object[types.length];
				/*
				 * Ŀǰֻ֧���ڴ�����������������
				 * request��response��Ϊ������
				 */
				for(int i = 0; i < types.length; i++){
					if(types[i] == 
							HttpServletRequest.class){
						params[i] = request;
					}
					if(types[i] == 
							HttpServletResponse.class){
						params[i] = response;
					}
				}
				//���ô��εķ���
				rv = mh.invoke(bean, params);
			}
			
			System.out.println("rv:" + rv);
			
			String viewName = rv.toString();
			/*
			 * �����ͼ������"redirect:"��ͷ��
			 * ���ض��򣬷���ת����
			 */
			if(viewName.startsWith("redirect:")){
				/*
				 * ������ͼ���������ض����ַ
				 * (Ҫ��"redirect:"����)��
				 */
				String redirectPath = 
						contextPath + "/" 
				+ viewName.substring(
						"redirect:".length());
				//�ض���
				response.sendRedirect(redirectPath);
			}else{
				/*
				 * ������ͼ��������jsp��ַ
				 * ("/WEB-INF/" + ��ͼ�� + ".jsp")
				 */
				String jspPath = 
						"/WEB-INF/" + viewName + ".jsp";
				//ת��
				request.getRequestDispatcher(
						jspPath)
				.forward(request, response);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}

}