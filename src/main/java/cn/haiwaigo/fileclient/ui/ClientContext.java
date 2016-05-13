package cn.haiwaigo.fileclient.ui;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cn.haiwaigo.fileclient.context.Global;
import cn.haiwaigo.fileclient.context.SystemConstant;
import cn.haiwaigo.fileclient.job.FtpFileJob;
import cn.haiwaigo.fileclient.job.MoveFtpFileJob;
import cn.haiwaigo.fileclient.job.SignJob;



/**
 * 客户端控制器
 * C层(控制层)
 * 作用:
 *    协调视图层和模型层,起到控制调度工作
 * @author linx
 *
 */
public class ClientContext {
  
  //定义视图层内容作为控制器的属性
  private LoginFrame loginFrame;
  private MenuFrame menuFrame;
 
  
  
 
  public void setLoginFrame(LoginFrame loginFrame){
    this.loginFrame = loginFrame;
  }
  
  public void setMenuFrame(MenuFrame menuFrame) {
	this.menuFrame = menuFrame;
}

/**
   * 程序启动后,做的第一件事情
   * 显示登录窗口
   */
  public void show(){
    loginFrame.setVisible(true);
  }
  /**
   * 控制器的登录方法
   *  本身不处理登录逻辑,而是交由service处理,得到结果后控制
   *  不同的视图显示结果
   */
  public void login(){
    try {
      String id = loginFrame.getUserId();
      String pwd = loginFrame.getUserPwd();
     //TODO 调用服务平台登录接口
      Global.globalMap.put(SystemConstant.FTPUSERNAME, "ftplinx");
      Global.globalMap.put(SystemConstant.FTPPASSWD, "ftplinx");
      Global.globalMap.put(SystemConstant.LOCAL_TEMP_PATH,SystemConstant.LOCAL_RECEIVE_PATH+"ftplinx"+File.separator);
      //将登录窗口隐藏
      loginFrame.setVisible(false);
      //将菜单窗口显示
      menuFrame.setVisible(true);
      
      //启动定时器
      FtpFileJob.getFileFromFtp();
      SignJob.signTask();
      MoveFtpFileJob.moveFtpFileToTemp();
//    }catch(ServiceException e){
//        loginFrame.showError(e.getMessage());
      }catch (Exception e) {
    	  e.printStackTrace();
    }
  }
  /**
   * 程序退出方法
   * 参数:哪个窗口要求退出程序
   * 作用:要在这个窗口上弹出一个确认框,在用户确认此操作后
   *      才真正的退出程序
   */
  public void exit(JFrame frame){
    //显示确认框
    /**
     * JOptionPane的静态方法showConfirmDialog方法的作用是显示
     *            一个确认对话框.
     *            方法的第一个参数:
     *                    在那个组件上弹出确认对话框
     *            方法的第二个参数:
     *                    对话框上显示的信息
     *            方法的返回值:
     *                    int值,代表用户点击的是确认还是取消
     *                    这个int值对应常量.
     */
    int value = JOptionPane
                  .showConfirmDialog(frame, "确认退出么?");
    //若用户点击的是确定按钮,隐藏当前窗口并退出程序
    if(value == JOptionPane.YES_OPTION){
      frame.setVisible(false);
      System.exit(0);//结束java进程(虚拟机关闭)
    }
    
    
  }
  
}

















