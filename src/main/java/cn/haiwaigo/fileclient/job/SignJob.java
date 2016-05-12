package cn.haiwaigo.fileclient.job;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import cn.haiwaigo.fileclient.context.SystemConstant;
import cn.haiwaigo.fileclient.util.FileUtils;
import cn.haiwaigo.fileclient.util.SignUtil;

/**
 * 
 * @author linx
 *
 */
public class SignJob {

 private static Logger logger = Logger.getLogger(SignJob.class);
   // 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)  
   public static void signTask() {  
       Timer timer = new Timer();  
       timer.schedule(new TimerTask() {  
           public void run() {  
           	excute();
           }  
       }, 1000,10000);// 设定延时1秒，每次10秒执行一次  
   }  
    public static void excute(){
        logger.info("加签发送报文定时启动");
        //循环处理
        	//加签
        	//ws发送
        File dir = new File(SystemConstant.LOCAL_RECEIVE_PATH);
		File[] listF = dir.listFiles();
		if(listF!=null && listF.length>0){
			Arrays.sort(listF);
			int num = listF.length;
			for (int i = 0; i < num; i++) {
				try {
					String xmlStr = FileUtils.getFileContent(listF[i]
							.getAbsolutePath());
					//加签
					String newXml = SignUtil.sign(xmlStr);
		        	//ws发送
					//WebserviceUtils.put(newXml, SystemConstant.WEB_SERVICE_URL, SystemConstant.WEB_SERVICE_METHOD);
					//listF[i].delete();
				} catch (Exception e) {
					logger.error("-----------单个处理加签报文失败，原因：", e);
				}
			}
		}
        logger.info("加签发送报文定时结束");
    }
    
    
    
}
