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
 * ���˵�����
 */
public class MenuFrame extends JFrame{
  private ClientContext clientContext;
  
  private JLabel userinfo;//������ʾ�û���Ϣ��label
  
  public void setClientContext(ClientContext clientContext){
    this.clientContext = clientContext;
  }
  
  public MenuFrame(){
    init();
  }
  //��ʼ������
  private void init(){
    this.setTitle("��ǩϵͳ");
    this.setSize(600,400);
    this.setLocationRelativeTo(null);
    this.setContentPane(createContentPane());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setIconImage(createImage());
  }
  
  //������pane
  private JPanel createContentPane(){
    JPanel panel = new JPanel(new BorderLayout());
    //����һ��ͼƬͼ��ImageIcon
//    ImageIcon icon = new ImageIcon(
//                  this.getClass().getResource("title.png")
//                          );
//    JLabel imageLabel = 
//                  new JLabel(icon);//��label��ʾͼƬ
//    panel.add(imageLabel,BorderLayout.NORTH);
    
    JLabel strLabel = 
         new JLabel("���ڴ�����,����رոý���",JLabel.CENTER);
    panel.add(strLabel,BorderLayout.CENTER);
    
  //  panel.add(createMenuPane(),BorderLayout.CENTER);
    
    return panel;
  }
  public Image createImage(){
	try {
		String src = "ygjt-logo.png";     //ͼƬ·��
		    Image image=ImageIO.read(this.getClass().getResource(src));
		    return image;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return null;
  }
}










