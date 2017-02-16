package org.demo.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PropertyUtils {
	private static Logger logger = Logger.getLogger(Properties.class);

	private static Properties prop = new Properties();
	private static final String DEFAULT_FILE_PATH = "config.properties";

	private PropertyUtils() {
	}

	static {
		loadFile(DEFAULT_FILE_PATH);
	}

	public static void loadFile(String classpath) {
		try {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource resource = resolver.getResource("classpath:" + classpath);
			InputStream inStream = resource.getInputStream();
			prop.load(inStream);
		} catch (IOException ioe) {
			logger.error("读取配置文件异常,文件不存在.", ioe);
		}
	}

	/**
	 * 获取属性值,默认返回""
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return prop.getProperty(key, "");
	}

}
