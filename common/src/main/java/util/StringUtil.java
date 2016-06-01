package util;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huawei on 2016/1/7.
 */
public class StringUtil {

	public static String joinAndSplitByComma(Collection<String> codes) {
		StringBuilder sb = new StringBuilder();
		int counter = 1;
		for (String code : codes) {
			sb.append(code);
			if (counter != (codes.size())) {
				sb.append(",");
			}
			counter++;
		}
		return sb.toString();
	}

	public static String joinAndSplitByStr(Object[] objs, String str) {
		StringBuilder sb = new StringBuilder();
		int counter = 1;
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i].toString());
			if (counter != (objs.length)) {
				sb.append(str);
			}
			counter++;
		}
		return sb.toString();
	}

	public static String getStrWhenNull(String checkValue, String returnStr) {
		return StringUtils.isEmpty(checkValue) ? returnStr : checkValue;
	}

	public static String truncateStr(String str, int num) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		return str.length() > num ? str.substring(0, num) : str;
	}

	//获取子串的个数
	public static int getSubStringNum(String str, String substring) {
		int substringNum = 0;
		if (StringUtils.isNotEmpty(str)) {
			int index = str.indexOf(substring);
			while (index >= 0) {
				substringNum++;
				index = str.indexOf(substring, (index + 1));
			}
		}
		return substringNum;
	}

	public static String formatBoardDetail(String detail) {
		String patternStr = "(\\{[^}]+\\})";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(detail);
		while (matcher.find()) {
			String str = matcher.group(1);
			String code = str.substring(1, str.indexOf(","));
			String idBoard = str.substring(str.indexOf(",") + 1, str.length() - 1);
			String hyperlink = "[<a href='/fadmodel/getReport/" + idBoard + "' target='_blank'>" + code + "</a>]";
			detail = detail.replace(str, hyperlink);
		}
		List<String> codeDescList = new ArrayList<String>();
		Collections.addAll(codeDescList, detail.split(";"));
		codeDescList.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		StringBuilder result = new StringBuilder();
		int count = 1;
		for (String item : codeDescList) {
			result.append(item);
			result.append(";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			if (count % 3 == 0) {
				result.append("</br>");
			}
			count++;
		}
		return result.toString();
	}
}

