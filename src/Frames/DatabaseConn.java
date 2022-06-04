package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.sql.Date.valueOf;

public class DatabaseConn {


    static Connection conn;
    static Statement st;
    static ResultSet rs;
    static PreparedStatement ps;

    //on device mysql - my php admin   host

    public  static String connString ="jdbc:mysql://localhost:3306/myshopdb";
    public  static String user ="root";
    public  static String password ="Reet369*";

    //online mysql - my php admin   host
    /*
    public static String connString="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11495022";
    public static String user = "sql11495022";
    public static String password = "rB154EAxrZ";

     */
    public  static  boolean foundUser;


    public static void findUser(String x, String y){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select * from user_table where Name = '"+x+"' and Password ='"+y+"'";
            st = conn.createStatement();
            rs=st.executeQuery(sql);

            while (rs.next()){

                if (rs.getString("Name").equals(x) && rs.getString("Password").equals(y)){

                    foundUser = true;
                    break;
                }
            }
            conn.close();

        } catch (SQLException e) {
            foundUser = false;
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    void connCheck(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(connString, user, password);
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select * from user_table Where Name='Admin'");

            while(rs.next()) {
                if (rs.getString("Name").equals("Admin")){
                    break;
                }
            }
            con.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
    public static String[] columns;
    public static String[][] data;
    public static void displayItemList(DefaultTableModel tm){
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM items_table";//Storing MySQL query in A string variable
            rs = st.executeQuery(query);//executing query and storing result in ResultSet

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
                String tax = rs.getString("TaxPercentage");
                String quantity = rs.getString("Quantity");
                tm.addRow(new Object[]{id, name,price,tax,quantity });
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public  static void addToItemList(long id,String name, double price,int quantity,int tax ){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();//crating statement object
            String query = "insert into items_table (ID,Name,Price,TaxPercentage,Quantity) values(?,?,?,?,?)";//Storing MySQL query in A string variable
            ps = conn.prepareStatement(query);
            ps.setLong (1,id );
            ps.setString (2, name);
            ps.setDouble   (3, price);
            ps.setInt(4,tax);
            ps.setInt(5, quantity);
            ps.execute();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public  static void  deleteFromItemList(long x){

        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();
            String query1 = "delete from  items_table where ID="+x;
            st.executeUpdate(query1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }



    }

    public static void  updateItemFromList(int id,String name, double price,int quantity, int tax){
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "update items_table set Name = ?,Price=?,TaxPercentage =?,Quantity=? where ID =? ";//Storing MySQL query in A string variable
            ps = conn.prepareStatement(query);
            ps.setString (1, name);
            ps.setDouble   (2, price);
            ps.setInt(3, quantity);
            ps.setInt(4,tax);
            ps.setInt (5,id );
            ps.execute();
            JOptionPane.showMessageDialog(null,"Updated successfully");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Updated failed");
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }


    }

    public static void search(DefaultTableModel tm,String x,String y){
        try {
            conn = DriverManager.getConnection(connString, user, password);

            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM items_table Where "+y+" like '%"+x+"%'";
            rs = st.executeQuery(query);
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
                String id = rs.getString("ID");
                String name = rs.getString("Name");
                String price = rs.getString("Price");
                String tax = rs.getString("TaxPercentage");
                String quantity = rs.getString("Quantity");
                tm.addRow(new Object[]{id, name,price,tax,quantity });//Adding row in Table

            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }


    public static int GetReceiptNum(){
        int num=0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(connString, user, password);
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select count(ID) from receipt_table");
            rs.next();
            num=rs.getInt(1);
            con.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return num+1;
    }

    public static void sellTable(DefaultTableModel tm, int receiptNum){
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM sells_table where Recipt_id ='"+receiptNum+"'";//Storing MySQL query in A string variable
            rs = st.executeQuery(query);//executing query and storing result in ResultSet

            ResultSetMetaData rsd = rs.getMetaData();
            columns = new String[rsd.getColumnCount()];
            for (int i = 1; i<= rsd.getColumnCount()-1;i++){
                columns[i-1]= rsd.getColumnLabel(i);
                if (tm.getColumnCount()!= columns.length-1){
                    tm.addColumn(rsd.getColumnName(i));
                }
            }
            while (rs.next()){
                int id =rs.getInt("ID");
                long item_id = rs.getLong("Item_id");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                double tax = rs.getDouble("Tax");
                tm.addRow(new Object[]{id,item_id,name,quantity,price,tax});
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
    public static void addSell(long Item_id,String Item_name, int Item_quantity,double Item_price,double TaxPercentage,int rec_id){
        try {
            long id= Item_id;
            String name=Item_name;
            int quantity=Item_quantity,receipt_id=rec_id;
            double price=Item_price;
            double tax = TaxPercentage;
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();//crating statement object
            String query = "insert into sells_table (Item_id,Name,Quantity,Price,Tax,Recipt_id) values(?,?,?,?,?,?)";//Storing MySQL query in A string variable
            ps = conn.prepareStatement(query);
            ps.setLong (1,id );
            ps.setString (2, name);
            ps.setInt(3, quantity);
            ps.setDouble   (4, price);
            ps.setDouble(5,tax);
            ps.setInt(6, receipt_id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
    public static void newReceipt(int receipt_id,String date){
        try{
            int id= receipt_id;
            String Date = date;
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();
            String query = "insert into receipt_table (ID,Date) values (?,?)";
            ps = conn.prepareStatement(query);
            ps.setLong (1,id );
            ps.setString(2,Date);
            ps.execute();

        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static String getItemName(long id){
        String n = null;
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql = "select Name from items_table where ID = '" + id + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                n = rs.getString("Name");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return n;
    }
    public static double getItemPrice(long id){
        double p = 0;
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select Price from items_table where ID = '"+id+"'";
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            if (rs.next()){
                p = rs.getDouble("Price");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }

        return p;
    }

    public static int getItemTax(long id){
        int tax = 0;
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select TaxPercentage from items_table where ID = '"+id+"'";
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            if (rs.next()){
                tax = rs.getInt("TaxPercentage");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { }
            }
        }
        return tax;
    }




    public static double getTotalPrice(int id){
        double p = 0;
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="select sum(Price) AS TotalPrice from sells_table where Recipt_id ='"+id+"'";
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            if (rs.next()){
                p = rs.getDouble("TotalPrice");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return p;
    }


    public static void setPurchase(int receipt_id,double TotalPrice, double paidAmount, double change,String type,double tax){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="update receipt_table set TotalPrice= '"+TotalPrice+"', Paidamount= '"+paidAmount+"' ,Changeamount= '"+change+"', Type='"+type+"', TotalTax='"+tax+"' where ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,receipt_id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
    public static void deleteSell(int id,long itemID ){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();
            String query1 = "delete from  sells_table where ID="+id+" and Item_id = "+itemID+"";
            st.executeUpdate(query1);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static void updateQuantity(int id, long itemID,int qty,double price){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="update sells_table set Quantity = '"+qty+"', Price = '"+price+"'  where ID="+id+" and Item_id = "+itemID+"";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }


    public static void showStaffTable(DefaultTableModel tm){
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM staff_table";//Storing MySQL query in A string variable
            rs = st.executeQuery(query);//executing query and storing result in ResultSet

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
                String idNum = rs.getString("IdNum");
                String name = rs.getString("FirstName");
                String price = rs.getString("LastName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String position = rs.getString("Position");
                String salary = rs.getString("Salary");
                tm.addRow(new Object[]{id,idNum, name,price,phone,email,position,salary });
            }
            conn.close();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static void addStaff(long id, String fn, String ln, long phone, String email, String position, double salary){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();//crating statement object
            String query = "insert into staff_table (IdNum,FirstName,LastName,Phone,Email,Position,Salary) values(?,?,?,?,?,?,?)";//Storing MySQL query in A string variable
            ps = conn.prepareStatement(query);
            ps.setLong(1,id);
            ps.setString(2,fn);
            ps.setString(3,ln);
            ps.setLong(4,phone);
            ps.setString(5,email);
            ps.setString(6,position);
            ps.setDouble(7,salary);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static void updateStaff(int id, long iDNum,String firstName,String lastName,long phone,String email,String position, double salary){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            String sql ="update staff_table set IdNum = '"+iDNum+"', FirstName = '"+firstName+"', LastName ='"+lastName+"', Phone='"+phone+ "'," +
                    " Email='"+email+"', Position='"+position+"', Salary='"+salary+"' where ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static void deleteStaff(int id){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            st = conn.createStatement();
            String query1 = "delete from staff_table where ID="+id+"";
            st.executeUpdate(query1);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static void searchStaff(DefaultTableModel tm,String x, String y){
        try {
            conn = DriverManager.getConnection(connString, user, password);
            //SELECT * FROM Customers
            //WHERE CustomerName LIKE '%or%'
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM staff_table Where "+y+" like '%"+x+"%'";
            rs = st.executeQuery(query);
            ResultSetMetaData rsd = rs.getMetaData();
            columns = new String[rsd.getColumnCount()];
            for (int i = 1; i<= rsd.getColumnCount();i++){
                columns[i-1]= rsd.getColumnLabel(i).toString();
                if (tm.getColumnCount()>= columns.length){

                }
                else {
                    tm.addColumn(rsd.getColumnName(i));
                }
            }
            while (rs.next()){
                String id = rs.getString("ID");
                String idNum = rs.getString("IdNum");
                String name = rs.getString("FirstName");
                String price = rs.getString("LastName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String position = rs.getString("Position");
                String salary = rs.getString("Salary");
                tm.addRow(new Object[]{id,idNum, name,price,phone,email,position,salary });
            }
            conn.close();
        } catch (SQLException e) {


            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public static void logTable(DefaultTableModel tm){
        //SELECT * FROM myshopdb.item_log_table;
        try {
            conn = DriverManager.getConnection(connString,user,password);
            st = conn.createStatement();//crating statement object
            String query = "SELECT * FROM myshopdb.item_log_table";//Storing MySQL query in A string variable
            rs = st.executeQuery(query);//executing query and storing result in ResultSet

            ResultSetMetaData rsd = rs.getMetaData();
            columns = new String[rsd.getColumnCount()];
            for (int i = 1; i<= rsd.getColumnCount();i++){
                columns[i-1]= rsd.getColumnLabel(i);
                if (tm.getColumnCount()>= columns.length){
                }
                else
                    tm.addColumn(rsd.getColumnName(i));
            }
            String Data[][] = new String[500][11];
            while (rs.next()){
                String id = rs.getString(1);
                String itemID = rs.getString(2);
                String Date = rs.getString(3);
                String type = rs.getString(4);
                String oldName = rs.getString(5);
                String oldPrice = rs.getString(6);
                String oldTax = rs.getString(7);
                String oldQuantity = rs.getString(8);
                String newName = rs.getString(9);
                String newPrice = rs.getString(10);
                String newTax = rs.getString(11);
                String newQuantity = rs.getString(12);
                tm.addRow(new Object[]{id,itemID,Date,type,oldName,oldPrice,oldTax,oldQuantity,newName,newPrice,newTax,newQuantity});
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }





    public static void main(String[] args) {



    }

}

