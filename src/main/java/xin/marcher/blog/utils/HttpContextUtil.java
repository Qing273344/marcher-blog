package xin.marcher.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * requestUtil
 *
 * @author marcher
 */
public class HttpContextUtil {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}

	public static String getMethods() {
		return "GET, POST, OPTIONS, PUT, DELETE";
	}

	public static String getHanders() {
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Access-Control-Request-Headers");
	}
}
