// Sorina-Andreea Ghimpu 2196670
package com.bham.fsd.assignments.jabberserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;

public class JabberServer {
	
	private static String dbcommand = "jdbc:postgresql://127.0.0.1:5432/postgres";
	private static String db = "postgres";
	private static String pw = "";

	private static Connection conn;
	
	public static Connection getConnection() {
		return conn;
	}

	public static void main(String[] args) {

		JabberServer jabber = new JabberServer();
		JabberServer.connectToDatabase();
		jabber.resetDatabase();

		/*
		 * Put calls to your methods here to test them.
		 */

		//System.out.println(jabber.getFollowerUserIDs(8));
		//System.out.println(jabber.getFollowingUserIDs(12));
		//System.out.println(jabber.getLikesOfUser(0));
		//System.out.println(jabber.getTimelineOfUser(12));
		//System.out.println(jabber.getMutualFollowUserIDs());
		//System.out.println(jabber.getUsersWithMostFollowers());
		//System.out.println(jabber.getUserId("solesurvivor"));
		//jabber.addUser("Johnny Silverhand", "robertjohnlinder@samurai.com");
		//jabber.addJab("Johnny Silverhand", "wake up, samurai, we got a game to fix");
		//jabber.addFollower(13, 3);
		//jabber.addLike(13,4);
	}
	
	public ArrayList<String> getFollowerUserIDs(int userid) {

		ArrayList<String> listOfUsers= new ArrayList<String>();

		try
		{
			PreparedStatement ps = conn.prepareStatement("select userida from follows where useridb = ?");

			ps.setInt(1, userid);

			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				listOfUsers.add(res.getObject("userida").toString());
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return listOfUsers;
	}

	public ArrayList<String> getFollowingUserIDs(int userid) {

		ArrayList<String> listOfFollowing= new ArrayList<String>();

		try
		{
			PreparedStatement ps = conn.prepareStatement("select useridb from follows where userida = ?");

			ps.setInt(1, userid);

			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				listOfFollowing.add(res.getObject("useridb").toString());
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return listOfFollowing;
	}
	
	public ArrayList<ArrayList<String>> getMutualFollowUserIDs() {

		ArrayList<ArrayList<String>> listOfMutuals = new ArrayList<ArrayList<String>>();


		try {

			PreparedStatement stmt = conn.prepareStatement("select A1.userida, A1.useridb from follows A1, follows A2 where A1.userida = A2.useridb and A1.useridb = A2.userida AND A1.userida < A2.userida order by userida, useridb;");

			ResultSet res = stmt.executeQuery();

			while (res.next()) {
				ArrayList<String> r = new ArrayList<String>();
				r.add(res.getObject("userida").toString());
				r.add(res.getObject("useridb").toString());
				listOfMutuals.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfMutuals;
	}

	public ArrayList<ArrayList<String>> getLikesOfUser(int userid) {


		ArrayList<ArrayList<String>> listOfUsernamesAndTexts = new ArrayList<ArrayList<String>>();


		try {

			PreparedStatement stmt = conn.prepareStatement("select username, jabtext from (select userid, jabtext from (select jabid from likes where userid = ?) as a1 natural join jab) as a2 natural join jabberuser;");

			stmt.setInt(1,userid);

			ResultSet res = stmt.executeQuery();

			while (res.next()) {
				ArrayList<String> r = new ArrayList<String>();
				r.add(res.getObject("username").toString());
				r.add(res.getObject("jabtext").toString());
				listOfUsernamesAndTexts.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfUsernamesAndTexts;
	}
	
	public ArrayList<ArrayList<String>> getTimelineOfUser(int userid) {

		ArrayList<ArrayList<String>> listOfTimeline = new ArrayList<ArrayList<String>>();


		try {

			PreparedStatement stmt = conn.prepareStatement("select username, jabtext from follows, jab, jabberuser where follows.userida = ? and follows.useridb=jab.userid and follows.useridb = jabberuser.userid;");

			stmt.setInt(1,userid);

			ResultSet res = stmt.executeQuery();

			while (res.next()) {
				ArrayList<String> r = new ArrayList<String>();
				r.add(res.getObject("username").toString());
				r.add(res.getObject("jabtext").toString());
				listOfTimeline.add(r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfTimeline;

	}

	public void addJab(String username, String jabtext) {

		int currentId = getUserId(username);
		int newJabid = generateNewJabid();

		try
		{
			PreparedStatement ps = conn.prepareStatement("Insert into jab (values(?,?,?))");
			ps.setInt(1, newJabid);
			ps.setInt(2, currentId);
			ps.setString(3, jabtext);
			ps.executeUpdate();
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addUser(String username, String emailadd) {

		int newId = generateNewId();

		try
		{
			PreparedStatement ps = conn.prepareStatement("Insert into jabberuser (values(?,?,?))");
			ps.setInt(1, newId);
			ps.setString(2, username);
			ps.setString(3, emailadd);
			ps.executeUpdate();
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	public void addFollower(int userida, int useridb) {

		try
		{
			PreparedStatement ps = conn.prepareStatement("Insert into follows (values(?,?))");
			ps.setInt(1, userida);
			ps.setInt(2, useridb);
			ps.executeUpdate();
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}
	} 
	
	public void addLike(int userid, int jabid) {
		try
		{
			PreparedStatement ps = conn.prepareStatement("Insert into likes (values(?,?))");
			ps.setInt(1, userid);
			ps.setInt(2, jabid);
			ps.executeUpdate();
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getUsersWithMostFollowers() {


		ArrayList<String> listOfUsersWithMostFollowers= new ArrayList<String>();

		try
		{
			PreparedStatement ps = conn.prepareStatement("select jabberuser.userid from follows, jabberuser where jabberuser.userid = follows.useridb group by jabberuser.userid having count(follows.userida) >= all (select count(userida) from follows group by useridb order by count(userida) desc);");

			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				listOfUsersWithMostFollowers.add(res.getObject("userid").toString());
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return listOfUsersWithMostFollowers;

	}


	private int generateNewId() //utility method to generate new id for the addJab method;
	{
		int max = -1;

		try
		{
			PreparedStatement ps = conn.prepareStatement("select max(userid) from jabberuser;");

			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				max = res.getInt(1);
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		if(max < 0) return max;
		else return max + 1;
	}


	private int getUserId(String username) //utility method to get id from the user with the username 'username';
	{
		int currentuserid = -1;
		try
		{
			PreparedStatement ps = conn.prepareStatement("select userid from jabberuser where username = ?");
			ps.setString(1, username);

			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				currentuserid = res.getInt(1);
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return currentuserid;
	}

	private int generateNewJabid() //utility method to generate new jabid for the addJab method;
	{
		int maxjabid = -1;

		try
		{
			PreparedStatement ps = conn.prepareStatement("select max(jabid) from jab;");

			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				maxjabid = res.getInt(1);
			}
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		if(maxjabid < 0) return maxjabid;
		else return maxjabid + 1;
	}
	
	public JabberServer() {}
	
	public static void connectToDatabase() {

		try {
			conn = DriverManager.getConnection(dbcommand,db,pw);

		}catch(Exception e) {		
			e.printStackTrace();
		}
	}

	/*
	 * Utility method to print an ArrayList of ArrayList<String>s to the console.
	 */
	private static void print2(ArrayList<ArrayList<String>> list) {
		
		for (ArrayList<String> s: list) {
			print1(s);
			System.out.println();
		}
	}
		
	/*
	 * Utility method to print an ArrayList to the console.
	 */
	private static void print1(ArrayList<String> list) {
		
		for (String s: list) {
			System.out.print(s + " ");
		}
	}

	public void resetDatabase() {
		
		dropTables();
		
		ArrayList<String> defs = loadSQL("jabberdef");
	
		ArrayList<String> data =  loadSQL("jabberdata");
		
		executeSQLUpdates(defs);
		executeSQLUpdates(data);
	}
	
	private void executeSQLUpdates(ArrayList<String> commands) {
	
		for (String query: commands) {
			
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<String> loadSQL(String sqlfile) {
		
		ArrayList<String> commands = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sqlfile + ".sql"));
			
			String command = "";
			
			String line = "";
			
			while ((line = reader.readLine())!= null) {
				
				if (line.contains(";")) {
					command += line;
					command = command.trim();
					commands.add(command);
					command = "";
				}
				
				else {
					line = line.trim();
					command += line + " ";
				}
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return commands;
		
	}

	private void dropTables() {
		
		String[] commands = {
				"drop table jabberuser cascade;",
				"drop table jab cascade;",
				"drop table follows cascade;",
				"drop table likes cascade;"};
		
		for (String query: commands) {
			
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
