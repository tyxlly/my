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
	 * 在目标方法执行之前要完成的操作
	 * 如果方法返回true,请求可以继续；如果方法返回false,请求将到此为止，包括目标方法，都将不再执行;
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("目标方法执行之前的操作...");
		
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
	 * 目标方法执行之后的操作
	 * 要求preHandle方法必须返回true,才会执行postHandle方法
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("目标方法执行之后的操作");

	}

	/**
	 * 请求最后执行的操作，一般用来释放资源
	 * 要求preHandle方法必须返回true,afterCompletion方法才能执行
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("请求完成时的操作");

	}

}
