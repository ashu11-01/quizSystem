import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.sql.SQLException;
import java.sql.ResultSet;

class Test implements ActionListener
{
	JFrame f1;
	JLabel l1,l2,l3,ct;
	JButton next,clear;
	JRadioButton jrb1,jrb2,jrb3,jrb4,unselect;
	JPanel quizOverPanel,quesPanel,rightPanel,top,leftPanel,finish;
	static int currQues=1,totalQues=0;
	int t=0;
	String sheet[];
	boolean result[];
	
	public Test()
	{
		totalQues=JDBCExample.totalQues(); //get total no of ques in database table
		sheet=new String[totalQues];
		result=new boolean[totalQues];
		//initilize frame
		initJFrame();
		//initialize quesPanel
		initQuesPanel();
		f1.add(quesPanel);
		//initialize leftPanel
		//initLeftPanel();
		//initialize RightPanel
		//initRightPanel();
		//initialize Header panel
		startTimer();
		top=(new Header()).initTopPanel();
		f1.add(top);
		f1.setVisible(true);
		try{
			startStopwatch();
		}
		catch(Exception e)
		{}
	}
	void initJFrame()	
	{
		f1=new JFrame("Play Quiz");
		f1.setSize(new Dimension(1500,600));
		f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f1.setUndecorated(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.getContentPane().setBackground(new Color(109,209,237));
		f1.setResizable(false);
		f1.setLayout(null);

		l3=new JLabel("");
		l3.setFont(new Font("Times New Roman",Font.PLAIN,20));
		l3.setBounds(1000,220,400,20);
		f1.add(l3);
		
		ct=new JLabel();
		ct.setFont(new Font("Times New Roman",Font.PLAIN,20));
		ct.setBounds(300,220,600,20);
		f1.add(ct);
	}

	void initQuesPanel()
	{
		quesPanel=new JPanel();
		quesPanel.setBounds(350,270,670,400);
		quesPanel.setBackground(new Color(193, 164, 222));
		quesPanel.setLayout(null);
		//----------ques no.----------
		l1=new JLabel("1");
		l1.setBounds(10,10,50,30);
		l1.setFont(new Font("Ariel",Font.PLAIN,20));
		quesPanel.add(l1);
		//------------ques statement---------
		l2=new JLabel("ques");
		l2.setBounds(60,10,800,70);
		l2.setFont(new Font("Ariel",Font.PLAIN,20));
		l2.setVerticalAlignment(JLabel.TOP);
		quesPanel.add(l2);

		
		//------------option radio buttons--------
		jrb1=new JRadioButton("");
		jrb1.setBounds(60,90,400,30);
		quesPanel.add(jrb1);

		jrb2=new JRadioButton("");
		jrb2.setBounds(60,130,400,30);
		quesPanel.add(jrb2);

		jrb3=new JRadioButton("");
		jrb3.setBounds(60,170,400,30);
		quesPanel.add(jrb3);

		jrb4=new JRadioButton("");
		jrb4.setBounds(60,210,400,30);
		quesPanel.add(jrb4);

		unselect=new JRadioButton("unselect");
		unselect.setBounds(380,290,120,30);
		//quesPanel.add(unselect);
		//-----------button group for radio button to be mutully exclusive
		ButtonGroup bg=new ButtonGroup();
		bg.add(jrb1); bg.add(jrb2); bg.add(jrb3); bg.add(jrb4);
		bg.add(unselect);
		//------next button----------
		next=new JButton("Next");
		next.setBounds(220,340,120,30);
		next.addActionListener(this);
		//quesPanel.add(next);
		//--------clear button-------------
		clear=new JButton("Clear Selection");      
		clear.setBounds(360,340,120,30);
		clear.addActionListener(this);
		quesPanel.add(clear);

	}

	void initQuizOverPanel()
	{
		quizOverPanel=new JPanel();
		quizOverPanel.setLayout(null);
		quizOverPanel.setBounds(350,270,670,120);
		quizOverPanel.setBackground(new Color(193, 164, 222));
		f1.add(quizOverPanel);
		
		JLabel l1=new JLabel("Quiz Over");
		l1.setFont(new Font("Times New Roman",Font.BOLD,20));
		l1.setBounds(10,10,660,50);
		l1.setHorizontalAlignment(JLabel.CENTER);
		quizOverPanel.add(l1);

		JButton showResult=new JButton();      
		showResult.setBounds(150,70,120,30);
		showResult.setText("Show Result");
		showResult.addActionListener(this);
		quizOverPanel.add(showResult);

		JButton logout=new JButton("Logout");      
		logout.setBounds(300,70,120,30);
		logout.addActionListener(this);
		logout.addActionListener(this);
		quizOverPanel.add(logout);
	}
//--------------------------------display ques -----------------------------	
	void displayQues(int currQues)
	{
		ResultSet rs;
		rs=JDBCExample.fetchQues(currQues);
		t=3;
		// extracting data from database using column name
		try{
			while(rs.next())
			{
				//Retrieve by column name
				clear.doClick();
				l1.setText(Integer.toString(rs.getInt("id")));
				l2.setText(rs.getString("statement"));
				jrb1.setText(rs.getString("opt1"));
				jrb2.setText(rs.getString("opt2"));
				jrb3.setText(rs.getString("opt3"));
				jrb4.setText(rs.getString("opt4"));
				rs.close();
				}//end while loop
			}//end try
		catch(SQLException e)
		{}
		//after question is displayed start a stop watch
		
	}//end method displayQues
//--------------------------extend panel-----------------------------------------
	void initResultPanel()
	{
		JPanel resultPanel=new JPanel();
		resultPanel.setBounds(350,400,670,320);
		resultPanel.setBackground(new Color(193, 164, 222));
		resultPanel.setLayout(null);
		Font f=new Font("Times New Roman",Font.BOLD,20);
		int attempted=totalQues,correct=0,marks=0;
		
		//calculating marks
		for(int i=0;i<totalQues;i++)
		{	
			if(sheet[i].equals(""))
				attempted--;
			if(result[i])
				correct++;
		}
		marks=correct*5;

		JLabel l1=new JLabel("Total no. of questions: "+totalQues);
		l1.setBounds(10,80,250,50);
		l1.setFont(f);
		resultPanel.add(l1);

		JLabel l2=new JLabel("No. of questions attempted: "+attempted);
		l2.setBounds(10,140,310,50);
		l2.setFont(f);
		resultPanel.add(l2);
		
		JLabel l4=new JLabel("No. of questions correct: "+correct);
		l4.setBounds(10,200,250,50);
		l4.setFont(f);
		resultPanel.add(l4);

		JLabel l5=new JLabel("Total Marks: "+correct);
		l5.setBounds(10,260,300,50);
		l5.setFont(new Font("Times New Roman",Font.BOLD,30));
		resultPanel.add(l5);
		int rowAffected=JDBCExample.updateMarks(marks);
		if(rowAffected<=0)
			System.out.println("Problem uploading marks");
		f1.add(resultPanel);
		f1.setVisible(false);
		f1.setVisible(true);
	}
//------------------------ show date and time--------------------
	void startTimer()
	{
		Timer timer = new Timer(1000, new ActionListener() {
                //@Override
                public void actionPerformed(ActionEvent e) {
                    tickTock();
                    if(t>-2)
						ct.setText("Remaining Time"+(t--));
					else
						ct.setText("");
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

//------------------------- start stopwatch-------------------------------------
	java.util.TimerTask task=new java.util.TimerTask()
	{
		public void run()
		{
			next.doClick();
		}
	};
	
	public void startStopwatch() throws Exception
	{
		java.util.Timer timer=new java.util.Timer();
		long delay=3*1000+2000, interval=3*1000+2000;
		timer.schedule(task,delay,interval);
	}
//------------------------- action performed-------------------------------------
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Next"))
		{
			String optSelcted="";
			//--------------------- very poor code -------------------
			if(jrb1.isSelected())
				optSelcted=jrb1.getText();
			else if(jrb2.isSelected())
				optSelcted=jrb2.getText();
			else if(jrb3.isSelected())
				optSelcted=jrb3.getText();
			else if(jrb4.isSelected())
				optSelcted=jrb4.getText();
			//--------------------  poor code end -----------------------
			//System.out.println("Option "+optSelcted);
			sheet[currQues-1]=optSelcted;
			boolean isCorrectAnswer=JDBCExample.checkAnswer(optSelcted,currQues);
			if(isCorrectAnswer)
			{
					result[currQues-1]=true;
					//l3.setText("Correct");
					System.out.println("question no: "+currQues+" is correct");
			}
			else
			{
				result[currQues-1]=false;
				//l3.setText("Incorrect");
				System.out.println("question no: "+currQues+" is incorrect");
			}
			//--------increase current ques i.e. currQues and display next ques
			currQues++;
			if(currQues<=totalQues)
				displayQues(currQues);
			else
			{	
				initQuizOverPanel();//show quiz over panel
				f1.remove(quesPanel);
				f1.setVisible(false);
				f1.setVisible(true);
				//quesPanel.setVisible(false);
				//l3.setText("Quiz over");
			}
		}

		else if(ae.getActionCommand().equals("Clear Selection"))
		{
			//l3.setText("");
			unselect.setSelected(true);
			//repaint();
		}
		else if(ae.getActionCommand().equals("Show Result"))
		{
			initResultPanel();
		}
		else if(ae.getActionCommand().equals("Logout"))
		{
			JDBCExample.currUser="";
			Login l=new Login();
			f1.dispose();
			f1.setVisible(false);
		}
	}
}//end class Test