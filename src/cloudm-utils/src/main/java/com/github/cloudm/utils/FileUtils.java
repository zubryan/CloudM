package com.github.cloudm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * 文件操作
 * 
 * @author andy.wan
 * 
 */
public class FileUtils {

	private static final Logger LOGGER = Logger.getLogger(FileUtils.class);

	/**
	 * 判断目录是否存在，否则创�?
	 * 
	 * @param path
	 *            文件路径
	 */
	public static void isExist(String path) {
		File fd = null;
		try {
			fd = new File(path);
			if (!fd.exists()) {
				fd.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fd = null;
		}
	}

	/**
	 * 创建单个文件
	 * 
	 * @param destFileName
	 *            文件�?
	 * @return 创建成功返回true，否则返回false
	 */
	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			return false;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}

		// 创建目标文件
		try {
			if (file.createNewFile()) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param f
	 * @return
	 */
	private static boolean deleteFile(File f) {
		LOGGER.info("DELETE: " + f.getPath());
		if (f.isFile())
			return f.delete();
		return f.delete();
	}

	/**
	 * 
	 * @param path
	 * @param pattern
	 * @return
	 */
	public static boolean delete(String path, String pattern){
		boolean result = false;
		try {
			if (path == null || pattern == null)
				throw new IllegalArgumentException("参数为空");
			result = true;

			File root = new File(path);
			if (!root.isDirectory() || !root.exists())
				throw new IllegalArgumentException("不是目录或者不存在!");

			List<File> folderBuffer = new ArrayList<File>();
			folderBuffer.add(root);

			String[] ps = pattern.split("\\" + File.separator);
			for (int i = 0; i < ps.length; i++) {
				List<File> tmpList = new ArrayList<File>();
				while (folderBuffer.size() > 0) {
					File folder = folderBuffer.remove(0);
					List<File> fileMatched = applyPattern(folder, ps[i]);
					tmpList.addAll(fileMatched);
				}
				folderBuffer.addAll(tmpList);

				if (folderBuffer.size() == 0)
					return true;
			}
			for (int i = 0; i < folderBuffer.size(); i++) {
				boolean r = deleteFile(folderBuffer.get(i));
				if (!r)
					return r;
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("删除文件异常");
		}
		return result;
	}

	private static List<File> applyPattern(File folder, String pattern) {
		List<File> list = new ArrayList<File>();
		if (folder == null || folder.isFile() || pattern == null)
			return list; // 空List
		Pattern p = createPattern(pattern);

		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String name = file.getName();
			Matcher m = p.matcher(name);
			if (m.matches()) {
				list.add(file);
				LOGGER.info("[ M] Folder: " + folder + ", Pattern: " + pattern
						+ ", File: " + name + " <-----Matched----");
			} else {
				LOGGER.info("[NM] Folder: " + folder + ", Pattern: " + pattern
						+ ", File: " + name);
			}
		}

		return list;
	}

	private static Pattern createPattern(String pattern) {
		String _pattern = pattern.replaceAll("\\.", "\\\\.").replaceAll("\\*",
				".*");
		Pattern p = Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE);
		return p;
	}

	public static void main(String[] args) throws Exception {
//		FileUtils.delete("c:/", "*.vm");
		List<String> lines = IOUtils.readLines(new FileInputStream("c:/createTenant.vm"), "UTF-8");
		
		IOUtils.writeLines(lines, null, new FileOutputStream("d:/x.lecast"));
	}
}
