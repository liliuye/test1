package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ӳ�䴦����:
 *    �����ṩ����·���봦�����Ķ�Ӧ��ϵ��
 *    ���磬"/hello.do"�������Ӧ����
 *    HelloController��hello����������
 *
 */
public class HandlerMapping {
	
	//handlerMap���ڴ������·���봦�����Ķ�Ӧ��ϵ��
	private Map<String,Handler> handlerMap = 
			new HashMap<String,Handler>();
	
	/**
	 * ��������·������Handler����
	 *   ע:
	 *   	Handler�����װ�˴�������Method����
	 */
	public Handler getHandler(String path){
		return handlerMap.get(path);
	}
	
	
	/**
	 * ����������·����Handler�Ķ�Ӧ��ϵ��
	 * ���ȱ���beans���ϣ�Ȼ������java�����ȡ
	 * �������е�@RequestMapping������·����
	 * ��������������·����Ϊkey,��Handler��Ϊ
	 * value(Handler��װ�˴�������Method����),
	 * �������Ӧ��ϵ��ŵ�handlerMap���档
	 */
	public void process(List beans) {
		for(Object bean : beans){
			Class clazz = bean.getClass();
			//��ô����������з���
			Method[] methods = 
					clazz.getDeclaredMethods();
			//���������������з���
			for(Method mh : methods){
				//��ü��ڷ���ǰ��@RequestMappingע��
				RequestMapping rm = 
					mh.getDeclaredAnnotation(
							RequestMapping.class);
				//��ȡ@RequestMappingע���value����ֵ
				//(������ֵ������·��)
				String path = rm.value();
				System.out.println("path:" + path);
				//������·����Ϊkey,��Handler������Ϊ 
				//value(�ö����װ��Method��������ʵ��),
				//����Ӧ��ϵ��ŵ�handlerMap��
				handlerMap.put(path, 
						new Handler(mh,bean));
			}
			
		}
		System.out.println("handlerMap:" 
				+ handlerMap);
		
		
	}

}

