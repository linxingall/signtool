package cn.haiwaigo.fileclient.util;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * properties文件读取 工具类
 * @author </a href="mailto:jinwx@yintong.com.cn">jinwx</a>
 * @version $Id: ProFileUtil.java, v 0.1 Mar 10, 2014 10:40:19 AM jinwx Exp $ 
 * @since 1.0
 */
public class PropertiesUtil {
	

	private static PropertyResourceBundle props = PropertiesUtil.getProps("config.properties");
	
	
    /**
     * 
     * 读取配置文件内容
     * @param proFile
     * @return
     * @throws Exception 
     */
    public static PropertyResourceBundle getProps(String proFile) {
    	try{
	        //判断空
	        if (StringUtils.isEmpty(proFile)) {
	            return null;
	        }
	        PropertyResourceBundle props = null;
	        InputStream fis = null;
	        fis = PropertiesUtil.class.getClassLoader().getResourceAsStream(proFile);
	        props = new PropertyResourceBundle(fis);
	        //关闭流
	        return props;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    /**
     * 
     * 获取配置文件中某个KEY的VALUE
     * @param proFile
     * @param prop
     * @throws Exception 
     */
    public static String getPropValue(String proFile, String prop){
    	try{
	        //判断空
	        if (StringUtils.isEmpty(proFile) || StringUtils.isEmpty(prop)) {
	            return null;
	        }
	        PropertyResourceBundle props = getProps(proFile);
	        if (null != props) {
	            return props.getString(prop);
	        }
	        return "";
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
   
    public static String getValue(String key){
    	try{
	        //判断空
	        if (StringUtils.isEmpty(key)) {
	            return null;
	        }
	        if (null != props) {
	            return props.getString(key);
	        }
	        return "";
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    

}
