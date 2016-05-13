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
 * �ͻ��˿�����
 * C��(���Ʋ�)
 * ����:
 *    Э����ͼ���ģ�Ͳ�,�𵽿��Ƶ��ȹ���
 * @author linx
 *
 */
public class ClientContext {
  
  //������ͼ��������Ϊ������������
  private LoginFrame loginFrame;
  private MenuFrame menuFrame;
 
  
  
 
  public void setLoginFrame(LoginFrame loginFrame){
    this.loginFrame = loginFrame;
  }
  
  public void setMenuFrame(MenuFrame menuFrame) {
	this.menuFrame = menuFrame;
}

/**
   * ����������,���ĵ�һ������
   * ��ʾ��¼����
   */
  public void show(){
    loginFrame.setVisible(true);
  }
  /**
   * �������ĵ�¼����
   *  ���������¼�߼�,���ǽ���service����,�õ���������
   *  ��ͬ����ͼ��ʾ���
   */
  public void login(){
    try {
      String id = loginFrame.getUserId();
      String pwd = loginFrame.getUserPwd();
     //TODO ���÷���ƽ̨��¼�ӿ�
      Global.globalMap.put(SystemConstant.FTPUSERNAME, "ftplinx");
      Global.globalMap.put(SystemConstant.FTPPASSWD, "ftplinx");
      Global.globalMap.put(SystemConstant.LOCAL_TEMP_PATH,SystemConstant.LOCAL_RECEIVE_PATH+"ftplinx"+File.separator);
      //����¼��������
      loginFrame.setVisible(false);
      //���˵�������ʾ
      menuFrame.setVisible(true);
      
      //������ʱ��
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
   * �����˳�����
   * ����:�ĸ�����Ҫ���˳�����
   * ����:Ҫ����������ϵ���һ��ȷ�Ͽ�,���û�ȷ�ϴ˲�����
   *      ���������˳�����
   */
  public void exit(JFrame frame){
    //��ʾȷ�Ͽ�
    /**
     * JOptionPane�ľ�̬����showConfirmDialog��������������ʾ
     *            һ��ȷ�϶Ի���.
     *            �����ĵ�һ������:
     *                    ���Ǹ�����ϵ���ȷ�϶Ի���
     *            �����ĵڶ�������:
     *                    �Ի�������ʾ����Ϣ
     *            �����ķ���ֵ:
     *                    intֵ,�����û��������ȷ�ϻ���ȡ��
     *                    ���intֵ��Ӧ����.
     */
    int value = JOptionPane
                  .showConfirmDialog(frame, "ȷ���˳�ô?");
    //���û��������ȷ����ť,���ص�ǰ���ڲ��˳�����
    if(value == JOptionPane.YES_OPTION){
      frame.setVisible(false);
      System.exit(0);//����java����(������ر�)
    }
    
    
  }
  
}

















