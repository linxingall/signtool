package cn.haiwaigo.fileclient.ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 主菜单窗口
 */
public class MenuFrame extends JFrame{
  private ClientContext clientContext;
  
  private JLabel userinfo;//用于显示用户信息的label
  
  public void setClientContext(ClientContext clientContext){
    this.clientContext = clientContext;
  }
  
  public MenuFrame(){
    init();
  }
  //初始化窗口
  private void init(){
    this.setTitle("加签系统");
    this.setSize(600,400);
    this.setLocationRelativeTo(null);
    this.setContentPane(createContentPane());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setIconImage(createImage());
  }
  
  //创建主pane
  private JPanel createContentPane(){
    JPanel panel = new JPanel(new BorderLayout());
    //创建一张图片图标ImageIcon
//    ImageIcon icon = new ImageIcon(
//                  this.getClass().getResource("title.png")
//                          );
//    JLabel imageLabel = 
//                  new JLabel(icon);//用label显示图片
//    panel.add(imageLabel,BorderLayout.NORTH);
    
    JLabel strLabel = 
         new JLabel("正在处理中,请勿关闭该界面",JLabel.CENTER);
    panel.add(strLabel,BorderLayout.CENTER);
    
  //  panel.add(createMenuPane(),BorderLayout.CENTER);
    
    return panel;
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
}










