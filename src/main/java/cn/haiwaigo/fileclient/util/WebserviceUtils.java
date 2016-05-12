package cn.haiwaigo.fileclient.util;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;

/**
 * 
 * @author linx
 *
 */
public class WebserviceUtils {
	Logger logger = Logger.getLogger(WebserviceUtils.class);

	public static String put(String xmlContent, String urlStr, String methodStr) throws ServiceException, MalformedURLException, RemoteException {
		Service service = new Service();
		Call call;
		call = (Call) service.createCall();
		call.setTimeout(10000); // 设置超时时间为10秒
		call.setTargetEndpointAddress(new java.net.URL(urlStr));// 设置service所在URL
		call.setOperationName(new QName(urlStr, methodStr));
		call.setUseSOAPAction(true);
		// 设置返回值类型
		call.setReturnType(XMLType.XSD_STRING); // 返回值类型：String
		String result = (String) call.invoke(new Object[] { xmlContent });// 远程调用
		return result;
	}
}
