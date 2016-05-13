package cn.haiwaigo.fileclient.job;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import cn.haiwaigo.fileclient.context.SystemConstant;
import cn.haiwaigo.fileclient.exception.FTPClientException;
import cn.haiwaigo.fileclient.util.FtpUtils;

public class MoveFtpFileJob {
	private static Logger logger = Logger.getLogger(MoveFtpFileJob.class);
	  // 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)  
    public static void moveFtpFileToTemp() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
            	excute();
            }  
        }, 1000,10000);// 设定延时1秒，每次10秒执行一次  
    }  
	
    public static  void excute(){
        logger.info("ftp移动报文定时启动");
        //获取ftp文件 
		try {
			int fileSize = FtpUtils.listSize(SystemConstant.FTP_TEMP_PATH);
			if(SystemConstant.NUM >fileSize){
				 String[] fileLists = FtpUtils.listNames(SystemConstant.FTP_RECEIVE_PATH);
				if(fileLists!=null){
					int needNum =SystemConstant.NUM - fileSize;
					int num = fileLists.length >= needNum ? needNum:fileLists.length;
					Arrays.sort(fileLists);
					for (int i = 0; i < num; i++) {
						String fromName = fileLists[i];
						String toName=SystemConstant.FTP_TEMP_PATH+fromName.substring(fromName.lastIndexOf("/")+1);
						FtpUtils.rename(fromName, toName);
					}
				}
			}
		} catch (FTPClientException e) {
			 logger.info("ftp移动报文异常"+e);
			e.printStackTrace();
		}
        logger.info("ftp移动报文定时结束");
    }
	
}
