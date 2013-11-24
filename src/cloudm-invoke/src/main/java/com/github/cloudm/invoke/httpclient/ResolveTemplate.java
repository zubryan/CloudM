package com.github.cloudm.invoke.httpclient;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.cloudm.utils.Constants;
import com.github.cloudm.utils.FileUtils;
import com.github.cloudm.utils.SystemPropertyUtils;

/**
 * 模版解析�?
 * 
 * @author andy.wan
 * 
 */
@Service
public class ResolveTemplate {
	
	@Value("${default.win}" )
    private String defaultWinRootPath ;

	@Value("${default.linux}" )
	private String defaultLinuxRootPath ;

	public ResolveTemplate() {
	}

	/**
	 * 生成脚本到指定目录下
	 * 
	 * @param templateName
	 *            模版名称
	 * @param paras
	 *            参数和�?
	 * @param genteratorFilePath
	 *            生成脚本路径
	 * @throws Exception
	 */
	public void createData(String templateName, Map<String, String> paras,
			String genteratorFilePath) throws Exception {
		VelocityContext context = new VelocityContext();
		FileWriter fw = null;
		try {
			for (Entry<String, String> entry : paras.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				context.put(key, value);
			}
			String body = processTemplate(templateName, context);
			if (StringUtils.isNotBlank(genteratorFilePath)) {
				boolean flag = FileUtils.createFile(genteratorFilePath);
				if (flag) {
					fw = new FileWriter(genteratorFilePath, true);
					fw.write(body);
					/*pw = new PrintWriter(fw);
					pw.println(body);*/
				}
			}
		} finally {
			if (fw != null)
				fw.close();
		}
	}

	/**
	 * 处理模版,结合模版和参数生成字符串
	 * 
	 * @param templateName
	 *            模版名称
	 * @param context
	 *            参数内容
	 * @return
	 * @throws Exception
	 */
	public String generatorBody(String templateName,Map<String, Object> parasBody){
		VelocityContext cont = new VelocityContext();
		for (Entry<String, Object> entry : parasBody.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			cont.put(key, value);
		}
		String body="";
		body=processTemplate(templateName, cont);
		return body;
	}
	
	/**
	 * 处理模版,结合模版和参数生成字符串
	 * 
	 * @param templateName
	 *            模版名称
	 * @param context
	 *            参数内容
	 * @return
	 * @throws Exception
	 */
	private String processTemplate(String templateName,VelocityContext context){
		Writer writer=null;
		try {
			Velocity.setProperty(Velocity.INPUT_ENCODING, "utf-8");// 设置输入字符�?
			if (SystemPropertyUtils.isWindowsOS()) {
				Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,defaultWinRootPath);
			} else if (SystemPropertyUtils.isLinuxOS()) {
				Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,defaultLinuxRootPath);
			}
			Velocity.init();
			String templatePath=templateName+Constants.TEMPLATE_SUFFIX_NAME;
			Template template = Velocity.getTemplate(templatePath);
			writer = new StringWriter();
			template.merge(context, writer);
		} catch (Exception e) {
			try {
				throw new IllegalAccessException("resolve template and parameters is exception!!!");
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		return writer.toString();
	}

}
