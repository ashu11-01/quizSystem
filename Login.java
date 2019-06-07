//login frame
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
//import java.sql.Date;
import java.util.*;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

class Login implements ActionListener
{
	JFrame f1;
	JLabel l1,l2,l3;
	JTextField tf1,tf2;
	JButton login,clear,quit;
	JPanel top;
	Font f=new Font("Times New Roman",Font.BOLD,30);
	Header h=new Header();
		
	Login()
	{
		initJFrame();
		initJLabel();
		initJTextField();
		initJButton();
		top=h.initTopPanel();
		f1.add(top);
		f1.add(l3);
		f1.add(l1);
		f1.add(tf1);
		f1.add(l2);
		f1.add(tf2);
		f1.add(login);
		f1.add(clear);
		f1.add(quit);
		login.addActionListener(this);
		clear.addActionListener(this);
		quit.addActionListener(this);
		//-----------
		startTimer();
		
        //----------------
		f1.setVisible(true);
	}
	
	void initJFrame()
	{
		f1=new JFrame("Login");
		f1.setSize(new Dimension(1500,600));
		f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f1.setUndecorated(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.getContentPane().setBackground(new Color(109,209,237));
		f1.setResizable(false);
		f1.setLayout(null);
	}

	void initJLabel()
	{
		l1=new JLabel("Username:");
		l1.setBounds(500,300,150,30);
		l1.setFont(f);

		l2=new JLabel("Password:");
		l2.setBounds(500,350,150,30);
		l2.setFont(f);

		l3=new JLabel("");
		l3.setFont(new Font("Times New Roman",Font.PLAIN,20));
		l3.setBounds(1000,220,400,20);		
	}
	
	void initJTextField()
	{
		tf1=new JTextField(15);
		tf1.setBounds(690,300,200,30);

		tf2=new JPasswordField(15);
		tf2.setBounds(690,350,200,30);
	}	

	void initJButton()
	{
		login=new JButton("Login");
		login.setBounds(550,410,100,40);

		clear=new JButton("Clear");
		clear.setBounds(670,410,100,40);

		quit=new JButton("Quit");
		quit.setBounds(600,470,100,50);
	}	
	void startTimer()
	{
		Timer timer = new Timer(500, new ActionListener() {
                //@Override
                public void actionPerformed(ActionEvent e) {
                    tickTock();
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            timer.start();
	}

	public void tickTock()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		l3.setText(dateFormat.format(date));
	}

	

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Login")) // login button clicked
		{
			String ob[]={tf1.getText(),tf2.getText()};
			int code=JDBCExample.validateLogin(ob); //call validateLogin and get code
			/*	code=1 userId field is empty
				code=2 password field is empty
				code=3 user id not found
				code=4 wrong password
				code=0 everything is good!! */
			switch(code)
			{
				case 0:
					Welcome wc=new Welcome();
					f1.dispose();
					f1.setVisible(false);
					break;
				case 1:
					JOptionPane.showMessageDialog(f1," UserId field is empty","Error",JOptionPane.ERROR_MESSAGE);
					break;
				case 2:
					JOptionPane.showMessageDialog(f1," Password field is empty","Error",JOptionPane.ERROR_MESSAGE);
					break;
				case 3:
					JOptionPane.showMessageDialog(f1," UserId \'"+ob[0]+"\'' not found","Error",JOptionPane.ERROR_MESSAGE);
					break;
				case 4:
					JOptionPane.showMessageDialog(f1," Password is wrong","Error",JOptionPane.ERROR_MESSAGE);
					break;
				default:
					JOptionPane.showMessageDialog(f1," Ohh! Something went wrong","Error",JOptionPane.ERROR_MESSAGE);
					break;
			}
			
		}
		
		else if(e.getActionCommand().equals("Clear")) // clear button clicked
		{
			tf1.setText("");
			tf2.setText("");
		}
		else if(e.getActionCommand().equals("Quit")) //	quit button clicked
		{
			System.exit(0);
		}	
	}
}