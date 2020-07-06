package com.jw.common.bean.request;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bangdao
 * @email bangdao@bangdao-tech.com
 * @date 2019-04-09 10:00
 */
public class HttpUtils {


	public static String KEY_STATUS_CODE = "statusCode";
	public static String KEY_CONTENT = "content";

	/**
	 * 连接池管理器
	 */
	private final static PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager();

	/**
	 * retry handler
	 */
	private final static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
		@Override
		public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
			if (executionCount >= 5) {
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				return true;
			}
			if (exception instanceof InterruptedIOException) {
				return false;
			}
			if (exception instanceof UnknownHostException) {
				return false;
			}
			if (exception instanceof ConnectTimeoutException) {
				return false;
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();

			if (!(request instanceof HttpEntityEnclosingRequest)) {
				return true;
			}
			return false;
		}
	};

	static {
		//类加载的时候 设置最大连接数 和 每个路由的最大连接数
		poolConnManager.setMaxTotal(2000);
		poolConnManager.setDefaultMaxPerRoute(1000);
	}


	/**
	 * ########################### core code#######################
	 * @return
	 */
	private static CloseableHttpClient getCloseableHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(poolConnManager)
				.setRetryHandler(httpRequestRetryHandler)
				.build();
		return httpClient;
	}


	/**
	 * buildResultMap
	 *
	 * @param response
	 * @param entity
	 * @return
	 * @throws IOException
	 */
	private static Map<String, Object> buildResultMap(CloseableHttpResponse response, HttpEntity entity) throws
			IOException {
		Map<String, Object> result;
		result = new HashMap<>(2);
		//status code
		result.put(KEY_STATUS_CODE, response.getStatusLine().getStatusCode());
		if (entity != null) {
			//message content
			result.put(KEY_CONTENT, EntityUtils.toString(entity, "UTF-8"));
		}
		return result;
	}


	/**
	 * 发送 get请求
	 * @param param
	 * @param URL
	 */
	public static String get(String param,String URL,String token) {
		CloseableHttpClient httpclient = getCloseableHttpClient();
		CloseableHttpResponse response =null;
		String obj=null;
		try {
			HttpUriRequest httpget = new HttpGet(URL+"?"+param);
			httpget.addHeader("tenantToken", token);
			// 执行get请求.
			response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				obj= EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			httpClose(response);
		}
		return obj;
	}


	/**
	 * 发送 post请求【json类型】
	 * @param json
	 * @param URL
	 */
	public static String post(String json,String URL,String token) {
		String obj=null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = getCloseableHttpClient();
		CloseableHttpResponse response=null;
		// 创建httppost
		HttpPost httppost = new HttpPost(URL);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.addHeader("tenantToken", token);
		httppost.setHeader("Accept", "application/json");
		try {
			//对参数进行编码，防止中文乱码
			StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
			s.setContentEncoding("UTF-8");
			httppost.setEntity(s);
			response = httpclient.execute(httppost);
			//获取相应实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				obj=EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			httpClose(response);
		}
		return obj;
	}



	/**
	 * 发送 post请求【表单类型】
	 * @param url
	 * @param param
	 */
	public static String doPost(String url, Map<String, String> param,String token) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient =  getCloseableHttpClient();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("tenantToken", token);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
				httpPost.setEntity(entity);
			}

			// 执行http请求
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resultString=EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClose(response);
		}
		return resultString;
	}


	private static void httpClose(CloseableHttpResponse response ){
		if (response != null) {
			try {
				EntityUtils.consume(response.getEntity());
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static String doPostForGateway(String url, Map<String, Object> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = getCloseableHttpClient();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}


    /**
     * 发送 post请求【表单类型】
     * @param url
     * @param param
     */
    public static String doPostByRas(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient =  getCloseableHttpClient();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }

            // 执行http请求
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultString=EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClose(response);
        }
        return resultString;
    }

	public static String send(String url, JSONObject jsonObject) throws ParseException, IOException{
		String body = "";

		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		//装填参数
		StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		//设置参数到请求对象中
		httpPost.setEntity(s);
//        System.out.println("请求参数："+nvps.toString());

		httpPost.setHeader("Content-type", "application/json");
//		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity,"UTF-8");
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}
}
