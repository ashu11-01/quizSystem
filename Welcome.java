import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


class Welcome implements ActionListener
{
	JFrame f1;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
	JButton addQues,play,modQues,delQues,back;
	JPanel dashboard,buttonPanel,rulesPanel;
	JTextArea rules;
	Font f=new Font("Times New Roman",Font.BOLD,25);
	Header h=new Header();
		
	Welcome()
	{	
		//initialize frame
		initJFrame();
		//initialize header
		JPanel header=h.initTopPanel();
		f1.add(header);
		//------show time label l7
		l7=new JLabel();
		l7.setBounds(1000,220,400,20);
		l7.setFont(new Font("Times New Roman",Font.PLAIN,18));
		f1.add(l7);
		//initialize dashboard
		initDashboard();
		f1.add(dashboard);
		//initialize button panel
		initButtonPanel();
		f1.add(buttonPanel);
		//initialize rules panel
		initRulesPanel();
		f1.add(rulesPanel);
		//-----timer function----------
		startTimer();
		//-------------------------
		f1.setVisible(true);
	}

	void initJFrame()
	{
		f1=new JFrame("Home Screen");
		f1.setSize(new Dimension(1500,600));
		f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f1.setUndecorated(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.getContentPane().setBackground(new Color(109,209,237));
		f1.setResizable(false);
		f1.setLayout(null);
	}
	void initDashboard()
	{
		String[] us=JDBCExample.fetchUserDetails();		
		dashboard=new JPanel();
		dashboard.setLayout(null);
		dashboard.setBounds(1000,270,300,325);
		dashboard.setBackground(new Color(77,158,240));
		//------------Dashboard text-----------
		l1=new JLabel(); 
		l1.setBounds(0,5,300,50);
		l1.setFont(f);
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setText("Dashboard");
		dashboard.add(l1);
		//-----------name--------------
		l2=new JLabel();  
		l2.setBounds(5,55,300,50);
		l2.setFont(f);
		l2.setHorizontalAlignment(JLabel.LEFT);
		l2.setText("Name: "+us[0]);
		dashboard.add(l2);
		//-----------user id-----------
		l3=new JLabel();
		l3.setBounds(5,105,300,50);
		l3.setFont(f);
		l3.setHorizontalAlignment(JLabel.LEFT);
		l3.setText("User ID: "+us[1]);
		dashboard.add(l3);
		//-----------class--------------
		l4=new JLabel(); 
		l4.setBounds(5,155,300,50);
		l4.setFont(f);
		l4.setHorizontalAlignment(JLabel.LEFT);
		l4.setText("Class: "+us[2]);
		dashboard.add(l4);
		//------------Roll--------------
		l5=new JLabel(); 
		l5.setBounds(5,205,300,50);
		l5.setFont(f);
		l5.setHorizontalAlignment(JLabel.LEFT);
		l5.setText("Roll no.: "+us[3]);
		dashboard.add(l5);
		//----------User Type-----------
		l8=new JLabel(); 
		l8.setBounds(5,255,300,50);
		l8.setFont(f);
		l8.setHorizontalAlignment(JLabel.LEFT);
		int type=Integer.parseInt(us[4]);
		if(type==1)
			l8.setText("Type: Admin");
		else if(type==2)
			l8.setText("Type: Player");
		dashboard.add(l8);
	}

	void initButtonPanel()
	{
		buttonPanel=new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(90,270,300,325);
		buttonPanel.setBackground(new Color(77,158,240));

		l6=new JLabel(); 
		l6.setBounds(0,10,300,50);
		l6.setFont(f);
		l6.setHorizontalAlignment(JLabel.CENTER);
		l6.setText("Navigation");
		buttonPanel.add(l6);
		//----------add ques button------------
		addQues=new JButton("Add a New Question");
		addQues.setFont(new Font("Ariel",Font.PLAIN,20));
		addQues.setBounds(25,65,250,40);
		addQues.addActionListener(this);
		buttonPanel.add(addQues);
		//----------modify ques button------------
		/*modQues=new JButton("Modify a Question");
		modQues.setFont(new Font("Ariel",Font.PLAIN,20));
		modQues.setBounds(25,115,250,40);
		modQues.addActionListener(this);
		buttonPanel.add(modQues);
		//----------delete ques button------------
		delQues=new JButton("Delete a Question");
		delQues.setFont(new Font("Ariel",Font.PLAIN,20));
		delQues.setBounds(25,165,250,40);
		delQues.addActionListener(this);
		buttonPanel.add(delQues);*/
		//----------play Quiz button------------
		play=new JButton("Go to Quiz");
		play.setFont(new Font("Ariel",Font.PLAIN,20));
		play.setBounds(25,115,250,40);
		play.addActionListener(this);
		buttonPanel.add(play);
		//----------back button------------
		back=new JButton("Log Out");
		back.setFont(new Font("Ariel",Font.PLAIN,20));
		back.setBounds(25,165,250,40);
		back.addActionListener(this);
		buttonPanel.add(back);
	}

	void initRulesPanel()
	{
		rulesPanel=new JPanel();
		rulesPanel.setLayout(null);
		rulesPanel.setBounds(400,270,590,325);
		rulesPanel.setBackground(new Color(77,158,240));
		rules=new JTextArea();
		rules.setBounds(10,60,570,250);
		rules.setEditable(false);
		rules.setLineWrap(true);
		rules.setFont(new Font("Ariel",Font.ITALIC,20));
		rules.setWrapStyleWord(true);
		String ins="Welcome to Tech-Hunch- a quiz playing platform."+
					" Below are the general guidelines and intrunction to use this platform:-"+
					"\n\n1. Only Admin can add, modify or delete a question. If you are not an"+
					" admin and click on these buttons on the left you will get an error message."+
					"\n\n2. Click on 'Go to Quiz' button to start playing quiz."+
					"\n\n3. There is no negative marking";
		rules.setText(ins);
		rulesPanel.add(rules);
		l9=new JLabel();
		l9.setBounds(0,10,590,30);
		l9.setHorizontalAlignment(JLabel.CENTER);
		l9.setFont(new Font("Times New Roman",Font.BOLD,25));
		l9.setText("Rules");
		rulesPanel.add(l9);
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
		l7.setText(dateFormat.format(date));
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==(addQues))
		{
			if(JDBCExample.isAdmin())
			{
				AddQues aq=new AddQues();
				f1.dispose();
				f1.setVisible(false);
			}
			else
				JOptionPane.showMessageDialog(f1,"You cannot add new question","Error",JOptionPane.ERROR_MESSAGE);
		}
		else if(ae.getSource()==(modQues))
		{
			f1.dispose();
			f1.setVisible(false);
			//ModQues aq=new ModQues();
		}
		else if(ae.getSource()==(delQues))
		{
			f1.dispose();
			f1.setVisible(false);
			//DelQues aq=new DelQues();
		}
		else if(ae.getSource()==(play))
		{
			System.out.println("Started Playing");
			f1.dispose();
			f1.setVisible(false);
			Test t=new Test();
			t.displayQues(1);
		}
		else if(ae.getSource()==(back))
		{
			f1.dispose();
			f1.setVisible(false);
			JDBCExample.currUser="";
			Login l=new Login();
		}
	}
}