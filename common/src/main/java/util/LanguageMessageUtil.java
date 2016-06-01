package util;

import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * com.huawei.fda.common.util.LanguageMessageUtil
 *
 * @author Huawei
 * @version v1.0
 * @date 2015/10/16 0016 15:03
 * @update
 */
public class LanguageMessageUtil {

	public static String getMessage(HttpServletRequest request,String key) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String msg = SpringContextHolder.getContext().getMessage(key, null, locale);
		return msg;
	}
}
