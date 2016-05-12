package cn.haiwaigo.fileclient.context;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 
* @ClassName: Global 
* @Description: TODO(全局容器) 
* @author linx
* @date 2016年5月12日 下午2:43:30 
*
 */
public class Global {
	public static ConcurrentHashMap<String, String> globalMap = new ConcurrentHashMap<String, String>();

}
