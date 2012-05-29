/**
 * 
 */
package Mousedatabase;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * @author evagita
 *
 */
public class BankFromMouse {
	
	
	private static MouseDataBase MDB;
	private static Scanner scan;
	private final static int exc = 0;
	
	static void ConnectToDB() throws SQLException
	{
		String Curs = "jdbc:mysql://localhost:3306/basename";
		String User = "user";
		String Password = "password";
		scan=new Scanner(System.in);
		MDB = new MouseDataBase(Curs,User,Password);
	}
	/**
	 * @param args
	 * @return 
	 * @throws IOException 
	 */
	//To show menu
	private static void Menu()
	{
		System.out.println("You can choose one of the following operation:");
		System.out.println("1. See my money");
		System.out.println("2. Send money");
		System.out.println("3. Create new account");
		System.out.println("4. Delete account");
		System.out.println("+++++++++++++++++++++");
		System.out.println("5. Exit");
		System.out.print("Choose the number: ");
	}
	public static void main(String[] args) throws SQLException
	{
		System.out.println("Welcome to Mouse State Bank!");
		ConnectToDB();	// Connecting to DB
		if (MDB.connected)// If this operation was successful
		{
			int num = exc;
			while (num != 5)
			{
				String User;
				String User2;
				String Password;
				Double Money = null;
				Menu();
				scan=new Scanner(System.in);
				try
				{
					num = scan.nextInt();
				} catch (Exception ex)
				{
					num = exc;
					System.out.println("Illegal expression! Try again.");
				}
				
				switch (num)
				{
				case 1:
					System.out.print("Enter the name of necessary person: ");
					User = scan.next();
					System.out.println("Enter his password:");
					Password = scan.next();
					Money = MDB.SeeKredit(User, Password);
					if (Money != MDB.Error)
						System.out.println("User "+User+" have "+Money+" on his account. ");
					else
						System.out.println("Error expression");
					//System.out.print(Money);
					break;
				case 2:
					System.out.println("Enter the name of person, whome money you want to send: ");
					User = scan.next();
					System.out.println("Enter his password: ");
					Password = scan.next();
					System.out.println("Enter the name of adressat-person: ");
					User2 = scan.next();
					System.out.println("How many money do you want to send? ");
					try
					{
						Money = scan.nextDouble();
					}catch(Exception ex)
					{
						System.out.println("Illegal expression(Error in money)");
						break;
					}
					if (MDB.SendMoney(User, Password, User2, Money))
						System.out.println("Sending is sukcesful!");
					else
						System.out.println("Sorry, something was wrong! ");
					// альтернатива:
					// Вывод сообщения вида: "Перечисление пользователю %имя% %сумма" от "имя": "
					//без перехода на новую строку, а дальше будет true или false
					//System.out.print(MDB.SendMoney(User, Password, User2, Money));
					break;
				case 3:
					System.out.println("Enter the name of new user: ");
					User = scan.next();
					System.out.println("Enter the password of new user: ");
					Password = scan.next();
					System.out.println("Enter the starting kredit of new user: ");
					Money = scan.nextDouble();
					if (MDB.CreateNewAcc(User, Password, Money))
						System.out.println("Creating is sucksesful! ");
					else
						System.out.println("Sorry, something was wrong! ");
					// альтернатива:
					// Вывод сообщения вида: "Создание пользователя %имя%: " без перехода
					// а дальше будет true или false
					//System.out.print(MDB.CreateNewAcc(User, Password, Money));
					break;
				case 4:
					System.out.println("Enter the name of deleting user: ");
					User = scan.next();
					System.out.println("Enter his password: ");
					Password = scan.next();
					if (MDB.DeleteAcc(User, Password))
						System.out.println("Sucksesful delete! ");
					else
						System.out.println("Sorry, something was wrong! ");
					// альтернатива:
					// Вывод сообщения вида: "Удаление пользователя %имя%: " без перехода
					// а дальше будет true или false
					//System.out.print(MDB.DeleteAcc(User, Password));
					break;
				case 5:
					System.exit(0);		
				}
			}
		}
		else
			System.out.println("Sorry, can't connect to DataBase! ");
	}

}
