package cn.haiwaigo.fileclient.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame{
  //控制器
  private ClientContext clientContext;
  //窗口的用户编码的输出框
  private JTextField idField;
  //窗口的用户密码的输入框
  private JPasswordField pwdField;
  //用于提示用户信息的label
  private JLabel message;
  
  public void setClientContext(ClientContext clientContext){
    this.clientContext = clientContext;
  }
  
  //窗口的构造方法
  public LoginFrame(){
    init();//创建窗口的同时初始化窗口内容
  }
  
  //初始化窗口的方法
  public void init(){
    this.setTitle("登录系统");
    this.setSize(260,200);
    this.setLocationRelativeTo(null);//在屏幕中间显示窗口
    this.setContentPane(createContentPane());//设置窗口中的panel
    this.setIconImage(createImage()); 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public Image createImage(){
	  try {
		String src = "ygjt-logo.png";     //图片路径
		    Image image=ImageIO.read(this.getClass().getResource(src));
		    return image;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return null;
  }
  //创建主panel并初始化内容
  public JPanel createContentPane(){
//    JPanel panel = new JPanel();
//    panel.setLayout(new BorderLayout());
    JPanel panel = new JPanel(new BorderLayout());
    //                       边距   上 左 下 右
    panel.setBorder(new EmptyBorder(10,10,10,10));
    
    JLabel label = new JLabel("登录系统",JLabel.CENTER);
    panel.add(label,BorderLayout.NORTH);
    
    panel.add(createCenterPane(),BorderLayout.CENTER);
    panel.add(createBtnPane(),BorderLayout.SOUTH);
    
    return panel;
  }
  
  //创建放在中间的Panel
  public JPanel createCenterPane(){
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(8,8,8,8));
    panel.add(createIdOrPwdPane(),BorderLayout.NORTH);  
    //创建用于给用户提示信息的JLabel
    message = new JLabel("",JLabel.CENTER);
    panel.add(message,BorderLayout.CENTER);
    return panel;
  }
  //创建放置两个lable和两个输入框的panel
  public JPanel createIdOrPwdPane(){
    JPanel panel = new JPanel(new GridLayout(2,1,0,6));
    panel.add(createIdPane());//添加存放编号输入框的panel
    panel.add(createPwdPane());//添加存放密码输入框的panel    
    return panel;
  }
  
  //创建存放编号输入框的panel
  public JPanel createIdPane(){
    JPanel panel = new JPanel(new BorderLayout(6,0));
    JLabel label = new JLabel("用户:");
    panel.add(label,BorderLayout.WEST);
    //创建一个输入框放在CENTER
    idField = new JTextField();
    panel.add(idField,BorderLayout.CENTER);
    return panel;
  }
  //创建存放密码输入框的panel
  public JPanel createPwdPane(){
    JPanel panel = new JPanel(new BorderLayout(6,0));
    JLabel label = new JLabel("密码:");
    panel.add(label,BorderLayout.WEST);
    //创建一个密码框
    pwdField = new JPasswordField();
    //密码框允许输入法(解决linux不能输入的问题)
    pwdField.enableInputMethods(true);
    panel.add(pwdField,BorderLayout.CENTER);
    
    return panel;
  }
  
  //创建按放按钮的Panel
  public JPanel createBtnPane(){
    JPanel panel = new JPanel();
    JButton login = new JButton("登录");
    login.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent e) {
        //当点击登录按钮后,调用控制器ClientContext的登录方法
        clientContext.login();
      }
      
    });
    
    JButton cancel = new JButton("退出");
    cancel.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent e) {
        //当点击cancel按钮后,调用控制器ClientContext的exit方法
        clientContext.exit(LoginFrame.this);
      }
      
    });
    
    panel.add(login);
    panel.add(cancel);
    return panel;
  }
  
  //获取用户编码框中的内容
  public int getUserId(){
    //获取输入框的内容
    return Integer.parseInt(idField.getText());
  }
  //获取用户密码框中的内容
  public String getUserPwd(){
    //从密码框中获取内容不要使用getText方法
//    return pwdField.getText();
    return new String(pwdField.getPassword());
  }
  
  //显示用户提示信息(错误)
  public void showError(String info){
    message.setText(info);//在label中显示错误信息
    message.setForeground(Color.RED);//设置文字颜色
    final Timer timer = new Timer();
    final Point point = this.getLocation();//返回窗口左上点坐标
    timer.schedule(new TimerTask(){
      int off[] = new int[]{0,-5,-10,-5,0,5,10,5};
      int i =0;//循环的计数器
      public void run(){
        Point p = LoginFrame.this.getLocation();//获取当前窗口位置
        p.x += off[i++ % 8];//改变x坐标
        setLocation(p);//将改变后的位置设置给窗口以改变位置
      }
    }, 0,25);
    timer.schedule(new TimerTask(){
      public void run(){
        timer.cancel();
        setLocation(point);
      }
    }, 1000);
  }
  
}







