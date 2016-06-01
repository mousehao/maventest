package util;

import freemarker.template.Configuration;

/**
 * com.huawei.fda.common.util.FreeMarkerUtils
 *
 * @author Huawei
 * @version v1.0
 * @date 2015/11/18 0018 12:56
 * @update
 */
public class FreeMarkerUtils {

	private static Configuration configuration = null;

	static {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}
	public static Configuration getConfiguration() {
		return configuration;
	}


}
