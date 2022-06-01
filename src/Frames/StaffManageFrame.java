package Frames;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Frames.DatabaseConn.*;

public class StaffManageFrame {

    static JFrame f;
    static JTextField id=new JTextField() ,FN =new JTextField(),LN=new JTextField(),phone=new JTextField(),email=new JTextField(),position=new JTextField(),salary=new JTextField();
    static long ID,Phone;
    static String fn,ln,Email,Position,slcFN,slcLN,slcEmail,slcPosition,slcIDNum,slcPhoneNum,slcSalary;
    static double Salary;
    //static JLabel l;
    static int y=50,x=250,slcId;

    static void staffTable(){
        Design.dtmST = new DefaultTableModel();
        Design.jtST = new JTable(Design.dtmST){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };
        Design.jtST.getSelectionModel().addListSelectionListener(event -> {
            try{
                slcId = Integer.parseInt(Design.jtST.getValueAt(Design.jtST.getSelectedRow(),0).toString());
                slcIDNum = Design.jtST.getValueAt(Design.jtST.getSelectedRow(),1).toString();
                slcFN = Design.jtST.getValueAt(Design.jtST.getSelectedRow(),2).toString();
                slcLN = Design.jtST.getValueAt(Design.jtST.getSelectedRow(),3).toString();
                slcPhoneNum = Design.jtST.getValueAt(Design.jtST.getSelectedRow(),4).toString();
                slcEmail = Design.jtST.getValueAt(Design.jtST.getSelectedRow(),5).toString();
                slcPosition =Design.jtST.getValueAt(Design.jtST.getSelectedRow(),6).toString();
                slcSalary = Design.jtST.getValueAt(Design.jtST.getSelectedRow(),7).toString();
                Design.deleteStaff.setEnabled(true);
                Design.editStaff.setEnabled(true);

            }catch (Exception exception){
                System.out.println(exception);
            }
        });
        Design.i = new JInternalFrame(("Staff"),false,false,false,false);
        Design.i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        Design.i.setBounds(220,250,775,400);
        Design.i.setVisible(true);
        Design.i.setClosable(false);
        Design.i.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Design.i.setLocation(220,250);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Design.i.setLocation(220,250);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Design.i.setLocation(220,250);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Design.i.setLocation(220,250);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Design.i.setLocation(220,250);
            }
        });
        Design.i.setEnabled(false);

        showStaffTable(Design.dtmST);

        Design.jtST.setVisible(true);
        Design.jtST.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Design.jtST.setDragEnabled(false);

        //Design.jtST.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(Design.jtST);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Design.i.getContentPane().add(sp);
        Design.f.add(Design.i);
    }

    static void loadButtons(){
        Design.btnNewStaff = new JButton("New Staff");
        Design.btnNewStaff.setBounds(930,100,120,20);
        Design.btnNewStaff.setEnabled(true);
        Design.btnNewStaff.setFocusable(false);
        Design.btnNewStaff.setBackground(new Color(108, 255, 141));
        Design.btnNewStaff.addActionListener(e->{
            try{
                Design.f.setEnabled(false);
                newStaffFrame();
                Design.jtST.clearSelection();
            }catch (Exception exception){
                System.out.println(exception);
            }

        });
        Design.f.add(Design.btnNewStaff);


        Design.deleteStaff = new JButton("Delete");
        Design.deleteStaff.setBounds(800,100,120,20); // Change x and y locations
        Design.deleteStaff.setFocusable(false);
        Design.deleteStaff.setEnabled(false);
        Design.deleteStaff.setVisible(true);
        Design.deleteStaff.setBackground(Color.red);
        Design.deleteStaff.setForeground(Color.white);
        Design.deleteStaff.addActionListener(e->{
            //delete from sql
            deleteStaff(slcId);
            Design.dtmST.setRowCount(0);
            showStaffTable(Design.dtmST);
            JOptionPane.showMessageDialog(null,"Updated "+fn+" "+ln+" to Staff");
            Design.deleteStaff.setEnabled(false);
            Design.editStaff.setEnabled(false);
            Design.jtST.clearSelection();
        });
        Design.f.add(Design.deleteStaff);


        Design.editStaff = new JButton("Edit");
        Design.editStaff.setBounds(800,130,120,20); // Change x and y locations
        Design.editStaff.setFocusable(false);
        Design.editStaff.setEnabled(false);
        Design.editStaff.setVisible(true);
        Design.editStaff.setBackground(Color.yellow);
        Design.editStaff.addActionListener(e->{
            //Update sql
            UpdateStaffFrame();
            Design.deleteStaff.setEnabled(false);
            Design.editStaff.setEnabled(false);
            Design.jtST.clearSelection();
        });
        Design.f.add(Design.editStaff);


    }

    static void createTF(JTextField tf,String text){
        tf.setBounds(x,y,100,20);
        tf.setVisible(true);
        f.add(tf);

        JLabel l = new JLabel();
        l.setText(text);
        l.setBounds(x-100,y,100,20);
        l.setVisible(true);
        f.add(l);

        y+=50;


    }
    static void createTF(JTextField tf,String text,String tfText){
        tf.setBounds(x,y,100,20);
        tf.setText(tfText);
        tf.setVisible(true);
        f.add(tf);

        JLabel l = new JLabel();
        l.setText(text);
        l.setBounds(x-100,y,100,20);
        l.setVisible(true);
        f.add(l);

        y+=50;


    }

    static void newStaffFrame(){
        f = new JFrame("Adding new Staff");
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Design.f.setEnabled(true);
                y=50;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Design.f.setEnabled(true);
                y=50;
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                Design.f.setEnabled(true);
                y=50;
            }
        });
        DatabaseConn.foundUser = false;
        f.setSize(500,500);
        f.setLayout(null);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        f.setVisible(true);

        createTF(id,"ID");
        createTF(FN,"First Name");
        createTF(LN,"Last Name");
        createTF(phone,"Phone");
        createTF(email,"Email");
        createTF(position,"Position");
        createTF(salary,"Salary");

        JButton add =new JButton("Add");

        add.addActionListener(e->{
            try{
                ID = Long.parseLong(id.getText());
                fn = FN.getText();
                ln = LN.getText();
                Phone = Long.parseLong(phone.getText());
                Email = email.getText();
                Position = position.getText();
                Salary = Double.parseDouble(salary.getText());
                addStaff(ID,fn,ln, Phone, Email, Position, Salary);

                f.dispose();
                Design.dtmST.setRowCount(0);
                showStaffTable(Design.dtmST);
                JOptionPane.showMessageDialog(null,"Added "+fn+" "+ln+" to Staff");
                id.setText(null);FN.setText(null);LN.setText(null);
                phone.setText(null);email.setText(null);position.setText(null);
                salary.setText(null);


            }catch (Exception exception){
                System.out.println(exception);
            }

        });
        add.setBounds(x,y,120,30);
        add.setBackground(new Color(91, 255, 12));
        add.setFocusable(false);
        add.setVisible(true);
        f.add(add);


        JButton cancel =new JButton("Cancel");
        cancel.setBounds(x-150,y,120,30);
        cancel.setBackground(new Color(255, 85, 85));
        cancel.setFocusable(false);
        cancel.setVisible(true);
        cancel.addActionListener(e->{
            f.dispose();
        });
        f.add(cancel);



    }
    static void UpdateStaffFrame(){
        f = new JFrame("Adding new Staff");
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Design.f.setEnabled(true);
                y=50;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Design.f.setEnabled(true);
                y=50;
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                Design.f.setEnabled(true);
                y=50;
            }
        });
        DatabaseConn.foundUser = false;
        f.setSize(500,500);
        f.setLayout(null);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        f.setVisible(true);

        createTF(id,"ID",slcIDNum);
        createTF(FN,"First Name",slcFN);
        createTF(LN,"Last Name",slcLN);
        createTF(phone,"Phone",slcPhoneNum);
        createTF(email,"Email",slcEmail);
        createTF(position,"Position",slcPosition);
        createTF(salary,"Salary",slcSalary);

        JButton update =new JButton("Update");
        update.addActionListener(e->{
            try{

                ID = Long.parseLong(id.getText());
                fn = FN.getText();
                ln = LN.getText();
                Phone = Long.parseLong(phone.getText());
                Email = email.getText();
                Position = position.getText();
                Salary = Double.parseDouble(salary.getText());

                updateStaff(slcId,ID,fn,ln, Phone, Email, Position, Salary);


                f.dispose();
                Design.dtmST.setRowCount(0);
                showStaffTable(Design.dtmST);
                JOptionPane.showMessageDialog(null,"Updated "+fn+" "+ln+" to Staff");
                id.setText(null);FN.setText(null);LN.setText(null);
                phone.setText(null);email.setText(null);position.setText(null);
                salary.setText(null);


            }catch (Exception exception){
                System.out.println(exception);
            }

        });
        update.setBounds(x,y,120,30);
        update.setBackground(new Color(91, 255, 12));
        update.setFocusable(false);
        update.setVisible(true);
        f.add(update);


        JButton cancel =new JButton("Cancel");
        cancel.setBounds(x-150,y,120,30);
        cancel.setBackground(new Color(255, 85, 85));
        cancel.setFocusable(false);
        cancel.setVisible(true);
        cancel.addActionListener(e->{
            f.dispose();
        });
        f.add(cancel);
    }

    static void search(){
        JLabel label = new JLabel("Search in table By:");
        label.setBounds(300,120,200,40);
        Design.f.add(label);
        JTextField search;
        search = new JTextField();
        String[] columns ={"ID","IDNum","FirstName","LastName","Phone","Email","Position","Salary"};
        JComboBox<String> cb=new JComboBox<>(columns);
        cb.setBounds(300, 150,90,20);
        cb.addActionListener(e -> search.setText(null));
        Design.f.add(cb);
        JLabel searchText = new JLabel();
        searchText.setBounds(300,220,500,40);
        searchText.setVisible(true);
        Design.f.add(searchText);
        search.setBounds(300,180,200,40);
        search.setVisible(true);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                Design.dtmST.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                searchStaff(Design.dtmST,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                Design.dtmST.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                searchStaff(Design.dtmST,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                Design.dtmST.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                searchStaff(Design.dtmST,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }
        });
        Design.f.add(search);
    }


    public static void loadStaffManageFrame(){


        loadButtons();
        search();
        staffTable();
    }

    public static void main(String[] args) {

    }

}
