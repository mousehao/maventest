package util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 */
public class HttpUtils {
	private static final Logger Log = Logger.getLogger(HttpUtils.class);

	/**
	 * 判断请求是否为AJAX请求
	 *
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		boolean ajax = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
		String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");

		return ajax || ajaxFlag.equalsIgnoreCase("true");
	}

	/**
	 * Used to test.
	 *
	 * @param url
	 * @param parasEntity
	 */
	public static void post(String url, StringEntity parasEntity) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			parasEntity.setContentEncoding("UTF-8");
			parasEntity.setContentType("application/json");
			httpPost.setEntity(parasEntity);
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream inSm = entity.getContent();
			Scanner inScn = new Scanner(inSm, EncodeUtils.ENCODING_UTF_8);
			while (inScn.hasNextLine()) {
				System.out.println(inScn.nextLine());
			}
			httpPost.abort();
			inScn.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

	private static String getJsonFromServer(String url, HttpEntity entity) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(entity);

		String json = null;
		try {
			ResponseHandler<String> rh = new StringResponseHandler();
			json = httpClient.execute(httppost, rh);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String getJsonFromWithForm(Map<String, String> paramMap,
											 String url) {
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		HttpEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		return getJsonFromServer(url, entity);
	}

	public static String getJsonFromUrlWithObject(String jsonParam, String url) {
		Log.debug("sendJson---" + jsonParam);
		StringEntity entity = null;
		entity = new StringEntity(jsonParam, "UTF-8");
		entity.setContentType("application/json");

		return getJsonFromServer(url, entity);
	}

	public static String getJsonWithGet(Map<String, String> paramMap, String url) {
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		StringBuilder sb = new StringBuilder();
		if (!paramMap.isEmpty()) {
			url += "?";
		}
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		url += sb.toString();
		HttpGet httpGet = new HttpGet(url);
		String json = null;
		try {
			CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
			json = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String getJsonWithGet(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String json = null;
		try {
			ResponseHandler<String> rh = new StringResponseHandler();
			json = httpClient.execute(httpGet, rh);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
