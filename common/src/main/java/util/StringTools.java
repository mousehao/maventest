package util;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Huawei on 15/10/29.
 */
public class StringTools {
	public static String trimPreAndSuf(String nets) {
		return StringUtils.replace(nets, "'", "");
	}

	/**
	 * 去掉最后一个逗号
	 * @param str
	 * @return
	 */
	public static String removeLastStr(String str,String reg) {
        if (StringUtils.isNotEmpty(str)) {
            str = str.substring(0, str.lastIndexOf(reg));
        }
		return str;
	}

	public static String replaceBlanksWithOneBlank(String str) {
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(str);
		str = m.replaceAll(" ");
		return str;
	}

	public static String listToStrings(List<String> moreSrcFilePathList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < moreSrcFilePathList.size(); i++) {
			sb.append(moreSrcFilePathList.get(i));
			if (i < moreSrcFilePathList.size() - 1) {
				sb.append(";");
			}
		}

		return sb.toString();
	}
}
