package xin.marcher.blog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 * 
 * @author marcher
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext = applicationContext;
		}
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 通过name和class返回指定的Bean
	 * @param name	name
	 * @param clazz	class
	 * @param <T>	T
	 * @return
	 * 		指定的Bean
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}