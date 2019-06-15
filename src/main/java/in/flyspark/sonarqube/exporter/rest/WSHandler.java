package in.flyspark.sonarqube.exporter.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class WSHandler {
	private static final Logger log = LogManager.getLogger(WSHandler.class);

	private static String response = "";
	private final static int GET = 1;
	private final static int POST = 2;

	static String makeServiceCall(final String url) {
		return makeServiceCall(url, GET, null);
	}

	private static String makeServiceCall(String url, final int method, final List<NameValuePair> params) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse httpResponse = null;

			if (method == POST) {
				HttpPost httpPost = new HttpPost(url);
				if (params != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				}
				httpResponse = httpClient.execute(httpPost);
			} else if (method == GET) {
				if (params != null) {
					String paramString = URLEncodedUtils.format(params, "UTF-8");
					url += "?" + paramString;
				}
				HttpGet httpGet = new HttpGet(url);
				httpResponse = httpClient.execute(httpGet);
			}
			response = EntityUtils.toString(httpResponse.getEntity());
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		} catch (ClientProtocolException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return response;
	}

}
