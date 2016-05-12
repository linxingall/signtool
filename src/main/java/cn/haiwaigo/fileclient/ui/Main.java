package cn.haiwaigo.fileclient.ui;


/**
 * 程序的主启动类
 * 负责各个层次的初始化工作
 */
public class Main {

  public static void main(String[] args) {
    //初始化所有的窗口,初始化视图层
    LoginFrame loginFrame = new LoginFrame();
    MenuFrame menuFrame = new MenuFrame();
    
    //初始化控制器(控制层)
    ClientContext clientContext = new ClientContext();
    
    //初始化模型层
    clientContext.setLoginFrame(loginFrame);
    clientContext.setMenuFrame(menuFrame);
    
    loginFrame.setClientContext(clientContext);
    menuFrame.setClientContext(clientContext);
    
    //调用控制器的show方法开始运行程序
    clientContext.show();
  }

  
}

