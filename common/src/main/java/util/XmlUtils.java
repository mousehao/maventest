package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtils {
	private final static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

	public static String parserXml(String xmlStr) {
		String result = "";
		try {
			Document document = DocumentHelper.parseText(xmlStr);
			Element elmt = document.getRootElement();
			result = elmt.node(1).getText();
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
			logger.error("解析xml数据失败：" + e.getMessage());
		}
		return result;
	}
}
