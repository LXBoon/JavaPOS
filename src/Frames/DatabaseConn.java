package Frames;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.sql.Date.valueOf;

public class DatabaseConn {


    static Connection conn;
    static Statement st;
    static ResultSet rs;

    //on device mysql - my php admin   host
    /*
    public  static String connString ="jdbc:mysql://localhost:3306/myshopdb";
    public  static String user ="root";
    public  static String password ="Reet369*";

     */

    //online mysql - my php admin   host
    public static String connString="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11495022";
    public static String user = "sql11495022";
    public static String password = "rB154EAxrZ";
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
                columns[i-1]= rsd.getColumnLabel(i);
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
                tm.addRow(new Object[]{id, name,price,quantity });
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


    public static int GetReceiptNum(){
        int num=0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(connString, user, password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select count(ID) from receipt_table");
            rs.next();
            num=rs.getInt(1);
            con.close();
        }catch(Exception e){
            System.out.println("Failed");
            System.out.println(e);
        }
        return num+1;
    }

    public static void sellTable(DefaultTableModel tm, int receiptNum){
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "SELECT Name,Quantity,Price FROM sells_table where Recipt_id ='"+receiptNum+"'";//Storing MySQL query in A string variable
            ResultSet rs = st.executeQuery(query);//executing query and storing result in ResultSet
            //System.out.println("Good");
            ResultSetMetaData rsd = rs.getMetaData();
            columns = new String[rsd.getColumnCount()];
            for (int i = 1; i<= rsd.getColumnCount();i++){
                columns[i-1]= rsd.getColumnLabel(i).toString();
                if (tm.getColumnCount()>= columns.length){
                }
                else
                    tm.addColumn(rsd.getColumnName(i));
            }
            while (rs.next()){
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                tm.addRow(new Object[]{name,quantity,price });
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Bad");
            throw new RuntimeException(e);
        }
    }
    public static void addSell(long Item_id,String Item_name, int Item_quantity,double Item_price,int rec_id){
        try {
            long id= Item_id;
            String name=Item_name;
            int quantity=Item_quantity,receipt_id=rec_id;
            double price=Item_price;
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();//crating statement object
            String query = "insert into sells_table (Item_id,Name,Quantity,Price,Recipt_id) values(?,?,?,?,?)";//Storing MySQL query in A string variable
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong (1,id );
            preparedStmt.setString (2, name);
            preparedStmt.setInt(3, quantity);
            preparedStmt.setDouble   (4, price);
            preparedStmt.setInt(5, receipt_id);
            preparedStmt.execute();
            System.out.println("Good");
        } catch (SQLException e) {
            System.out.println("Error add sell");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public static void newReceipt(int receipt_id){
        try{
            int id= receipt_id;
            SimpleDateFormat DH= new SimpleDateFormat("yyyy-MM-dd HH-mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String Date = DH.format(date);
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();
            String query = "insert into receipt_table (ID,Date) values (?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong (1,id );
            preparedStmt.setString(2,Date);
            preparedStmt.execute();

        }catch (Exception e){
            System.out.println("Error new r");
        }
    }

    public static String getItemName(long id) throws SQLException {
        String n = null;
        conn = DriverManager.getConnection(connString, user, password);
        String sql ="select Name from items_table where ID = '"+id+"'";
        st = conn.createStatement();
        ResultSet resultSet=st.executeQuery(sql);
        if (resultSet.next()){
            n = resultSet.getString("Name");
        }
        return n;
    }
    public static double getItemPrice(long id) throws SQLException {
        double p = 0;
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select Price from items_table where ID = '"+id+"'";
            st = conn.createStatement();
            ResultSet resultSet=st.executeQuery(sql);
            if (resultSet.next()){
                p = resultSet.getDouble("Price");
            }
        return p;
    }

    public static double getTotalPrice(int id){
        double p = 0;
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select sum(Price) AS TotalPrice from sells_table where Recipt_id ='"+id+"'";
            st = conn.createStatement();
            ResultSet resultSet=st.executeQuery(sql);
            if (resultSet.next()){
                p = resultSet.getDouble("TotalPrice");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
    public static void setPurchase(int receipt_id,double TotalPrice, double paidAmount, double change,String type){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="update receipt_table set TotalPrice= '"+TotalPrice+"', Paidamount= '"+paidAmount+"' ,Changeamount= '"+change+"', Type='"+type+"' where ID = "+receipt_id+"";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }

}

