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
  //������
  private ClientContext clientContext;
  //���ڵ��û�����������
  private JTextField idField;
  //���ڵ��û�����������
  private JPasswordField pwdField;
  //������ʾ�û���Ϣ��label
  private JLabel message;
  
  public void setClientContext(ClientContext clientContext){
    this.clientContext = clientContext;
  }
  
  //���ڵĹ��췽��
  public LoginFrame(){
    init();//�������ڵ�ͬʱ��ʼ����������
  }
  
  //��ʼ�����ڵķ���
  public void init(){
    this.setTitle("��¼ϵͳ");
    this.setSize(260,200);
    this.setLocationRelativeTo(null);//����Ļ�м���ʾ����
    this.setContentPane(createContentPane());//���ô����е�panel
    this.setIconImage(createImage()); 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
  //������panel����ʼ������
  public JPanel createContentPane(){
//    JPanel panel = new JPanel();
//    panel.setLayout(new BorderLayout());
    JPanel panel = new JPanel(new BorderLayout());
    //                       �߾�   �� �� �� ��
    panel.setBorder(new EmptyBorder(10,10,10,10));
    
    JLabel label = new JLabel("��¼ϵͳ",JLabel.CENTER);
    panel.add(label,BorderLayout.NORTH);
    
    panel.add(createCenterPane(),BorderLayout.CENTER);
    panel.add(createBtnPane(),BorderLayout.SOUTH);
    
    return panel;
  }
  
  //���������м��Panel
  public JPanel createCenterPane(){
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(8,8,8,8));
    panel.add(createIdOrPwdPane(),BorderLayout.NORTH);  
    //�������ڸ��û���ʾ��Ϣ��JLabel
    message = new JLabel("",JLabel.CENTER);
    panel.add(message,BorderLayout.CENTER);
    return panel;
  }
  //������������lable������������panel
  public JPanel createIdOrPwdPane(){
    JPanel panel = new JPanel(new GridLayout(2,1,0,6));
    panel.add(createIdPane());//��Ӵ�ű��������panel
    panel.add(createPwdPane());//��Ӵ������������panel    
    return panel;
  }
  
  //������ű��������panel
  public JPanel createIdPane(){
    JPanel panel = new JPanel(new BorderLayout(6,0));
    JLabel label = new JLabel("�û�:");
    panel.add(label,BorderLayout.WEST);
    //����һ����������CENTER
    idField = new JTextField();
    panel.add(idField,BorderLayout.CENTER);
    return panel;
  }
  //�����������������panel
  public JPanel createPwdPane(){
    JPanel panel = new JPanel(new BorderLayout(6,0));
    JLabel label = new JLabel("����:");
    panel.add(label,BorderLayout.WEST);
    //����һ�������
    pwdField = new JPasswordField();
    //������������뷨(���linux�������������)
    pwdField.enableInputMethods(true);
    panel.add(pwdField,BorderLayout.CENTER);
    
    return panel;
  }
  
  //�������Ű�ť��Panel
  public JPanel createBtnPane(){
    JPanel panel = new JPanel();
    JButton login = new JButton("��¼");
    login.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent e) {
        //�������¼��ť��,���ÿ�����ClientContext�ĵ�¼����
        clientContext.login();
      }
      
    });
    
    JButton cancel = new JButton("�˳�");
    cancel.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent e) {
        //�����cancel��ť��,���ÿ�����ClientContext��exit����
        clientContext.exit(LoginFrame.this);
      }
      
    });
    
    panel.add(login);
    panel.add(cancel);
    return panel;
  }
  
  //��ȡ�û�������е�����
  public int getUserId(){
    //��ȡ����������
    return Integer.parseInt(idField.getText());
  }
  //��ȡ�û�������е�����
  public String getUserPwd(){
    //��������л�ȡ���ݲ�Ҫʹ��getText����
//    return pwdField.getText();
    return new String(pwdField.getPassword());
  }
  
  //��ʾ�û���ʾ��Ϣ(����)
  public void showError(String info){
    message.setText(info);//��label����ʾ������Ϣ
    message.setForeground(Color.RED);//����������ɫ
    final Timer timer = new Timer();
    final Point point = this.getLocation();//���ش������ϵ�����
    timer.schedule(new TimerTask(){
      int off[] = new int[]{0,-5,-10,-5,0,5,10,5};
      int i =0;//ѭ���ļ�����
      public void run(){
        Point p = LoginFrame.this.getLocation();//��ȡ��ǰ����λ��
        p.x += off[i++ % 8];//�ı�x����
        setLocation(p);//���ı���λ�����ø������Ըı�λ��
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







