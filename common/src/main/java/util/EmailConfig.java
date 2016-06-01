package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载邮件配置文件
 */
public class EmailConfig {


	private static final Properties emailConfig = new Properties();
	private static final String EMAIL_CONFIG_FILE = "/email-config.properties";
	private static final String EMAIL_CONFIG_USER = "mail.smtp.username";
	private static final String EMAIL_CONFIG_PWD = "mail.smtp.password";
	private static final String EMAIL_CONFIG_SENDER = "mail.smtp.sender";
	private static final Logger logger = LoggerFactory.getLogger(EmailConfig.class);

	static {
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = EmailConfig.class.getResourceAsStream(EMAIL_CONFIG_FILE);
			emailConfig.load(resourceAsStream);
		} catch (IOException e) {
			logger.error("读取emil的配置文件报错，请检查文件是否在路径" + EMAIL_CONFIG_FILE + "下，获取文件内容是否正确！！", e);
		} finally {
			if (resourceAsStream != null) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					logger.warn("close email-config.properties");
				}
			}
		}
	}

	private EmailConfig() {
	}

	public static Properties getConfiguration() {
		return emailConfig;
	}

	public static String getUsername() {
		return (String) emailConfig.get(EMAIL_CONFIG_USER);
	}

	public static String getPassword() {
		return (String) emailConfig.get(EMAIL_CONFIG_PWD);
	}

	public static String getSender() {
		return (String) emailConfig.get(EMAIL_CONFIG_SENDER);
	}
}
