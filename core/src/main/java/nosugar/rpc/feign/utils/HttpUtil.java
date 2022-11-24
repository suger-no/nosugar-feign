package nosugar.rpc.feign.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nosugar.rpc.feign.entity.FeignRequest;
import nosugar.rpc.feign.exception.HttpException;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestMethod;

public class HttpUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private HttpUtil() {
	}
 
	/**
	 * 发送get请求
	 *
	 * @param url    地址
	 * @param params 参数
	 * @return 请求结果
	 */
	public static byte[] get(String url, Map<String, String> params) {
		return request("GET", url, params);
	}
 
	/**
	 * 发送post请求
	 *
	 * @param url    地址
	 * @param params 参数
	 * @return 请求结果
	 */
	public static byte[] post(String url, Map<String, String> params) {
		return request("POST", url, params);
	}

	/**
	 * 发送put请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static byte[] put(String url, Map<String, String> params) {
		return request("PUT", url, params);
	}

	/**
	 * 发送delete请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static byte[] delete(String url, Map<String, String> params) {
		return request("DELETE", url, params);
	}

	public static byte[] request(FeignRequest request){
		String host = request.getHost();
		Integer port = request.getPort();
		String path = request.getPath();
		Map<String, String> param = request.getParam();
		RequestMethod method = request.getMethod();
		if (!path.startsWith("/")){
			path = "/"+path;
		}
		String url = "http://"+host+":"+port+path;
		return request(method.name(),url,param);
	}

	/**
	 * 发送http请求
	 *
	 * @param method 请求方法
	 * @param url    地址
	 * @param params 参数
	 * @return 请求结果
	 */
	public static byte[] request(String method, String url, Map<String, String> params) {
 
		if (method == null) {
			throw new RuntimeException("请求方法不能为空");
		}
 
		if (url == null) {
			throw new RuntimeException("url不能为空");
		}
 
		HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
 


		Request request = null;
		if (!method.equals("GET")){
			try {
				RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsBytes(params));
				request = new Request.Builder()
						.url(httpBuilder.build())
						.method(method,requestBody)
						.build();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}else {
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					httpBuilder.addQueryParameter(param.getKey(), param.getValue());
				}
			}
			request = new Request.Builder()
					.url(httpBuilder.build())
					.get().build();
		}

		Response response = null;
		try {
			OkHttpClient client = new OkHttpClient.Builder()
					.readTimeout(20, TimeUnit.SECONDS)
					.build();
			response = client.newCall(request).execute();
			return response.body().bytes();
		} catch (IOException e) {
			throw new HttpException("请求时出错");
		}finally {
			response.close();
		}
	}
 
//	/**
//	 * 发送post请求（json格式）
//	 *
//	 * @param url  url
//	 * @param json json字符串
//	 * @return 请求结果
//	 */
//	public static String postJson(String url, String json) {
//
//		Request request = new Request.Builder()
//				.url(url)
//				.post(RequestBody.Companion.create(json, MediaType.Companion.parse("application/json")))
//				.build();
//
//		try {
//			OkHttpClient client = new OkHttpClient();
//			Response response = client.newCall(request).execute();
//			return response.body().string();
//		} catch (IOException e) {
//			return null;
//		}
//	}
}