import java.sql.*;
class JDBCExample
{
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost/quiz";

	static final String USER="root";
	static final String PASS="";

	static Connection conn=null;
	public static String currUser=""; //user that is currently logged in
	public static Connection connect()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/quiz";
			String username="root";
			String password="";
			//System.out.println("Connecting to a selected database");
			conn=DriverManager.getConnection(url,username,password);
			//System.out.println("Connected database successfully");
		} // end try

		//---------------------------- handling exception ------------------------
		catch(SQLException se)
		{
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e)
		{
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return conn;
	}// end method connect
	
	//----------------validate login----------------------
	public static int validateLogin(String ob[])
	{
		boolean flag=false;
		int num_user=0,num_pswd=0;
		String inputUserId=ob[0],inputPassword=ob[1];
		if(inputUserId.equals(""))
			{
			 	
			 	return 1; //userId fielsd is empty
			 }
		else if(inputPassword.equals(""))
			{ return 2;} //Password field is empty

		//System.out.println("userid"+inputUserId+"password"+inputPassword);
		
		ResultSet rs=null;
		try{
			Statement stmt=conn.createStatement();
			String query = "SELECT user_id FROM user where user_id='"+inputUserId+"'";
			rs = stmt.executeQuery(query);
			//rs.last(); num_user=rs.getRow(); rs.beforeFirst();
			if(rs.next())
			num_user=1;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		if(num_user==1) //exactly one user is found (user_id is unique in database table)
		{
			//match password
			try{
				Statement stmt=conn.createStatement();
				String query = "SELECT user_id FROM user where user_id='"+inputUserId+"' and password=sha1('"+inputPassword+"')";
				rs = stmt.executeQuery(query);
				//rs.last(); num_pswd=rs.getRow(); rs.beforeFirst();
				if(rs.next())
					{
						num_pswd=1;
						currUser=inputUserId;
						System.out.println("Current user: "+currUser);
					}
			}
			catch(SQLException e)
			{}
	
			if(num_pswd==1) //exactly one row means password and user id matched
				return 0; //everything good
			else
				return 4; //password is wrong
		}
		else
			return 3; //user id not found
	}
	public static int totalQues()  //calculate total no of ques
	{
		ResultSet rs=null;
		int totalQues=0;
		try{
			Statement stmt=conn.createStatement();
			String query = "SELECT * FROM ques where 1";
			rs = stmt.executeQuery(query);
			rs.last(); totalQues=rs.getRow(); rs.beforeFirst();
		}
		catch(SQLException e)
		{}
		System.out.println(totalQues);
		return totalQues;
	}// end method totalQues
	
	public static ResultSet fetchQues(int currQues) // fetch ques from database table 'ques'
	{	
		ResultSet rs=null;
		try{
			Statement stmt=conn.createStatement();
			String query = "SELECT * FROM ques where id="+currQues;
			rs = stmt.executeQuery(query);
		}
		catch(SQLException e)
		{}
		//System.out.println("Select query execute");
		return rs;
	}// end method fetchData

	public static boolean checkAnswer(String option, int currQues)
	{
		ResultSet rs=null;
		String correctAns="";
		//System.out.println("currQues"+currQues);
		try
		{
			Statement stmt=conn.createStatement();
			//extract correct answer from database table 'ques'

			String query="Select ans from ques where id="+currQues;
			rs=stmt.executeQuery(query);
			//converting resultset into string
			while(rs.next())
			{	
				correctAns=rs.getString("ans");
				//System.out.println(Integer.toString(rs.getInt("id")));
			}//end while
		}//end try
		catch(SQLException e)
		{}
		//System.out.print("ans is"+correctAns);
		if(option.equals(correctAns))
			return true;
		else
			return false;

	}//end method checkAnswer

	public static int insertQues(String[] input)
	{
		/*testing the recieved input
		for(int i=0;i<6;i++)
			System.out.println(input[i]);
		*/
		String insert="Insert into ques values(null,?,?,?,?,?,?)";
		int x=-1;
		try
		{
			PreparedStatement stmt=null;
			stmt=conn.prepareStatement(insert);
			for(int i=0;i<6;i++)
				stmt.setString(i+1,input[i]);
			x=stmt.executeUpdate();
		}
		catch(SQLException e)
		{ e.getMessage(); }
		return x;
	}

	public static String[] fetchUserDetails() // fetch user details from database table 'user'
	{	
		String userDetails[]=new String[5];
		ResultSet rs=null;
		try{
			Statement stmt=conn.createStatement();
			String query = "SELECT * FROM user where user_id=\'"+currUser+"\'";
			rs = stmt.executeQuery(query);
	
			while(rs.next())
			{
				//System.out.println("result set rec");
				userDetails[0]=rs.getString("name");
				userDetails[1]=rs.getString("user_id");
				userDetails[2]=rs.getString("class");
				userDetails[3]=rs.getString("roll");
				userDetails[4]=rs.getString("user_type");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userDetails;
	}// end method fetchUserDetails

	public static boolean isAdmin()
	{
		ResultSet rs=null;
		String type="";
		try{
			Statement stmt=conn.createStatement();
			System.out.println(currUser);
			String query = "SELECT user_type FROM user where user_id=\'"+currUser+"\'";
			rs = stmt.executeQuery(query);
	
			while(rs.next())
			{
				type=rs.getString("user_type");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(type.equals("1"))
			return true;
		else
			return false;
	}

	public static int updateMarks(int marks)
	{
		String insert="UPDATE user set marks="+marks+" where user_id=\'"+currUser+"\'";
		int x=-1;
		try
		{
			PreparedStatement stmt=null;
			stmt=conn.prepareStatement(insert);
			
			x=stmt.executeUpdate();
		}
		catch(SQLException e)
		{ e.getMessage(); }
		return x;
	}
}//end class JDBCExample
