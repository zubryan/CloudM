package com.github.cloudm.invoke.httpclient;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.github.cloudm.utils.Configure;
import com.github.cloudm.utils.Constants;


/**
 * 公共调用�?,当前类支�?GET\POST\PUT\DELETE方法
 * 例如: CommonInvoker invoker = new CommonInvoker(); 
 * ResponseObject res = invoker.excute(url, "get", header, null);
 * 其中:url为请求的地址;"get"为请求方�?header为请求头信息;如果请求方法�?put' 或�? 'post' 则最后参数为请求体body信息
 * 
 * @author andy.wan
 * 
 */
public final class CommonInvoker {
	private static final Logger LOGGER = Logger.getLogger(CommonInvoker.class);

	/**
	 * 方法总入�?
	 * 
	 * @param url
	 * @param method GET\POST\DELETE
	 * @param header request header
	 * @param json request json body
	 * @return ResponseObject 响应对象
	 */
	public ResponseObject excute(String url, String method,
			Map<String, String> header, String json,Integer timeout) {
		// 设置超时
		if (method.equalsIgnoreCase(Constants.HTTP.GET)) {
			return sendGetMethod(url, header,timeout);
		}
		if (method.equalsIgnoreCase(Constants.HTTP.POST)) {
			return sendPostMethod(url, json, header,timeout);
		}
		if (method.equalsIgnoreCase(Constants.HTTP.PUT)) {
			return sendPutMethod(url, json, header,timeout);
		}
		if (method.equalsIgnoreCase(Constants.HTTP.DELETE)) {
			return sendDeleteMethod(url, header,timeout);
		}
		ResponseObject res = new ResponseObject();
		res.setEntity("request method unknown!");
		return res;
	}

	
	/**
	 * 通过请求对象获取用户请求头信�?
	 * 
	 * @param header
	 *            请求头信�?
	 * @return Header[] 请求头数�?
	 */
	public Header[] getHeadByRequest(Map<String, String> header) {
		Header headerUserName = new BasicHeader(Constants.HEAD_USERNAME,
				header.get(Constants.HEAD_USERNAME));
		Header headerPassWord = new BasicHeader(Constants.HEAD_PASSWORD,
				header.get(Constants.HEAD_PASSWORD));

		Header contentType = new BasicHeader("content-type","application/json");
		Header[] headers = new Header[] { headerUserName, headerPassWord,contentType };
		
		
		

		return headers;
	}

	/**
	 * 响应实体
	 * 
	 * @param requestMethod
	 *            请求方法
	 * @return ResponseObject 响应对象
	 */
	public ResponseObject responseEntity(HttpUriRequest requestMethod,Integer timeout) {
		ResponseObject resObj = new ResponseObject();
		String errorMessage= null;
		HttpClient httpClient = null;
		try {
			httpClient = new DefaultHttpClient();
			// 设置网络链接超时
			if(timeout==null||timeout==0){
				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20*1000);//连接超时20�?
			}else{
				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout*1000);//连接超时20�?
			}
			//设置网络传输内容为默�?0�?
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  60*1000);//数据传输时间60s
	        
			HttpResponse httpResponse = httpClient.execute(requestMethod);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("[ response status code is >>>"+statusCode+" ]");
			if (statusCode >= HttpStatus.SC_OK
					&& statusCode < HttpStatus.SC_TEMPORARY_REDIRECT) {
				// 取出回应字串
				resObj.setEntity(EntityUtils.toString(httpResponse.getEntity()));
				Header[] headers = httpResponse.getAllHeaders();
				Map<String, String> responseHeaders = new HashMap<String, String>();
				for (Header h : headers) {
					responseHeaders.put(h.getName(), h.getValue());
				}
				resObj.setHeaders(responseHeaders);
				resObj.setStatus(httpResponse.getStatusLine().getStatusCode());
			} else if(statusCode==HttpStatus.SC_CONFLICT){
				throw new Exception(">>>>parameter is conflict!");
			}else if(statusCode==Constants.OS_API){
				throw new Exception(">>>>OS API error!");
			}else {
				resObj.setStatus(statusCode);
				resObj.setErrorMessage(errorMessage);
			}
		} catch (UnknownHostException uh) {
			LOGGER.error(uh.getMessage());
			this.setException(resObj, uh, 508, Configure.getValue(Constants.DNS_RESOLVE));
		}catch (ConnectTimeoutException ec) {
			LOGGER.error(ec.getMessage());
			this.setException(resObj, ec, HttpStatus.SC_REQUEST_TIMEOUT, Configure.getValue(Constants.TIMEOUT));
		}catch (HttpHostConnectException hc) {
			LOGGER.error(hc.getMessage());
			this.setException(resObj, hc,509, Configure.getValue(Constants.REFUSED));
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			resObj.setStatus(500);
			resObj.setErrorMessage(e.getMessage());
			resObj.setOptionErrorMessage(Configure.getValue(Constants.OTHERS));
		} finally {
			httpClient = null;
		}
		return resObj;
	}

	/**
	 * 发�?GET方法
	 * 
	 * @param url
	 *            URL地址
	 * @param header
	 *            请求头信�?
	 * @return ResponseObject 响应对象
	 */
	private ResponseObject sendGetMethod(String url, Map<String, String> header,Integer timeout) {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("http get url is exception!");
		}
		// 建立连接
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);
			LOGGER.debug("[ Get request url:======>" + url+" ]");
			httpGet.setHeaders(getHeadByRequest(header));
			return this.responseEntity(httpGet,timeout);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("http get method is exception!");
		} finally {
			if (httpGet != null) {
				if (!httpGet.isAborted()) {
					httpGet.abort();
				}
				httpGet.releaseConnection();
				httpGet = null;
			}
		}
	}

	/**
	 * 发�?POST方法
	 * 
	 * @param url
	 *            URL地址
	 * @param jsonBody
	 *            请求body�?
	 * @return ResponseObject 响应对象
	 */
	private ResponseObject sendPostMethod(String url, String jsonBody,
			Map<String, String> header,Integer timeout) {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("http post url is exception!");
		}
		// 建立post连接
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			// 调用请求�?
			this.getHeadByRequest(header);
			httpPost.setHeaders(getHeadByRequest(header));
			LOGGER.debug("[ Post request url:======>" + url+" ]");
			LOGGER.debug("<<<<:post request object to json:>>>>:\n" + jsonBody);
			// 发出请求
			HttpEntity entity = new StringEntity(jsonBody, "UTF-8");
			httpPost.setEntity(entity);
			return this.responseEntity(httpPost,timeout);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("http post method is exception!");
		} finally {
			if (httpPost != null) {
				if (!httpPost.isAborted()) {
					httpPost.abort();
				}
				httpPost.releaseConnection();
				httpPost = null;
			}
		}
	}

	/**
	 * 发�?PUT方法
	 * 
	 * @param url
	 *            URL地址
	 * @param jsonBody
	 *            请求body�?
	 * @return ResponseObject 响应对象
	 */
	private ResponseObject sendPutMethod(String url, String jsonBody,
			Map<String, String> header,Integer timeout) {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("http put url is exception!");
		}
		// 建立post连接
		HttpPut httpPut = null;
		try {
			httpPut = new HttpPut(url);
			// 调用请求�?
			this.getHeadByRequest(header);
			httpPut.setHeaders(getHeadByRequest(header));
			LOGGER.debug("[ Put request url:======>" + url+" ]");
			LOGGER.debug("<<<<:put request object to json:>>>>:\n" + jsonBody);
			HttpEntity entity = new StringEntity(jsonBody, "UTF-8");
			httpPut.setEntity(entity);
			return this.responseEntity(httpPut,timeout);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("http put method is exception!");
		} finally {
			if (httpPut != null) {
				if (!httpPut.isAborted()) {
					httpPut.abort();
				}
				httpPut.releaseConnection();
				httpPut = null;
			}
		}
	}

	/**
	 * 发�?DELETE方法
	 * 
	 * @param url
	 *            url地址
	 * @param header
	 *            请求�?
	 * @return ResponseObject 响应对象
	 */
	private ResponseObject sendDeleteMethod(String url,
			Map<String, String> header,Integer timeout) {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("http delete url is exception!");
		}
		HttpDelete httpDelete = null;
		try {
			httpDelete = new HttpDelete(url);
			// 调用请求�?
			this.getHeadByRequest(header);
			httpDelete.setHeaders(getHeadByRequest(header));
			LOGGER.debug("[ Delete request url:======>" + url+" ]");
			return this.responseEntity(httpDelete,timeout);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"http delete method is exception!");
		} finally {
			if (httpDelete != null) {
				if (!httpDelete.isAborted()) {
					httpDelete.abort();
				}
				httpDelete.releaseConnection();
				httpDelete = null;
			}
		}
	}

	/**
	 * 异常信息统一处理
	 * 
	 * @param resObj 响应对象
	 * @param ec 异常�?
	 * @param optionStatus 自定义状态编�?
	 * @param optionErrorMessage 自定义错误信�?
	 */
	private void setException(ResponseObject resObj, Exception ec,Integer optionStatus,String optionErrorMessage) {
		String errorMessage="";
		resObj.setStatus(optionStatus);//链接超时代码
		if(ec instanceof UnknownHostException){
			errorMessage=ec.getMessage();
		}
		if(ec instanceof ConnectTimeoutException){
			errorMessage=ec.getMessage();
		}
		if(ec instanceof HttpHostConnectException){
			errorMessage=ec.getMessage();
		}
		resObj.setErrorMessage(errorMessage);
		resObj.setOptionErrorMessage(optionErrorMessage);
	}
	

}
