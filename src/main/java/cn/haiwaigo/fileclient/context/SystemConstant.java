package cn.haiwaigo.fileclient.context;

import cn.haiwaigo.fileclient.util.PropertiesUtil;

/**
 * 
* @ClassName: SystemConstant 
* @Description: TODO(常量类) 
* @author linx
* @date 2016年5月12日 下午2:42:59 
*
 */
public class SystemConstant {
	//用户FTP账号
	public final static String FTPUSERNAME = "ftpUserName";
	//用户FTP密码
	public final static String FTPPASSWD = "ftpPasswd";
	//每一次移动多少个报文到临时文件夹
	public final static int NUM =10;
	
	public final static String RATE_DEFAULTE = "defaul";
	// 成功
	public final static String SUCCESS = "success";
	// 失败
	public final static String FAILURE = "failure"; 
	//字符编码
	public final static String ENCODING = "UTF-8";
	public static final String FTP_RECEIVE_PATH = PropertiesUtil.getValue("ftp.receive.path");//远程服务器接收地址
	public static final String LOCAL_RECEIVE_PATH = PropertiesUtil.getValue("local.receive.path");//本地保存地址
	public static final String SPLIT_STR="##";//二维码分隔符
	public static final String VERSION="1.0";
	public static final String SIGN_TYPE="MD5";
	
	public static final String WEB_SERVICE_URL = PropertiesUtil.getValue("webservice.url");//请求URL
	public static final String WEB_SERVICE_METHOD = PropertiesUtil.getValue("webservice.method");//请求方法

	
}
