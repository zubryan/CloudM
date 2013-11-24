package com.github.cloudm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * 配置�?
 * 
 * @author andy.wan
 *
 */
public class Configure {
	
	private static Properties props = new Properties();
	
	static { 
		props = new Properties(); 
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("common-invoke.properties");
        try { 
        	props.load(in); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {
			IOUtils.closeQuietly(in);
		}
    } 
	
	public static String getValue(String key) {
		return props.getProperty(key);
	}
	
	/**
	 * 取出Property�?
	 */
	public static String getValues(String key) {
		String systemProperty = System.getProperty(key);
		if (systemProperty != null) {
			return systemProperty;
		}
		return props.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(Configure.getValues("iaas_http"));
	}
}
