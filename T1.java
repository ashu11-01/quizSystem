/*Documentation
author ashutosh
date 19/12/18
time 1826hrs
main flie: creates object, main method
*/
import java.sql.Connection;
class T1
{
	public static void main(String[] args) 
	{
		Connection conn=JDBCExample.connect();
		Login l=new Login();
		//AddQues aq=new AddQues();
	}
}
