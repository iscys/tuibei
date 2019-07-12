package com.tuibei.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;


/**
 * spring 提供的文件工具类->FileSystemContextResource
 * @author iscys
 *
 */
public class ResourceUtils {

	static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
	
	
	/**
	 * 加载项目中的文件转换为文件File对象
	 * 如果文件在classpath 目录下，resourceName = classes下的相对路径 eg->spring/applicationContext.xml
	 * 如果查找的文件在磁盘中，resourceName=磁盘绝对路径：eg->D:\\tomcat7.0.59\\apache-tomcat-7.0.59\\
	 * 首先先会到项目路径下找
	 * 然后才会到系统文件中去找
	 * ..可以返回上一级目录
	 * @throws Exception 
	 */
	
	public static File getFile(String resourceName) throws Exception {
		
		notNull(resourceName, "文件名不能为空");
		ClassLoader loader = getClassLoader();
		//判断是否在classpath目录下
		URL url = loader.getResource(resourceName);
		if(url==null) {
			//去磁盘中获取文件
			File file = new File(resourceName);
			if(!file.exists()) {throw new IOException("文件不存在");}
			return file;
		}
		else {
			//创建classpath 目录下的文件对象,utils使用spring的
			URI uri = toURI(url.toString());
			return new File(uri.getSchemeSpecificPart());
			
		}
		
	}
	
	/**
	 * 创建URL实例为给定的location
	 * @param location
	 * @return
	 * @throws URISyntaxException
	 */
	public static URI toURI(String location) throws URISyntaxException {
		return new URI(StringUtils.replace(location, " ", "%20"));
	}


	/**
	 * 项目classpath 目录下单个 的文件加载到properties 中，
	 * @param resouceName   fromatLocation:properties/properties
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	
	public static Properties loadSingleProperties(String resouceName)  {
		
		notNull(resouceName, "文件名不能为空");
		InputStream  is = null;
		//容错，去除第一位的"/";
		String hasSeparate = resouceName.substring(0, 1);
		if(hasSeparate.equals("/") ||hasSeparate.equals("\\")){
			resouceName=resouceName.substring(1);
		}
		Properties prop =new Properties();
		ClassLoader loader = getClassLoader();
		try {
		is = loader.getResourceAsStream(resouceName);
		prop.load(is);
		}catch (Exception e){

		}
		finally {
			IOUtils.closeQuietly(is);
		}

		return prop;
		
	}

	/**
	 * 加载所有的 指定名称的 Properties 文件（是项目下所有符合的名称）
	 * @return
	 */
	public static  Properties loadAllProperties(String resouceName) throws IOException{
		notNull(resouceName, "文件名不能为空");
		//容错，去除第一位的"/";
		String hasSeparate = resouceName.substring(0, 1);
		if(hasSeparate.equals("/") ||hasSeparate.equals("\\")){
			resouceName=resouceName.substring(1);
		}
		ClassLoader loadr = getClassLoader();
		Properties props = new Properties();
		Enumeration<URL> urls = loadr.getResources(resouceName);
		while(urls.hasMoreElements()){
			URL url = urls.nextElement();
			InputStream is = url.openStream();//url.openConnection() +getInputStream()组合体
			try {
				props.load(is);
			}finally {
				is.close();
			}
		}
		return props;

	}


	/**
	 * 会扫描所有的文件
	 * @param directRoot
	 * @return
	 * @throws Exception
	 */
	public static LinkedList<File> scanPackage(String directRoot) throws Exception{
		LinkedList<File> result =new LinkedList<File>();
		File dir = getFile(directRoot);
		return doRetrieveMatchingFiles(dir,result,null);
	}
	/**
	 * 获取指定文件目录下的所有匹配的文件
	 */
	public static LinkedList<File> doRetrieveMatchingFiles(File dir,LinkedList<File> result,String matchWord) throws Exception{

		File[] files = dir.listFiles();
		for(File f :files){
			if(f.isDirectory()){//判断是否是文件还是目录
				doRetrieveMatchingFiles(f,result,matchWord);
			} else {
				if (!StringUtils.isEmpty(matchWord)) {
					if (f.getName().matches(".*" + matchWord + ".*")) {
						result.add(f);
					}
				} else {
					System.out.println(f.getAbsolutePath());
					result.add(f);
				}
			}
		}
		return result;

	}


	/**
	 * 将"." 替换成"/"
	 * @param basePackage
	 * @return
	 */
	public static String resolveBasePackage(String basePackage){
		return basePackage.replace('.', '/');
	}
	
	/**
	 * 获取类加载器
	 */
	
	public static  ClassLoader getClassLoader() {
		
		
		ClassLoader loader = null;
		try {
			loader = Thread.currentThread().getContextClassLoader();
		}catch(Exception e) {}
		if(loader==null) {
		 loader = ResourceUtils.class.getClassLoader();
		}
		return loader;
	}
	
	/**
	 * 检测抛异常
	 */
	
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}


	public static void main(String[] args)throws Exception {
		ClassLoader loader = getClassLoader();
		URL com =loader.getResource("");
		System.out.println(com);

	}

}
