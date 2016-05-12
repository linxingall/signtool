package cn.haiwaigo.fileclient.exception;
/**
 * 
* @ClassName: ServiceException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author linx
* @date 2016年5月12日 下午2:42:34 
*
 */
public class ServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -377659176558526362L;

	public ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
