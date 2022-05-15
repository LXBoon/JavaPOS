package Frames;

import Frames.LandingPage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frames.DatabaseConn.columns;
import static Frames.DatabaseConn.displayItemList;

public class InventoryFrame  {


    /*
    public static JFrame f = new JFrame();

    public static JTable jt;
    public static DefaultTableModel dtm;
    public static long rowID;
    public static JTextField tfID ,tfName,tfPrice,tfQ;
    public static JButton btnNew, btnAdd, btnDelete,refresh,btnEdit,btnSave;

     */

    static void loadFields(){
        //Add new items
        JLabel lid = new JLabel("ID:");
        lid.setBounds(850,150,125,30);
        lid.setBorder(BorderFactory.createEtchedBorder());
        lid.setOpaque(false);
        lid.setVisible(true);
        Design.tfID = new JTextField();
        Design.tfID.setBounds(900,150,125,30);
        Design.tfID.setBorder(BorderFactory.createEtchedBorder());
        Design.tfID.setVisible(true);
        Design.f.add(Design.tfID);
        Design.f.add(lid);

        JLabel lName = new JLabel("Name:");
        lName.setBounds(850,200,125,30);
        lName.setBorder(BorderFactory.createEtchedBorder());
        lName.setOpaque(false);
        lName.setVisible(true);
        Design.tfName = new JTextField();
        Design.tfName.setBounds(900,200,125,30);
        Design.tfName.setBorder(BorderFactory.createEtchedBorder());
        Design.tfName.setVisible(true);
        Design.f.add(Design.tfName);
        Design.f.add(lName);


        JLabel lPrice = new JLabel("Price:");
        lPrice.setBounds(850,250,125,30);
        lPrice.setBorder(BorderFactory.createEtchedBorder());
        lPrice.setOpaque(false);
        lPrice.setVisible(true);
        Design.tfPrice = new JTextField();
        Design.tfPrice.setBounds(900,250,125,30);
        Design.tfPrice.setBorder(BorderFactory.createEtchedBorder());
        Design.tfPrice.setVisible(true);
        Design.f.add(Design.tfPrice);
        Design.f.add(lPrice);

        JLabel lQ = new JLabel("Quantity:");
        lQ.setBounds(850,300,125,30);
        lQ.setBorder(BorderFactory.createEtchedBorder());
        lQ.setOpaque(false);
        lQ.setVisible(true);
        Design.tfQ = new JTextField();
        Design.tfQ.setBounds(900,300,125,30);
        Design.tfQ.setBorder(BorderFactory.createEtchedBorder());

        Design.tfQ.setVisible(true);
        Design.f.add(Design.tfQ);
        Design.f.add(lQ);
        Design.setPageLabel(Design.pageLabel,"Inventory Management");


    }

    static void tfDisable(){
        Design.tfID.setEnabled(false);Design.tfName.setEnabled(false);Design.tfPrice.setEnabled(false);
        Design.tfQ.setEnabled(false);
        Design.tfID.setBackground(new Color(154, 102, 102));
        Design.tfName.setBackground(new Color(154, 102, 102));
        Design.tfPrice.setBackground(new Color(154, 102, 102));
        Design.tfQ.setBackground(new Color(154, 102, 102));
        Design.tfID.setForeground(Color.BLACK);Design.tfName.setForeground(Color.BLACK);
        Design.tfPrice.setForeground(Color.BLACK);Design.tfQ.setForeground(Color.BLACK);
        Design.tfID.setText(null);Design.tfName.setText(null);Design.tfPrice.setText(null);Design.tfQ.setText(null);
    }
    static void tfEnable(){
        Design.tfID.setEnabled(true);Design.tfName.setEnabled(true);Design.tfPrice.setEnabled(true);
        Design.tfQ.setEnabled(true);
        Design.tfID.setBackground(Color.white); Design.tfName.setBackground(Color.white);
        Design.tfPrice.setBackground(Color.white); Design.tfQ.setBackground(Color.white);
    }



    public static void btnNewCreate(JButton x){
        x = new JButton("New Item");
        x.setBounds(950,115,65,25);
        x.setVisible(true);
        x.setForeground(Color.white);
        x.setBackground(new Color(45, 168, 34));

        Design.f.add(x);
        x.setFocusable(false);
        x.setBorder(BorderFactory.createEtchedBorder());
        SwingUtilities.updateComponentTreeUI(Design.f);
        x.setEnabled(true);
        x.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnAddNew");
                JOptionPane.showMessageDialog(null,"Now You can Add a new Item Click Add when you are done","Adding new Item",JOptionPane.INFORMATION_MESSAGE);
                tfDisable();
                tfEnable();
                Design.btnAdd.setEnabled(true);
                Design.btnSave.setEnabled(false);
                Design.btnEdit.setEnabled(false);
            }
        });

    }

    static void btnAdd(){
        Design.btnAdd = new JButton("Add");
        Design.btnAdd.setBounds(950,350,65,25);
        Design.btnAdd.setVisible(true);
        Design.btnAdd.setForeground(Color.white);
        Design.btnAdd.setBackground(new Color(45, 168, 34));

        Design.btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    long id = Long.parseLong(Design.tfID.getText().trim());
                    String name = Design.tfName.getText().trim();
                    double price = Double.parseDouble(Design.tfPrice.getText().trim());
                    int quantity = Integer.parseInt(Design.tfQ.getText().trim());
                    DatabaseConn.addToItemList(id,name,price,quantity);
                    tfDisable();
                    Design.btnAdd.setEnabled(false);
                    Design.refresh.doClick();
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(null,exception.toString());
                    System.out.println("Add error");
                }
            }
        });
        Design.f.add(Design.btnAdd);
        Design.btnAdd.setFocusable(false);
        Design.btnAdd.setBorder(BorderFactory.createEtchedBorder());
        Design.btnAdd.setEnabled(false);
    }

    static void btnDelete(){
        Design.btnDelete = new JButton("Delete");
        Design.btnDelete.setBounds(875,350,65,25);
        Design.btnDelete.setVisible(true);
        Design.btnDelete.setForeground(Color.white);
        Design.btnDelete.setBackground(new Color(199, 30, 4));
        Design.btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //JOptionPane.showMessageDialog(null,"Delete seceded, Please refresh the data manually");
                    DatabaseConn.deleteFromItemList(Design.rowID);
                    Design.refresh.doClick();
                }
                catch (Exception exception){
                    System.out.println(exception);
                }
            }
        });
        Design.f.add(Design.btnDelete);
        Design.btnDelete.setFocusable(false);
        Design.btnDelete.setBorder(BorderFactory.createEtchedBorder());
        Design.btnDelete.setEnabled(false);
    }

    static void btnSave (){
        Design.btnSave = new JButton("Save");
        Design.btnSave.setBounds(775,185,65,25);
        Design.btnSave.setVisible(true);
        Design.btnSave.setForeground(Color.BLACK);
        Design.btnSave.setBackground(new Color(12, 255, 0));
        Design.btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnSave");
                int id = Integer.parseInt(Design.tfID.getText().trim());
                String name = Design.tfName.getText().trim();
                double price = Double.parseDouble(Design.tfPrice.getText().trim());
                int quantity = Integer.parseInt(Design.tfQ.getText().trim());

                try {
                    DatabaseConn.updateItemFromList(id,name,price,quantity);
                }catch (Exception exception){
                    //JOptionPane.showMessageDialog(null,exception.toString());
                    System.out.println("Save error");
                }


                JOptionPane.showMessageDialog(null,"Updated successfully");
                tfDisable();
                Design.btnSave.setEnabled(false);
                Design.refresh.doClick();
                //Design.refresh.doClick(2);
            }
        });
        Design.f.add(Design.btnSave);
        Design.btnSave.setFocusable(false);
        Design.btnSave.setBorder(BorderFactory.createEtchedBorder());
        Design.btnSave.setEnabled(false);
    }

    static void search(){
        JLabel label = new JLabel("Search in table By:");
        label.setBounds(520,120,200,40);
        Design.f.add(label);
        JTextField search;
        search = new JTextField();
        String columns[]={"ID","Name","Price","Quantity"};
        JComboBox cb=new JComboBox(columns);
        cb.setBounds(525, 150,90,20);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search.setText(null);
            }
        });
        Design.f.add(cb);
        JLabel searchText = new JLabel();
        searchText.setBounds(525,220,200,40);
        searchText.setVisible(true);
        Design.f.add(searchText);


        search.setBounds(525,180,200,40);
        search.setVisible(true);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                Design.dtm.setRowCount(0);
                String by =cb.getItemAt(cb.getSelectedIndex()).toString();
                DatabaseConn.search(Design.dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                Design.dtm.setRowCount(0);
                String by =cb.getItemAt(cb.getSelectedIndex()).toString();
                DatabaseConn.search(Design.dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                Design.dtm.setRowCount(0);
                String by =cb.getItemAt(cb.getSelectedIndex()).toString();
                DatabaseConn.search(Design.dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }
        });
        Design.f.add(search);
    }
    static void Table(){
        Design.dtm = new DefaultTableModel();
        Design.jt = new JTable(Design.dtm){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        Design.jt.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                try{
                    String rowIndex = Design.jt.getValueAt(Design.jt.getSelectedRow(), 0).toString();
                    String name =Design.jt.getValueAt(Design.jt.getSelectedRow(), 1).toString();
                    String price =Design.jt.getValueAt(Design.jt.getSelectedRow(), 2).toString();
                    String quantity = Design.jt.getValueAt(Design.jt.getSelectedRow(), 3).toString();
                    Design.rowID = Long.parseLong(rowIndex);
                    tfDisable();
                    Design.tfID.setText(rowIndex);Design.tfName.setText(name);Design.tfPrice.setText(price);Design.tfQ.setText(quantity);
                    Design.btnEdit.setEnabled(true);Design.btnDelete.setEnabled(true);
                    Design.btnSave.setEnabled(false);
                }catch (Exception exception){
                    System.out.println("val");
                }


            }
        });
        Design.i = new JInternalFrame("List");
        Design.i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        Design.i.setBounds(220,70,300,500);
        Design.i.setVisible(true);
        Design.i.setClosable(false);
        displayItemList(Design.dtm);
        Design.jt.setVisible(true);
        Design.jt.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(Design.jt);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Design.i.getContentPane().add(sp);
        Design.f.add(Design.i);

    }

    static void btnRefresh (){
        Design.refresh = new JButton("Refresh");
        Design.refresh.setBounds(800,350,65,25);
        Design.refresh.setVisible(true);
        Design.refresh.setForeground(Color.white);
        Design.refresh.setBackground(new Color(1, 22, 62));
        Design.refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean de = false;
                try{
                    tfDisable();
                    Design.dtm.setRowCount(0);
                    de = true;
                    //DatabaseConn.displayItemList(Design.dtm);
                    //JOptionPane.showMessageDialog(null,"Refreshed Table");
                }catch (Exception exception){
                    de = false;
                    Design.refresh.doClick();
                }
                System.out.println(de);
                if (de) {
                    DatabaseConn.displayItemList(Design.dtm);
                }

            }
        });
        Design.f.add(Design.refresh);
        Design.refresh.setFocusable(false);
        Design.refresh.setBorder(BorderFactory.createEtchedBorder());
        Design.btnEdit.setEnabled(false);Design.btnSave.setEnabled(false);
        SwingUtilities.updateComponentTreeUI(Design.f);
    }
    static void btnEdit(){
        Design.btnEdit = new JButton("Edit");
        Design.btnEdit.setBounds(775,150,65,25);
        Design.btnEdit.setVisible(true);
        Design.btnEdit.setForeground(Color.white);
        Design.btnEdit.setBackground(new Color(255, 158, 0));
        Design.btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==Design.btnEdit) {
                    System.out.println("btnEdit");
                    JOptionPane.showMessageDialog(null, "Now You can Edit Click Save when you are done");
                    tfEnable();
                    Design.tfID.setEnabled(false);Design.tfID.setBackground(new Color(154, 102, 102));
                    Design.btnEdit.setEnabled(false);
                    Design.btnSave.setEnabled(true);
                    Design.btnDelete.setEnabled(false);
                }
            }
        });
        Design.f.add(Design.btnEdit);
        Design.btnEdit.setFocusable(false);
        Design.btnEdit.setBorder(BorderFactory.createEtchedBorder());
    }

    public static void inventoryDesign(){

        btnNewCreate(Design.btnNew);
        loadFields();
        tfDisable();
        btnAdd();
        btnDelete();
        btnEdit();
        btnSave();
        btnRefresh();
        search();
        Table();
    }



    public static void main(String[] args) {
        Design.loadInventoryPage();
    }



}