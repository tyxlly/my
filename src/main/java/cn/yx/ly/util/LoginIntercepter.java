package cn.yx.ly.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginIntercepter implements HandlerInterceptor {

	private String [] arr = {"uc/login","","bc/findall","bc/findbyid"};
	
	public boolean checkUrl(String myurl){
		
		if(myurl.endsWith(".jpg")||myurl.endsWith(".png")||myurl.endsWith(".css")||myurl.endsWith(".js")){
			return true;
		}
		
		for(int i=0;i<arr.length;i++){
			if(arr[i].equals(myurl)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ��Ŀ�귽��ִ��֮ǰҪ��ɵĲ���
	 * �����������true,������Լ����������������false,���󽫵���Ϊֹ������Ŀ�귽������������ִ��;
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Ŀ�귽��ִ��֮ǰ�Ĳ���...");
		
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("myusers");
		
		String path = request.getContextPath();
		String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
	
		if(obj==null){
			
			String uri = request.getRequestURI();
			String myurl = null;
			if(uri.contains(";jsessionid")){
				myurl = uri.substring(path.length()+1,uri.indexOf(";jsessionid"));
			}else{
				myurl = uri.substring(path.length()+1);
			}
			System.out.println("myurl:"+myurl);
			if(this.checkUrl(myurl)){
				return true;
			}
			
			response.sendRedirect(basepath+"uc/login");
			return false;
		}
		
		return true;
	}

	/**
	 * Ŀ�귽��ִ��֮��Ĳ���
	 * Ҫ��preHandle�������뷵��true,�Ż�ִ��postHandle����
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Ŀ�귽��ִ��֮��Ĳ���");

	}

	/**
	 * �������ִ�еĲ�����һ�������ͷ���Դ
	 * Ҫ��preHandle�������뷵��true,afterCompletion��������ִ��
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("�������ʱ�Ĳ���");

	}

}
