package Frames;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;

import static Frames.DatabaseConn.displayItemList;




public class InventoryFrame  {

    static JTable jt;
    static DefaultTableModel dtm;
    static long rowID;
    static JTextField tfID ,tfName,tfPrice,tfTax,tfQ;
    static  JButton btnEdit,refresh,btnDelete,btnAdd,btnNew,btnSave;
    static JInternalFrame i ;

    static int insertRow;
    static void loadFields(){
        //Add new items
        JLabel lid = new JLabel("ID:");
        lid.setBounds(850,150,125,30);
        lid.setBorder(BorderFactory.createEtchedBorder());
        lid.setOpaque(false);
        lid.setVisible(true);
        tfID = new JTextField();
        tfID.setBounds(900,150,125,30);
        tfID.setBorder(BorderFactory.createEtchedBorder());
        tfID.setVisible(true);
        Design.f.add(tfID);
        Design.f.add(lid);

        JLabel lName = new JLabel("Name:");
        lName.setBounds(850,200,125,30);
        lName.setBorder(BorderFactory.createEtchedBorder());
        lName.setOpaque(false);
        lName.setVisible(true);
        tfName = new JTextField();
        tfName.setBounds(900,200,125,30);
        tfName.setBorder(BorderFactory.createEtchedBorder());
        tfName.setVisible(true);
        Design.f.add(tfName);
        Design.f.add(lName);


        JLabel lPrice = new JLabel("Price:");
        lPrice.setBounds(850,250,125,30);
        lPrice.setBorder(BorderFactory.createEtchedBorder());
        lPrice.setOpaque(false);
        lPrice.setVisible(true);
        tfPrice = new JTextField();
        tfPrice.setBounds(900,250,125,30);
        tfPrice.setBorder(BorderFactory.createEtchedBorder());
        tfPrice.setVisible(true);
        Design.f.add(tfPrice);
        Design.f.add(lPrice);


        JLabel lTax = new JLabel("Tax:");
        lTax.setBounds(850,300,125,30);
        lTax.setBorder(BorderFactory.createEtchedBorder());
        lTax.setOpaque(false);
        lTax.setVisible(true);
        tfTax = new JTextField();
        tfTax.setBounds(900,300,125,30);
        tfTax.setBorder(BorderFactory.createEtchedBorder());
        tfTax.setVisible(true);
        Design.f.add(tfTax);
        Design.f.add(lTax);



        JLabel lQ = new JLabel("Quantity:");
        lQ.setBounds(850,350,125,30);
        lQ.setBorder(BorderFactory.createEtchedBorder());
        lQ.setOpaque(false);
        lQ.setVisible(true);
        tfQ = new JTextField();
        tfQ.setBounds(900,350,125,30);
        tfQ.setBorder(BorderFactory.createEtchedBorder());

        tfQ.setVisible(true);
        Design.f.add(tfQ);
        Design.f.add(lQ);



    }

    static void tfDisable(){
        tfID.setEnabled(false);tfName.setEnabled(false);tfPrice.setEnabled(false);
        tfQ.setEnabled(false);tfTax.setEnabled(false);
        tfID.setBackground(new Color(154, 102, 102));
        tfName.setBackground(new Color(154, 102, 102));
        tfPrice.setBackground(new Color(154, 102, 102));
        tfQ.setBackground(new Color(154, 102, 102));
        tfTax.setBackground(new Color(154, 102, 102));
        tfID.setForeground(Color.BLACK);tfName.setForeground(Color.BLACK);
        tfPrice.setForeground(Color.BLACK);tfQ.setForeground(Color.BLACK);
        tfID.setText(null);tfName.setText(null);tfPrice.setText(null);tfQ.setText(null);tfTax.setText(null);
    }
    static void tfEnable(){
        tfID.setEnabled(true);tfName.setEnabled(true);tfPrice.setEnabled(true);
        tfQ.setEnabled(true);tfTax.setEnabled(true);
        tfID.setBackground(Color.white); tfName.setBackground(Color.white);
        tfPrice.setBackground(Color.white); tfQ.setBackground(Color.white);
        tfTax.setBackground(Color.white);
    }



    public static void btnNewCreate(){
        btnNew = new JButton("New Item");
        btnNew.setBounds(950,115,65,25);
        btnNew.setVisible(true);
        btnNew.setForeground(Color.white);
        btnNew.setBackground(new Color(45, 168, 34));
        Design.f.add(btnNew);
        btnNew.setFocusable(false);
        btnNew.setBorder(BorderFactory.createEtchedBorder());
        SwingUtilities.updateComponentTreeUI(Design.f);
        btnNew.setEnabled(true);
        btnNew.addActionListener(e -> {
            System.out.println("btnAddNew");
            JOptionPane.showMessageDialog(null,"Now You can Add a new Item Click Add when you are done","Adding new Item",JOptionPane.INFORMATION_MESSAGE);
            tfDisable();
            tfEnable();
            btnAdd.setEnabled(true);
            btnSave.setEnabled(false);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        });

    }

    static void btnAdd(){
        btnAdd = new JButton("Add");
        btnAdd.setBounds(950,400,65,25);
        btnAdd.setVisible(true);
        btnAdd.setForeground(Color.white);
        btnAdd.setBackground(new Color(45, 168, 34));

        btnAdd.addActionListener(e -> {
            try{
                long id = Long.parseLong(tfID.getText().trim());
                String name = tfName.getText().trim();
                double price = Double.parseDouble(tfPrice.getText().trim());
                int quantity = Integer.parseInt(tfQ.getText().trim());
                int tax = Integer.parseInt(tfTax.getText());
                DatabaseConn.addToItemList(id,name,price,quantity,tax);
                tfDisable();
                btnAdd.setEnabled(false);
                refresh.doClick();
            }
            catch (Exception exception){
                //JOptionPane.showMessageDialog(null,exception.toString());
                System.out.println("Add error");
                throw new RuntimeException(exception);
            }
        });
        Design.f.add(btnAdd);
        btnAdd.setFocusable(false);
        btnAdd.setBorder(BorderFactory.createEtchedBorder());
        btnAdd.setEnabled(false);
    }

    static void btnDelete(){
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(875,400,65,25);
        btnDelete.setVisible(true);
        btnDelete.setForeground(Color.white);
        btnDelete.setBackground(new Color(199, 30, 4));
        btnDelete.addActionListener(e -> {
            try{
                //JOptionPane.showMessageDialog(null,"Delete seceded, Please refresh the data manually");
                DatabaseConn.deleteFromItemList(rowID);
                refresh.doClick();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(null,e);
            }
        });
        Design.f.add(btnDelete);
        btnDelete.setFocusable(false);
        btnDelete.setBorder(BorderFactory.createEtchedBorder());
        btnDelete.setEnabled(false);
    }

    static void btnSave (){
        btnSave = new JButton("Save");
        btnSave.setBounds(775,185,65,25);
        btnSave.setVisible(true);
        btnSave.setForeground(Color.BLACK);
        btnSave.setBackground(new Color(12, 255, 0));
        btnSave.addActionListener(e -> {
            System.out.println("btnSave");
            int id = Integer.parseInt(tfID.getText().trim());
            String name = tfName.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            int quantity = Integer.parseInt(tfQ.getText().trim());
            int tax = Integer.parseInt(tfTax.getText().trim());


            DatabaseConn.updateItemFromList(id,name,price,tax,quantity);
            tfDisable();
            btnSave.setEnabled(false);
            insertRow=jt.getSelectedRow();
            refresh.doClick();

        });
        Design.f.add(btnSave);
        btnSave.setFocusable(false);
        btnSave.setBorder(BorderFactory.createEtchedBorder());
        btnSave.setEnabled(false);
    }

    static void search(){
        JLabel label = new JLabel("Search in table By:");
        label.setBounds(250,50,200,40);
        Design.f.add(label);
        JTextField search;
        search = new JTextField();
        String[] columns ={"ID","Name","Price","TaxPercentage","Quantity"};
        JComboBox<String> cb=new JComboBox<>(columns);
        cb.setBounds(250, 80,90,20);
        cb.addActionListener(e -> search.setText(null));
        Design.f.add(cb);
        JLabel searchText = new JLabel();
        searchText.setBounds(250,150,200,40);
        searchText.setVisible(true);
        Design.f.add(searchText);


        search.setBounds(250,110,200,40);
        search.setVisible(true);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                DatabaseConn.search(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                DatabaseConn.search(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                DatabaseConn.search(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }
        });
        Design.f.add(search);


    }
    static void Table(){
        dtm = new DefaultTableModel();
        jt = new JTable(dtm){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jt.getSelectionModel().addListSelectionListener(event -> {
            // do some actions here, for example
            // print first column value from selected row
            try{
                String rowIndex = jt.getValueAt(jt.getSelectedRow(), 0).toString();
                String name =jt.getValueAt(jt.getSelectedRow(), 1).toString();
                String price =jt.getValueAt(jt.getSelectedRow(), 2).toString();
                String tax = jt.getValueAt(jt.getSelectedRow(), 3).toString();
                String quantity = jt.getValueAt(jt.getSelectedRow(), 4).toString();
                rowID = Long.parseLong(rowIndex);
                insertRow=jt.getSelectedRow();
                tfDisable();
                tfID.setText(rowIndex);tfName.setText(name);tfPrice.setText(price);tfQ.setText(quantity);tfTax.setText(tax);
                btnEdit.setEnabled(true);btnDelete.setEnabled(true);
                btnSave.setEnabled(false);
            }catch (Exception exception){
                throw new RuntimeException(exception);
            }
        });
        i = new JInternalFrame(("List"),false,false,false,false);
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,200,550,400);
        i.setVisible(true);
        i.setClosable(false);
        displayItemList(dtm);
        jt.setVisible(true);
        jt.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(jt);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        Design.f.add(i);

    }

    static void btnRefresh (){
        refresh = new JButton("Refresh");
        refresh.setBounds(800,400,65,25);
        refresh.setVisible(true);
        refresh.setForeground(Color.white);
        refresh.setBackground(new Color(1, 22, 62));
        refresh.addActionListener(e -> {
            boolean de;
            try{
                tfDisable();
                dtm.setRowCount(0);
                de = true;
            }catch (Exception exception){
                de = false;
                refresh.doClick();
            }
            if (de) {
                DatabaseConn.displayItemList(dtm);
                //JOptionPane.showMessageDialog(null,insertRow);
                jt.setRowSelectionInterval(insertRow,insertRow);
            }

        });
        Design.f.add(refresh);
        refresh.setFocusable(false);
        refresh.setBorder(BorderFactory.createEtchedBorder());
        btnEdit.setEnabled(false);btnSave.setEnabled(false);
        SwingUtilities.updateComponentTreeUI(Design.f);
    }
    static void btnEdit(){
        btnEdit = new JButton("Edit");
        btnEdit.setBounds(775,150,65,25);
        btnEdit.setVisible(true);
        btnEdit.setForeground(Color.white);
        btnEdit.setBackground(new Color(255, 158, 0));
        btnEdit.addActionListener(e -> {
            if (e.getSource()==btnEdit) {
                System.out.println("btnEdit");
                JOptionPane.showMessageDialog(null, "Now You can Edit Click Save when you are done");
                tfEnable();
                tfID.setEnabled(false);tfID.setBackground(new Color(154, 102, 102));
                btnEdit.setEnabled(false);
                btnSave.setEnabled(true);
                btnDelete.setEnabled(false);
            }
        });
        Design.f.add(btnEdit);
        btnEdit.setFocusable(false);
        btnEdit.setBorder(BorderFactory.createEtchedBorder());
    }


    public static void inventoryDesign(){

        btnNewCreate();
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

    }



}