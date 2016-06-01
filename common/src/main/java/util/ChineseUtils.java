package util;

import org.springframework.util.StringUtils;

/**
 * com.huawei.fda.common.util.ChineseUtils
 *
 * @author Huawei
 * @version v1.0
 * @date 2015/10/22 0022 16:34
 * @update
 */
public class ChineseUtils {

	/**
	 * 判断是否是中文
	 *
	 * @param strName
	 * @return
	 */
	public static boolean isChinese(String strName) {
		if (StringUtils.isEmpty(strName)) {
			return true;
		}
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
}
