package cn.haiwaigo.fileclient.job;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import cn.haiwaigo.fileclient.context.Global;
import cn.haiwaigo.fileclient.context.SystemConstant;
import cn.haiwaigo.fileclient.exception.FTPClientException;
import cn.haiwaigo.fileclient.util.FtpUtils;

public class FtpFileJob {
	 private static Logger logger = Logger.getLogger(FtpFileJob.class);
	  // 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)  
    public static void getFileFromFtp() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
            	excute();
            }  
        }, 1000,10000);// 设定延时1秒，每次10秒执行一次  
    }  
	
    public static  void excute(){
        logger.info("ftp获取报文定时启动");
        //获取ftp文件 
        /**
         * recv temp error backup
         * 
         * job1 根据temp数量N，每次从 recv拿， 如果temp数量等于N 就不拿，小于拿
         * 
         * job2 下载temp的到本地
         * 
         * job3 多线程发送，根据发送结果删除temp下的文件，如果发送失败，文件重命名_N,尝试N次后移入error
         * 
         * 
         */
        String[] fileLists;
		try {
			fileLists = FtpUtils.listNames(SystemConstant.FTP_TEMP_PATH);
			if(fileLists!=null){
				Arrays.sort(fileLists);
				for (int i = 0; i < fileLists.length; i++) {
					String str = fileLists[i];
					String fileName=SystemConstant.LOCAL_RECEIVE_PATH+Global.globalMap.get(SystemConstant.FTPUSERNAME)+str.substring(str.lastIndexOf("/")+1);
					try {
						FtpUtils.get(str,fileName, false);
					} catch (Exception e) {
						logger.error("获取远程文件失败，远程文件名："+str, e);
					}
				}
			}
		} catch (FTPClientException e) {
			 logger.info("ftp获取报文异常"+e);
			e.printStackTrace();
		}
        logger.info("ftp获取报文定时结束");
    }
	
}
