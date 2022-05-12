package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {


    static Connection conn;
    static Statement st;
    static ResultSet rs;

    public  static String connString ="jdbc:mysql://localhost:3306/myshopdb";
    public  static String user ="root";
    public  static String password ="Root147*";
    public  static  boolean foundUser;


    public static void findUser(String x, String y){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select * from user_table where Name = '"+x+"' and Password ='"+y+"'";
            st = conn.createStatement();
            ResultSet resultSet=st.executeQuery(sql);
            //System.out.println(rs.getString("Name").equals(x) && rs.getString("Password").equals(y));
            while (resultSet.next()){
                //System.out.println(resultSet.getString("Name") +" "+ resultSet.getString("Password"));
                if (resultSet.getString("Name").equals(x) && resultSet.getString("Password").equals(y)){
                    System.out.println("Found");
                    foundUser = true;
                    break;
                }
            }
            conn.close();

            //System.out.println("Connected");

        } catch (SQLException e) {
            System.out.println("Failed");
            foundUser = false;
            throw new RuntimeException(e);
        }


    }

    void connCheck(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(connString, user, password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from user_table Where Name='Admin'");
            System.out.println("Connected");
            while(rs.next()) {
                if (rs.getString("Name").equals("Admin")){
                    break;
                }
                System.out.println(rs.getInt("ID") + "," + rs.getString("Name") + "," + rs.getString("Password"));

            }
            con.close();
        }catch(Exception e){
            System.out.println("Failed");
            System.out.println(e);
        }
    }
    public static String[] columns;
    public static String[][] data;
    public static void displayItemList(DefaultTableModel tm){

        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM items_table";//Storing MySQL query in A string variable
            ResultSet rs = st.executeQuery(query);//executing query and storing result in ResultSet
            //System.out.println("Good");
            ResultSetMetaData rsd = rs.getMetaData();
            columns = new String[rsd.getColumnCount()];
            for (int i = 1; i<= rsd.getColumnCount();i++){
                columns[i-1]= rsd.getColumnLabel(i).toString();
                //System.out.println(rsd.getColumnName(i));
                if (tm.getColumnCount()>= columns.length){

                }
                else
                tm.addColumn(rsd.getColumnName(i));
            }
            while (rs.next()){
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                String price = rs.getString("Price");
                String quantity = rs.getString("Quantity");
                tm.addRow(new Object[]{id, name,price,quantity });//Adding row in Table
                //System.out.println(rs.getInt("ID") + "," + rs.getString("Name") + "," + rs.getString("Price") +","+rs.getString("Quantity"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Bad");
            throw new RuntimeException(e);
        }
    }

    public  static void addToItemList(long id,String name, double price,int quantity ){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();//crating statement object
            String query = "insert into items_table (ID,Name,Price,Quantity) values(?,?,?,?)";//Storing MySQL query in A string variable
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong (1,id );
            preparedStmt.setString (2, name);
            preparedStmt.setDouble   (3, price);
            preparedStmt.setInt(4, quantity);
            preparedStmt.execute();
            System.out.println("Good");
        } catch (SQLException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }

    public  static void  deleteFromItemList(long x){

        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();
            String query1 = "delete from  items_table where ID="+x;
            st.executeUpdate(query1);
            System.out.println("Record is deleted from the table successfully..................");
        } catch (SQLException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }



    }

    public static void  updateItemFromList(int id,String name, double price,int quantity){
        //UPDATE Customers
        //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        //WHERE CustomerID = 1;
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "update items_table set Name = ?,Price=?,Quantity=? where ID =? ";//Storing MySQL query in A string variable
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, name);
            preparedStmt.setDouble   (2, price);
            preparedStmt.setInt(3, quantity);
            preparedStmt.setInt (4,id );

            preparedStmt.execute();

            System.out.println("Good");
        } catch (SQLException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }


    }

    public static void search(DefaultTableModel tm,String x,String y){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            //SELECT * FROM Customers
            //WHERE CustomerName LIKE '%or%'
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM items_table Where "+y+" like '%"+x+"%'";//Storing MySQL query in A string variable
            ResultSet rs = st.executeQuery(query);//executing query and storing result in ResultSet
            //System.out.println("Good");
            ResultSetMetaData rsd = rs.getMetaData();
            columns = new String[rsd.getColumnCount()];
            for (int i = 1; i<= rsd.getColumnCount();i++){
                columns[i-1]= rsd.getColumnLabel(i).toString();
                //System.out.println(rsd.getColumnName(i));
                if (tm.getColumnCount()>= columns.length){

                }
                else
                    tm.addColumn(rsd.getColumnName(i));
            }
            while (rs.next()){
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                String price = rs.getString("Price");
                String quantity = rs.getString("Quantity");
                tm.addRow(new Object[]{id, name,price,quantity });//Adding row in Table
                //System.out.println(rs.getInt("ID") + "," + rs.getString("Name") + "," + rs.getString("Price") +","+rs.getString("Quantity"));
            }
            conn.close();
        } catch (SQLException e) {

            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        //findUser("Admin","admin");
        //displayItemList("items_table");
        //addToItemList(2,"Watermelon",3.5,10);
        //updateItemFromList(2,"Water Melon",5.5,25);


    }

}

