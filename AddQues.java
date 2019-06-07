import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class AddQues extends JPanel implements ActionListener 
{
	JFrame f1;
	JPanel addQuesPanel=new JPanel();
	JTextArea ta1,ta2,ta3,ta4,ta5,ta6;
	JLabel l1,l2,l3,l4,l5,l6;
	JButton addButton,resetButton,back;
	
	AddQues()
	{
		f1=new JFrame("Add New Ques");

		initJLabel();
		initJTextArea();
		initJButton();
		
		f1.setSize(new Dimension(600,500));
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addQuesPanel.setLayout(new GridLayout(8,2,5,5));

		addQuesPanel.add(l1);
		addQuesPanel.add(ta1);
		addQuesPanel.add(l2);
		addQuesPanel.add(ta2);
		addQuesPanel.add(l3);
		addQuesPanel.add(ta3);
		addQuesPanel.add(l4);
		addQuesPanel.add(ta4);
		addQuesPanel.add(l5);
		addQuesPanel.add(ta5);
		addQuesPanel.add(l6);
		addQuesPanel.add(ta6);
		addQuesPanel.add(addButton);
		addQuesPanel.add(resetButton);
		addQuesPanel.add(back);
		addButton.addActionListener(this);
		resetButton.addActionListener(this);
		back.addActionListener(this);
		addQuesPanel.setVisible(true);
		f1.add(addQuesPanel);

		f1.setVisible(true);
		
	}

	void initJLabel()
	{
		l1=new JLabel("Question Statement");
		l2=new JLabel("Option 1");
		l3=new JLabel("Option 2");
		l4=new JLabel("Option 3");
		l5=new JLabel("Option 4");
		l6=new JLabel("Correct Answer");

	}

	void initJTextArea()
	{
		ta1=new JTextArea(10,50);
		ta2=new JTextArea(2,30);
		ta3=new JTextArea(2,30);
		ta4=new JTextArea(2,30);
		ta5=new JTextArea(2,30);
		ta6=new JTextArea(2,30);

	}
	void initJButton()
	{
		addButton=new JButton("Add");
		resetButton=new JButton("Reset");
		back=new JButton("Back");
	}
	
	public String[] getinput()
	{
		String inputObj[]=new String[6];
		inputObj[0]=ta1.getText(); //question statement
		inputObj[1]=ta2.getText(); //option1
		inputObj[2]=ta3.getText(); //option2
		inputObj[3]=ta4.getText(); //option3
		inputObj[4]=ta5.getText(); //option4
		inputObj[5]=ta6.getText(); //answer
		//-------------validating input---------------------
		int flag=0;
		for(int i=1;i<=4;i++)
		{	
			for(int j=i+1;j<=4;j++)
			{	
				System.out.println(inputObj[i]);
				System.out.println(inputObj[j]);
				if(inputObj[i].equals(inputObj[j]))
					{//System.out.println("options are repeated");
						flag=1;
						break;
					}
			}
			if(flag==1)
				break;					
		}
		String error[]={"-!error!-"};
		if(flag==1) //more than one options are same
			return null;
		else if(flag==0) //options are unique
			{
				//now correct answer must belong to one of the options
				if(inputObj[5].equals(inputObj[1]) || inputObj[5].equals(inputObj[2]) || inputObj[5].equals(inputObj[3]) || inputObj[5].equals(inputObj[4]) )
					return inputObj; //answer belongs to set of options
				else
					return error; // answer not belong to set of options
			}
		return null;
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Add"))
		{
			String ob[]=getinput();
			int rowAffected=-1;
			if(ob==null)
				JOptionPane.showMessageDialog(f1,"Options must be unique");
			else if(ob[0].equals("-!error!-"))
				JOptionPane.showMessageDialog(f1,"Correct answer must be one of the options");
			else
				rowAffected=JDBCExample.insertQues(getinput());
			
			if(rowAffected>0)
				{
					JOptionPane.showMessageDialog(f1,rowAffected+" new question inserted");
					resetButton.doClick();
				}	
			else
				{
					JOptionPane.showMessageDialog(f1,"New Question could not be added");
					//resetButton.doClick();
				}
		}

		else if(ae.getActionCommand().equals("Reset"))
			{
				ta1.setText("");
				ta2.setText("");
				ta3.setText("");
				ta4.setText("");
				ta5.setText("");
				ta6.setText("");
			}
		else if(ae.getActionCommand().equals("Back"))
			{
				f1.dispose();
				f1.setVisible(false);
				new Welcome();
			}
	}

}