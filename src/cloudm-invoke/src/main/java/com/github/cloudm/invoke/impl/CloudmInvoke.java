package com.github.cloudm.invoke.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cloudm.invoke.ICloudmInvoke;
import com.github.cloudm.invoke.httpclient.CommonInvoker;
import com.github.cloudm.invoke.httpclient.InvokeParameter;
import com.github.cloudm.invoke.httpclient.ResolveTemplate;
import com.github.cloudm.invoke.httpclient.ResponseObject;
import com.github.cloudm.utils.Constants;


@Service
public class CloudmInvoke implements ICloudmInvoke {

	private static final Logger LOGGER = Logger.getLogger(CloudmInvoke.class);
	
	@Autowired
	private ResolveTemplate template;
	
	/**
	 * 调用远程iaas
	 * 
	 * @param method
	 *            方法名称
	 * @param url
	 *            模版url内容
	 * @param urlParameters
	 *            url的参�?
	 * @param header
	 *            请求�?
	 * @param templateName
	 *            模版名称
	 * @param bodyMap
	 *            请求体参�?
	 * @return ResponseObject 响应对象
	 * @throws IllegalAccessException
	 */
	public ResponseObject createInvoke(InvokeParameter invokeParameter) {
		ResponseObject res = null;
		String bodyStr = null;
		LOGGER.debug(">>>begin invoke...");
		// 入口对象不能为空
		if (invokeParameter != null) {
			String method = invokeParameter.getMethod();// 获取方法名称
			String url = invokeParameter.getUrl();// 获取URL
			Object[] urlParameters = invokeParameter.getUrlParameters();// 获取URL参数
			Map<String, Object> parasMap = invokeParameter.getParaMap();
			Map<String, String> header = new HashMap<String, String>();

			// 获取设置超时时间
			Integer timeout = invokeParameter.getTimeout();

			if (parasMap!=null) {
				if (parasMap.containsKey(Constants.HEAD_USERNAME)
						&& parasMap.containsKey(Constants.HEAD_PASSWORD)) {
					header.put(Constants.HEAD_USERNAME,String.valueOf(parasMap.get(Constants.HEAD_USERNAME)));
					header.put(Constants.HEAD_PASSWORD,String.valueOf(parasMap.get(Constants.HEAD_PASSWORD)));
					// 移除请求头的信息
					parasMap.remove(Constants.HEAD_USERNAME);
					parasMap.remove(Constants.HEAD_PASSWORD);
				}
			}
			// 获取模版名称
			String templateName = invokeParameter.getTemplateName();
			// 判断请求方法和请求的URL路径不能为空
			if (StringUtils.isNotBlank(method) && StringUtils.isNotBlank(url)) {
				CommonInvoker invoker = new CommonInvoker();
				// 解析url的路�?
				String newUrl = MessageFormat.format(url, urlParameters);
				/**
				 * 请求方法为GET或�?DELETE,不存在请求体BODY
				 */
				if (method.equalsIgnoreCase(Constants.HTTP.GET)
						|| method.equalsIgnoreCase(Constants.HTTP.DELETE)) {
					res = invoker.excute(newUrl, method, header, null, timeout);
				} else {// 请求方法为PUT或�?POST
					if (StringUtils.isNotBlank(templateName)
							&& !parasMap.isEmpty()) {
						bodyStr = template
								.generatorBody(templateName, parasMap);
						res = invoker.excute(newUrl, method, header, bodyStr,
								timeout);
					} else {
						LOGGER.debug("invoke parasBody or templateName is null!");
						throw new IllegalAccessError("invoke parasBody or templateName is null!");
					}
				}
			} else {// 请求方法和请求URL都为空运行时异常
				LOGGER.debug("invoke method or url is null!");
				throw new IllegalAccessError("invoke method or url is null!");
			}
		} else {
			LOGGER.debug("request object is null!");
			throw new IllegalAccessError("request object is null");
		}
		return res;
	}

}
