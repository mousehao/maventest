package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Map;

/**
 *
 */
public class HttpRequestUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

	public static String sendGet(String url) {
		String content = "";

		BufferedReader in = null;
		try {
			URL uri = new URL(url);

			URLConnection connection = uri.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			connection.connect();


			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), EncodeUtils.ENCODING_UTF_8));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			content = sb.toString();

		} catch (Exception e) {
			logger.error("GET Error:", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				logger.error("Close InputStream Error:", e);
			}
		}

		return content;
	}

	public static String sendGet(String url, String charsetName) {
		String content = "";

		BufferedReader in = null;
		try {
			URL uri = new URL(url);

			URLConnection connection = uri.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			connection.connect();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetName));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			content = sb.toString();

		} catch (Exception e) {
			logger.error("GET Error:", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				logger.error("Close InputStream Error:", e);
			}
		}

		return content;
	}

	public static String sendPost(String url) {
		return sendPost(url, null);
	}

	public static String sendPost(String url, Map<String, String> params) {
		String content = "";

		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL uri = new URL(url);

			URLConnection connection = uri.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			connection.setDoOutput(true);
			connection.setDoInput(true);

			StringBuffer param = new StringBuffer();
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					param.append(entry.getKey() + "=" + entry.getValue() + "&");

				}
				param.deleteCharAt(param.length() - 1);
			}
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), EncodeUtils.ENCODING_UTF_8));
			out.print(param);
			out.flush();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), EncodeUtils.ENCODING_UTF_8));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			content = sb.toString();
		} catch (Exception e) {
			logger.error("POST Error:", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("Close Stream Error:", e);
			}
		}

		return content;
	}

	public static JSONObject receivePost(HttpServletRequest request) {
		BufferedReader br = null;
		String reqBody = null;
		try {
			// 读取请求内容
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// 将资料解码
			reqBody = sb.toString();
			URLDecoder.decode(reqBody, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("reqBody:" + reqBody);
		return JSON.parseObject(reqBody);
	}

}
