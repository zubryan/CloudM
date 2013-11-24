package com.github.cloudm.invoke;

import com.github.cloudm.invoke.httpclient.InvokeParameter;
import com.github.cloudm.invoke.httpclient.ResponseObject;

public interface ICloudmInvoke {

	/**
	 * piaas调用iaas入口
	 * 
	 * @param invokeParameter 调用参数对象
	 * @return ResponseObject 响应对象
	 */
	public ResponseObject createInvoke(InvokeParameter invokeParameter);
}
