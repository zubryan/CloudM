package com.github.cloudm.invoke.httpclient;

import java.io.Serializable;
import java.util.Map;

/**
 * 调用iaas入口的请求参�?
 * 
 * @author andy.wan
 *
 */
public class InvokeParameter implements Serializable {

	private static final long serialVersionUID = -2197062926106826985L;

	/** 方法名称 */
	private String method;
	
	/** 模版url内容 */
	private String url;
	
	/** url的参�?*/
	private Object[] urlParameters;
	
	/** 模版名称 */
	private String templateName;
	
	/** 请求体参�?*/
	private Map<String,Object> paraMap;
	
	/**超时*/
	private Integer timeout;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, Object> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}

	public Object[] getUrlParameters() {
		return urlParameters;
	}

	public void setUrlParameters(Object[] urlParameters) {
		this.urlParameters = urlParameters;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public static enum MethodType {
		get(0), post(1), delete(2), put(3);
		int value;

		private MethodType(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}

		public static MethodType fromKey(Integer key) {
			for (MethodType methodType : MethodType.values()) {
				if (key.intValue() == methodType.value) {
					return methodType;
				}
			}
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(InvokeParameter.MethodType.post.value);
		System.out.println(InvokeParameter.MethodType.fromKey(1));
	}
}
