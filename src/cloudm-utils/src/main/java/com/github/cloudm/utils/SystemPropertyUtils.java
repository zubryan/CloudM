package com.github.cloudm.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 获取系统相关属性
 * 
 * @author andy.wan
 * 
 */
public class SystemPropertyUtils {
	/**
	 * 获取系统字符集
	 * 
	 * @return
	 */
	public static String getSystemChartset() {
		// 获取系统默认的字符集
		return System.getProperty("sun.jnu.encoding");
	}

	/**
	 * 获取系统操作系统名称，如Windows 7
	 * 
	 * @return
	 */
	public static String getOSName() {
		return System.getProperty("os.name");
	}

	/**
	 * 获取公网IP地址
	 * 
	 * @return IP Address
	 * @throws SocketException
	 */
	public static InetAddress getPublicAddress() throws SocketException {
		for (Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces(); interfaces.hasMoreElements();) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if (networkInterface.isLoopback() || networkInterface.isVirtual()
					|| !networkInterface.isUp()) {
				continue;
			}
			Enumeration<InetAddress> addresses = networkInterface
					.getInetAddresses();
			if (addresses.hasMoreElements()) {
				return addresses.nextElement();
			}
		}
		return null;
	}
	
	/** 
     * 判断运行环境是否是Windows 
     *  
     */  
    public static boolean isWindowsOS() {  
        return SystemPropertyUtils.getOSName().toUpperCase().indexOf("WINDOWS") != -1;  
    }  
  
    /** 
     * 判断运行环境是否是Linux 
     *  
     */  
    public static boolean isLinuxOS() {  
        return SystemPropertyUtils.getOSName().toUpperCase().indexOf("LINUX") != -1;  
    }  
}
