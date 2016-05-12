package cn.haiwaigo.fileclient.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import cn.haiwaigo.fileclient.context.Global;
import cn.haiwaigo.fileclient.context.SystemConstant;
import cn.haiwaigo.fileclient.exception.FTPClientException;


/**
 * 
 * FTP工具类
 * 
 * @author jinwx
 * @version $Id: FtpUtils.java, v 0.1 Apr 17, 2015 11:30:41 AM jinwx Exp $
 * @since 1.0
 */
public class FtpUtils {
	
	private static Logger log = Logger.getLogger(FtpUtils.class);

	private static String host = PropertiesUtil.getValue("ftp.url");
	private static int port = Integer.parseInt(PropertiesUtil.getValue("ftp.port"));
	private static boolean binaryTransfer = Boolean.parseBoolean(PropertiesUtil.getValue("ftp.binaryTransfer"));  
    private static boolean    passiveMode    = Boolean.parseBoolean(PropertiesUtil.getValue("ftp.passiveMode"));  
    private static String     encoding       = PropertiesUtil.getValue("ftp.encoding");  
    private static int        clientTimeout  = Integer.parseInt(PropertiesUtil.getValue("ftp.clientTimeout"));  

    private static FTPClient getFTPClient() throws FTPClientException {  
        FTPClient ftpClient = new FTPClient(); //构造一个FtpClient实例  
        ftpClient.setControlEncoding(encoding); //设置字符集  
        connect(ftpClient); //连接到ftp服务器  
          
        //设置为passive模式  
        if (passiveMode) {  
            ftpClient.enterLocalPassiveMode();  
        }  
        setFileType(ftpClient); //设置文件传输类型  
          
        try {  
            ftpClient.setSoTimeout(clientTimeout);  
        } catch (SocketException e) {  
            throw new FTPClientException("Set timeout error.", e);  
        }  
        log.debug("ftp连接成功");
        return ftpClient;  
    }  
  
    /**  
     * 设置文件传输类型  
     *   
     * @throws FTPClientException  
     * @throws IOException  
     */  
    private static void setFileType(FTPClient ftpClient) throws FTPClientException {  
        try {  
            if (binaryTransfer) {  
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            } else {  
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);  
            }  
        } catch (IOException e) {  
            throw new FTPClientException("Could not to set file type.", e);  
        }  
    }  
  
    /**  
     * 连接到ftp服务器  
     *   
     * @param ftpClient  
     * @return 连接成功返回true，否则返回false  
     * @throws FTPClientException  
     */  
    public static boolean connect(FTPClient ftpClient) throws FTPClientException {  
        try {  
            ftpClient.connect(host, port);  
  
            // 连接后检测返回码来校验连接是否成功  
            int reply = ftpClient.getReplyCode();  
  
            if (FTPReply.isPositiveCompletion(reply)) {  
                //登陆到ftp服务器  
                if (ftpClient.login(Global.globalMap.get(SystemConstant.FTPUSERNAME), Global.globalMap.get(SystemConstant.FTPPASSWD))) {  
                    setFileType(ftpClient);  
                    return true;  
                }  
            } else {  
                ftpClient.disconnect();  
                throw new FTPClientException("FTP server refused connection.");  
            }  
        } catch (IOException e) {  
            if (ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect(); //断开连接  
                } catch (IOException e1) {  
                    throw new FTPClientException("Could not disconnect from server.", e);  
                }  
  
            }  
            throw new FTPClientException("Could not connect to server.", e);  
        }  
        return false;  
    }  
  
    /**  
     * 断开ftp连接  
     *   
     * @throws FTPClientException  
     */  
    private static void disconnect(FTPClient ftpClient) throws FTPClientException {  
        try {  
            ftpClient.logout();  
            if (ftpClient.isConnected()) {  
                ftpClient.disconnect();  
            }  
        } catch (IOException e) {  
            throw new FTPClientException("Could not disconnect from server.", e);  
        }  
    }  
  
    //---------------------------------------------------------------------  
    // public method  
    //---------------------------------------------------------------------  
    /**  
     * 上传一个本地文件到远程指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @return 成功时，返回true，失败返回false  
     * @throws FTPClientException  
     */  
    public static boolean put(String serverFile, String localFile) throws FTPClientException {  
        return put(serverFile, localFile, false);  
    }  
    
    /**
     * 
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param in 输入流
     * @return 
     * @throws FTPClientException
     */
    public static boolean put(String serverFile,InputStream in)throws FTPClientException{
    	 FTPClient ftpClient = null;  
	        try {  
	            ftpClient = getFTPClient();  
	            // 处理传输  
	            ftpClient.storeFile(serverFile, in);  
	            return true;  
	        } catch (FileNotFoundException e) {  
	            throw new FTPClientException("local file not found.", e);  
	        } catch (IOException e) {  
	            throw new FTPClientException("Could not put file to server.", e);  
	        } finally {  
	            try {  
	                if (in != null) {  
	                    in.close();  
	                }  
	            } catch (Exception e) {  
	                throw new FTPClientException("Couldn't close FileInputStream.", e);  
	            }  
	            if (ftpClient != null) {  
	                disconnect(ftpClient); //断开连接  
	            }  
	        }  
    }
  
    /**  
     * 上传一个本地文件到远程指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @param delFile 成功后是否删除文件  
     * @return 成功时，返回true，失败返回false  
     * @throws FTPClientException  
     */  
    public static boolean put(String serverFile, String localFile, boolean delFile) throws FTPClientException {  
        FTPClient ftpClient = null;  
        InputStream input = null;  
        try {  
            ftpClient = getFTPClient();  
            // 处理传输  
            input = new FileInputStream(localFile);  
            ftpClient.storeFile(serverFile, input);  
            log.debug("put " + localFile);  
            System.out.println("put " + localFile);
            input.close();  
            if (delFile) {  
                (new File(localFile)).delete();  
            }  
            log.debug("delete " + localFile);  
            return true;  
        } catch (FileNotFoundException e) {  
            throw new FTPClientException("local file not found.", e);  
        } catch (IOException e) {  
            throw new FTPClientException("Could not put file to server.", e);  
        } finally {  
            try {  
                if (input != null) {  
                    input.close();  
                }  
            } catch (Exception e) {  
                throw new FTPClientException("Couldn't close FileInputStream.", e);  
            }  
            if (ftpClient != null) {  
                disconnect(ftpClient); //断开连接  
            }  
        }  
    }  
  
    /**  
     * 下载一个远程文件到本地的指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @return 成功时，返回true，失败返回false  
     * @throws FTPClientException  
     */  
    public static boolean get(String serverFile, String localFile) throws FTPClientException {  
        return get(serverFile, localFile, false);  
    }  
  
    /**  
     * 下载一个远程文件到本地的指定文件  
     *   
     * @param serverFile 服务器端文件名(包括完整路径)  
     * @param localFile 本地文件名(包括完整路径)  
     * @return 成功时，返回true，失败返回false  
     * @throws FTPClientException  
     */  
    public static boolean get(String serverFile, String localFile, boolean delFile) throws FTPClientException {  
        OutputStream output = null;  
        try {  
            output = new FileOutputStream(localFile);  
            return get(serverFile, output, delFile);  
        } catch (FileNotFoundException e) {  
            throw new FTPClientException("local file not found.", e);  
        } finally {  
            try {  
                if (output != null) {  
                    output.close();  
                }  
            } catch (IOException e) {  
                throw new FTPClientException("Couldn't close FileOutputStream.", e);  
            }  
        }  
    }  
      
    /**  
     * 下载一个远程文件到指定的流  
     * 处理完后记得关闭流  
     *   
     * @param serverFile  
     * @param output  
     * @return  
     * @throws FTPClientException  
     */  
    public static boolean get(String serverFile, OutputStream output) throws FTPClientException {  
        return get(serverFile, output, false);  
    }  
      
    /**  
     * 下载一个远程文件到指定的流  
     * 处理完后记得关闭流  
     *   
     * @param serverFile  
     * @param output  
     * @param delFile  
     * @return  
     * @throws FTPClientException  
     */  
    public static boolean get(String serverFile, OutputStream output, boolean delFile) throws FTPClientException {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = getFTPClient();  
            // 处理传输  
            ftpClient.retrieveFile(serverFile, output);  
            if (delFile) { // 删除远程文件  
                ftpClient.deleteFile(serverFile);  
            }  
            return true;  
        } catch (IOException e) {  
            throw new FTPClientException("Couldn't get file from server.", e);  
        } finally {  
            if (ftpClient != null) {  
                disconnect(ftpClient); //断开连接  
            }  
        }  
    }  
      
    /**  
     * 从ftp服务器上删除一个文件  
     *   
     * @param delFile  
     * @return  
     * @throws FTPClientException  
     */  
    public static boolean delete(String delFile) throws FTPClientException {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = getFTPClient();  
            ftpClient.deleteFile(delFile);  
            return true;  
        } catch (IOException e) {  
            throw new FTPClientException("Couldn't delete file from server.", e);  
        } finally {  
            if (ftpClient != null) {  
                disconnect(ftpClient); //断开连接  
            }  
        }  
    }  
      
    /**  
     * 批量删除  
     *   
     * @param delFiles  
     * @return  
     * @throws FTPClientException  
     */  
    public static boolean delete(String[] delFiles) throws FTPClientException {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = getFTPClient();  
            for (String s : delFiles) {  
                ftpClient.deleteFile(s);  
            }  
            return true;  
        } catch (IOException e) {  
            throw new FTPClientException("Couldn't delete file from server.", e);  
        } finally {  
            if (ftpClient != null) {  
                disconnect(ftpClient); //断开连接  
            }  
        }  
    }  
  
    /**  
     * 列出远程默认目录下所有的文件  
     *   
     * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组  
     * @throws FTPClientException  
     */  
    public static String[] listNames() throws FTPClientException {  
        return listNames(null);  
    }  
  
    /**  
     * 列出远程目录下所有的文件  
     *   
     * @param remotePath 远程目录名  
     * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组  
     * @throws FTPClientException  
     */  
    public static String[] listNames(String remotePath) throws FTPClientException {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = getFTPClient();  
            String[] listNames = ftpClient.listNames(remotePath);  
            return listNames;  
        } catch (IOException e) {  
            throw new FTPClientException("列出远程目录下所有的文件时出现异常", e);  
        } finally {  
            if (ftpClient != null) {  
                disconnect(ftpClient); //断开连接  
            }  
        }  
    }  
    public static boolean createFile(String strFilePath, String strFileContent,String encoding) {  
		 if(StringUtils.isEmpty(encoding)){
				encoding = "UTF-8";
		}
	        boolean bFlag = false;  
	        File file = new File(strFilePath.toString()); 
	        PrintWriter pw = null;  
           FileOutputStream fo = null;  
           OutputStreamWriter osw = null;  
	        try {  
				File pf = file.getParentFile();
				if(!pf.exists()){
				 pf.mkdirs();
				}
	            if (!file.exists()) {  
	                bFlag = file.createNewFile();  
	            }  
	            if (bFlag == Boolean.TRUE) {  
	                fo = new FileOutputStream(file);  
	                osw = new OutputStreamWriter(fo,encoding);  
	                pw = new PrintWriter(osw);  
	                pw.println(strFileContent.toString());  
	                pw.close();  
	                osw.close();
		        	fo.close();
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return bFlag;  
	    }  
    public static boolean createFile(String strFilePath, String strFileContent) {  
		 return createFile(strFilePath,strFileContent,null);
	  }  
	 /** 
	     * 复制单个文件 
	     * @param oldPath String 原文件路径 如：c:/fqf.txt 
	     * @param newPath String 复制后路径 如：f:/fqf.txt 
	     * @return boolean 
	     */ 
	   public static void copyFile(String oldPath, String newPath) { 
	       try { 
	           int byteread = 0; 
	           File oldfile = new File(oldPath); 
	           if (oldfile.exists()) { //文件存在时 
	               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	               File file = new File(newPath);
	               File pf = file.getParentFile();
	               if(!pf.exists()){
	            	   pf.mkdirs();
	               }
	               createFile(newPath, "");
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	           } 
	       } catch (Exception e) { 
	           System.out.println("复制单个文件操作出错"); 
	           e.printStackTrace(); 

	       } 

	   } 
//	    public static void main(String[] args) throws FTPClientException{  
//	    	D:\\ftp上传\\
//	    	/Users/xuan/Documents/YGJTProject/src/main/java/com/ygjt/oms/tran/util/1.txt
	        //ftp.get("/test_0920.zip", "d:/test_0920.zip");  
//	        String[] aa = {"qqq/111.txt", "qqq/222.zip"};  
//	        ftp.delete(aa);  
	    	
	    	// 上传
//	    	new FtpUtils().put("order\\test\\333111.txt", new ByteArrayInputStream("1231231中sdfs   >".getBytes()));
	    	
	    	// 下载
//	    	new FtpUtils().get("kjftp\\bak\\QDSDHZ.xml", "F:/ygjt_hz_workspaces_svn/omsTran/src/test/java/tran/itg/ftp/hn/test.xml");
//	    	new FtpUtils().get("order\\test\\333111.txt", "F:/ygjt_hz_workspaces_svn/omsTran/src/test/java/tran/itg/ftp/hn/test.text");
	    	
//	    	String[] files = new FtpUtils().listNames("kjftp\\bak");
//	    	String[] files = new FtpUtils().listNames("order");
//	    	if(null != files && files.length > 0){
//	    		for(String fileName : files){
//	    			System.out.println("fileName=" + fileName);
//	    		}
//	    	}
//	    }  


}
