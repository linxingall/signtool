package cn.haiwaigo.fileclient.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author linx
 *
 */
public class FileUtils {
	Logger logger = Logger.getLogger(FileUtils.class);
	
	/**
	 * 
	* @Title: getFileContent 
	* @Description: TODO(根据文件名获取文件内容) 
	* @param @param filePath
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getFileContent(String filePath) throws Exception{
		return getFileContent(filePath,null);
	}
	/**
	 * 
	* @Title: getFileContent 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param filePath
	* @param @param encoding
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getFileContent(String filePath,String encoding) throws Exception{
		if(StringUtils.isEmpty(encoding)){
			encoding = "UTF-8";
		}
		StringBuffer sb = new StringBuffer();
		File file=new File(filePath);
        if(file.isFile() && file.exists()){ //判断文件是否存在
        	InputStreamReader read = new InputStreamReader(
        			new FileInputStream(file),encoding);//考虑到编码格式
        	BufferedReader bufferedReader = new BufferedReader(read);
            try {
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
				    sb.append(lineTxt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				read.close();
				bufferedReader.close();
			}
        }else{
        	throw new Exception("找不到指定的文件");
        }
		return sb.toString();
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
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	           } 
	       } 
	       catch (Exception e) { 
	           System.out.println("复制单个文件操作出错"); 
	           e.printStackTrace(); 

	       } 

	   } 
//	public static void main(String[] args) {
//		System.out.println("start======");
//		createFile("D:\\1.txt", "我们都是中国人呢");
//		System.out.println("over=========");
//	}
}	
