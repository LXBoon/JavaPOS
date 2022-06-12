package Frames;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serial;

import static Frames.DatabaseConn.*;

public class StaffManageFrame {

    static JFrame f = new JFrame("Adding new Staff");
    static JTextField id=new JTextField() ,FN =new JTextField(),LN=new JTextField(),phone=new JTextField(),email=new JTextField(),position=new JTextField(),salary=new JTextField();
    static long ID,Phone;
    static String fn,ln,Email,Position,slcFN,slcLN,slcEmail,slcPosition,slcIDNum,slcPhoneNum,slcSalary;
    static double Salary;
    //static JLabel l;
    static int y=50,x=250,slcId;
    static JButton add, cancel,update;


    static JTable jtST;
    static DefaultTableModel dtmST;
    static JInternalFrame i;

    static JButton btnNewStaff,deleteStaff,editStaff;

    static void staffTable(){
        dtmST = new DefaultTableModel();
        jtST = new JTable(dtmST){
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
        jtST.getSelectionModel().addListSelectionListener(event -> {
            try{
                slcId = Integer.parseInt(jtST.getValueAt(jtST.getSelectedRow(),0).toString());
                slcIDNum = jtST.getValueAt(jtST.getSelectedRow(),1).toString();
                slcFN = jtST.getValueAt(jtST.getSelectedRow(),2).toString();
                slcLN = jtST.getValueAt(jtST.getSelectedRow(),3).toString();
                slcPhoneNum = jtST.getValueAt(jtST.getSelectedRow(),4).toString();
                slcEmail = jtST.getValueAt(jtST.getSelectedRow(),5).toString();
                slcPosition =jtST.getValueAt(jtST.getSelectedRow(),6).toString();
                slcSalary = jtST.getValueAt(jtST.getSelectedRow(),7).toString();
                deleteStaff.setEnabled(true);
                editStaff.setEnabled(true);


            }catch (Exception exception){

            }
        });
        i = new JInternalFrame(("Staff"),false,false,false,false);
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,250,775,400);
        i.setVisible(true);
        i.setClosable(false);
        i.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                i.setLocation(220,250);
            }
        });
        i.setEnabled(false);

        showStaffTable(dtmST);

        jtST.setVisible(true);
        jtST.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jtST.setDragEnabled(false);

        //jtST.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(jtST);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        Design.f.add(i);
    }

    static void loadButtons(){
        btnNewStaff = new JButton("New Staff");
        btnNewStaff.setBounds(930,100,120,20);
        btnNewStaff.setEnabled(true);
        btnNewStaff.setFocusable(false);
        btnNewStaff.setBackground(new Color(108, 255, 141));
        btnNewStaff.addActionListener(e->{
            try{
                Design.f.setEnabled(false);
                newStaffFrame();
                jtST.clearSelection();
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,exception);
                throw new RuntimeException(exception);
            }
        });
        Design.f.add(btnNewStaff);


        deleteStaff = new JButton("Delete");
        deleteStaff.setBounds(800,100,120,20); // Change x and y locations
        deleteStaff.setFocusable(false);
        deleteStaff.setEnabled(false);
        deleteStaff.setVisible(true);
        deleteStaff.setBackground(Color.red);
        deleteStaff.setForeground(Color.white);
        deleteStaff.addActionListener(e->{
            //delete from sql
            DatabaseConn.deleteStaff(slcId);
            dtmST.setRowCount(0);
            dtmST.setColumnCount(0);
            DatabaseConn.showStaffTable(dtmST);
            JOptionPane.showMessageDialog(null,"Deleted "+slcFN+" "+slcLN+" from Staff");
            jtST.clearSelection();
        });
        Design.f.add(deleteStaff);


        editStaff = new JButton("Edit");
        editStaff.setBounds(800,130,120,20); // Change x and y locations
        editStaff.setFocusable(false);
        editStaff.setEnabled(false);
        editStaff.setVisible(true);
        editStaff.setBackground(Color.yellow);
        editStaff.addActionListener(e-> {
            UpdateStaffFrame();
        });
        Design.f.add(editStaff);


    }


    //create tf of new frames (new/update) staff
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
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }
            @Override
            public void windowClosing(WindowEvent e) {
                f.setEnabled(true);
                y=50;
            }
            @Override
            public void windowClosed(WindowEvent e) {
                f.setEnabled(true);
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
                f.setEnabled(true);
                y=50;
            }
        });
        DatabaseConn.foundUser = false;
        f.setSize(500,500);
        f.setLayout(null);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        f.setTitle("Adding new staff");
        f.setVisible(true);

        createTF(id,"ID");
        createTF(FN,"First Name");
        createTF(LN,"Last Name");
        createTF(phone,"Phone");
        createTF(email,"Email");
        createTF(position,"Position");
        createTF(salary,"Salary");
        add =new JButton("Add");
        add.addActionListener(e->{
            try{
                ID = Long.parseLong(id.getText());
                fn = FN.getText();
                ln = LN.getText();
                Phone = Long.parseLong(phone.getText());
                Email = email.getText();
                Position = position.getText();
                Salary = Double.parseDouble(salary.getText());
                DatabaseConn.addStaff(ID,fn,ln, Phone, Email, Position, Salary);
                Design.f.setEnabled(true);
                f.dispose();
                dtmST.setRowCount(0);
                dtmST.setColumnCount(0);
                DatabaseConn.showStaffTable(dtmST);
                JOptionPane.showMessageDialog(null,"Added "+fn+" "+ln+" to Staff");
                id.setText(null);FN.setText(null);LN.setText(null);
                phone.setText(null);email.setText(null);position.setText(null);
                salary.setText(null);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,exception);
                throw new RuntimeException(exception);
            }
        });
        add.setBounds(x,y,120,30);
        add.setBackground(new Color(91, 255, 12));
        add.setFocusable(false);
        add.setVisible(true);
        f.add(add);


        cancel =new JButton("Cancel");
        cancel.setBounds(x-150,y,120,30);
        cancel.setBackground(new Color(255, 85, 85));
        cancel.setFocusable(false);
        cancel.setVisible(true);
        cancel.addActionListener(e->{
            Design.f.setEnabled(true);
            id.setText(null);FN.setText(null);LN.setText(null);
            phone.setText(null);email.setText(null);position.setText(null);
            Design.f.setEnabled(true);
            salary.setText(null);
            f.dispose();
            Design.f.setEnabled(true);
        });
        f.add(cancel);
    }
    static void UpdateStaffFrame(){
        //if (f.isActive()) f.dispose();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                f.setEnabled(true);
                y=50;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                f.setEnabled(true);
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
                f.setEnabled(true);
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
        f.setTitle("Update information about staff");
        createTF(id,"ID",slcIDNum);
        createTF(FN,"First Name",slcFN);
        createTF(LN,"Last Name",slcLN);
        createTF(phone,"Phone",slcPhoneNum);
        createTF(email,"Email",slcEmail);
        createTF(position,"Position",slcPosition);
        createTF(salary,"Salary",slcSalary);
        update =new JButton("Update");
        update.addActionListener(e->{
            try{
                ID = Long.parseLong(id.getText());
                fn = FN.getText();
                ln = LN.getText();
                Phone = Long.parseLong(phone.getText());
                Email = email.getText();
                Position = position.getText();
                Salary = Double.parseDouble(salary.getText());
                DatabaseConn.updateStaff(slcId,ID,fn,ln, Phone, Email, Position, Salary);
                f.dispose();
                dtmST.setRowCount(0);
                dtmST.setColumnCount(0);
                DatabaseConn.showStaffTable(dtmST);
                JOptionPane.showMessageDialog(null,"Updated "+fn+" "+ln+" to Staff");
                id.setText(null);FN.setText(null);LN.setText(null);
                phone.setText(null);email.setText(null);position.setText(null);
                salary.setText(null);
            }catch (Exception exception){
            }
        });
        update.setBounds(x,y,120,30);
        update.setBackground(new Color(91, 255, 12));
        update.setFocusable(false);
        update.setVisible(true);
        f.add(update);


        cancel =new JButton("Cancel");
        cancel.setBounds(x-150,y,120,30);
        cancel.setBackground(new Color(255, 85, 85));
        cancel.setFocusable(false);
        cancel.setVisible(true);
        cancel.addActionListener(e->{
            id.setText(null);FN.setText(null);LN.setText(null);
            phone.setText(null);email.setText(null);position.setText(null);
            salary.setText(null);
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
                dtmST.setRowCount(0);
                dtmST.setColumnCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                DatabaseConn.searchStaff(dtmST,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtmST.setRowCount(0);
                dtmST.setColumnCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                DatabaseConn.searchStaff(dtmST,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtmST.setRowCount(0);
                dtmST.setColumnCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                DatabaseConn.searchStaff(dtmST,searchText.getText(),by);
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



}
