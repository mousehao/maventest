package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring context holder
 * 
 * @author lijib
 * 
 */
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext context = null;


	private static final Log log = LogFactory.getLog(SpringContextHolder.class);


	public static ApplicationContext getContext() {
		return context;
	}

	public static synchronized void setContext(ApplicationContext context) {
		SpringContextHolder.context = context;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		log.info("Initial spring application context.");
		SpringContextHolder.setContext(applicationContext);
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}