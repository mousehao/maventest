package util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringResponseHandler implements ResponseHandler<String> {
	@Override
	public String handleResponse(final HttpResponse response)
			throws IOException {
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}

		if (entity == null) {
			throw new ClientProtocolException("Response contains no content");
		}
		System.out.println("response length:" + entity.getContentLength());
		InputStream inSm = entity.getContent();
		String result = null;
		try {
			result = inStream2String(inSm);
		} catch (Exception e) {
		}
		return result;
	}

	// 将输入流转换成字符串
	private String inStream2String(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		return new String(baos.toByteArray(), EncodeUtils.ENCODING_UTF_8);
	}
}
