import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Header
{
	JLabel l1;
	JPanel topPanel;

	JPanel initTopPanel()
	{
		topPanel=new JPanel();
		topPanel.setLayout(null);
		topPanel.setBackground(new Color(113,175,190));
		topPanel.setBounds(90,60,1210,150);
		l1=new JLabel("Tech-Hunch");
		l1.setBounds(0,0,1210,140);
		l1.setHorizontalAlignment(JLabel.CENTER);
		//l1.setVerticalAlignment(JLabel.TOP);
		l1.setFont(new Font("Times New Roman",Font.BOLD,70));
		topPanel.add(l1);
		return topPanel;
	}
}