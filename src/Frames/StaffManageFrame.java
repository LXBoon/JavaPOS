package Frames;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frames.DatabaseConn.displayItemList;

public class StaffManageFrame {

    JFrame f = new JFrame();
    public JTable jt;
    public DefaultTableModel dtm;
    public int rowID;
    public JTextField tfID ,tfName,tfPrice,tfQ;
    public JButton btnEdit,btnSave,btnNew,btnAdd,btnDelete,refresh;

    void staffTable(){
        dtm = new DefaultTableModel();
        jt = new JTable(dtm){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        jt.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                String rowIndex = jt.getValueAt(jt.getSelectedRow(), 0).toString();
                String name =jt.getValueAt(jt.getSelectedRow(), 1).toString();
                String price =jt.getValueAt(jt.getSelectedRow(), 2).toString();
                String quantity = jt.getValueAt(jt.getSelectedRow(), 3).toString();
            }
        });
        JInternalFrame i = new JInternalFrame("Staff");
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,50,300,500);
        i.setVisible(true);
        //displayItemList(dtm);
        jt.setVisible(true);
        jt.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(jt);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        f.add(i);
    }

    void tfDisable(){
        tfID.setEnabled(false);tfName.setEnabled(false);tfPrice.setEnabled(false);
        tfQ.setEnabled(false);
        tfID.setBackground(new Color(154, 102, 102));
        tfName.setBackground(new Color(154, 102, 102));
        tfPrice.setBackground(new Color(154, 102, 102));
        tfQ.setBackground(new Color(154, 102, 102));
        tfID.setForeground(Color.BLACK);tfName.setForeground(Color.BLACK);
        tfPrice.setForeground(Color.BLACK);tfQ.setForeground(Color.BLACK);
        tfID.setText(null);tfName.setText(null);tfPrice.setText(null);tfQ.setText(null);
    }
    void tfEnable(){
        tfID.setEnabled(true);tfName.setEnabled(true);tfPrice.setEnabled(true);
        tfQ.setEnabled(true);
        tfID.setBackground(Color.white); tfName.setBackground(Color.white);
        tfPrice.setBackground(Color.white); tfQ.setBackground(Color.white);
    }
    void loadFields(){
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
        f.add(tfID);
        f.add(lid);

        JLabel lName = new JLabel("Name:");
        lName.setBounds(850,200,125,30);
        lName.setBorder(BorderFactory.createEtchedBorder());
        lName.setOpaque(false);
        lName.setVisible(true);
        tfName = new JTextField();
        tfName.setBounds(900,200,125,30);
        tfName.setBorder(BorderFactory.createEtchedBorder());
        tfName.setVisible(true);
        f.add(tfName);
        f.add(lName);


        JLabel lPrice = new JLabel("Price:");
        lPrice.setBounds(850,250,125,30);
        lPrice.setBorder(BorderFactory.createEtchedBorder());
        lPrice.setOpaque(false);
        lPrice.setVisible(true);
        tfPrice = new JTextField();
        tfPrice.setBounds(900,250,125,30);
        tfPrice.setBorder(BorderFactory.createEtchedBorder());
        tfPrice.setVisible(true);
        f.add(tfPrice);
        f.add(lPrice);

        JLabel lQ = new JLabel("Quantity:");
        lQ.setBounds(850,300,125,30);
        lQ.setBorder(BorderFactory.createEtchedBorder());
        lQ.setOpaque(false);
        lQ.setVisible(true);
        tfQ = new JTextField();
        tfQ.setBounds(900,300,125,30);
        tfQ.setBorder(BorderFactory.createEtchedBorder());

        tfQ.setVisible(true);
        f.add(tfQ);
        f.add(lQ);
    }

    void btnAddNew(){
        //New item add btn
        btnNew = new JButton("New Item");
        btnNew.setBounds(950,115,65,25);
        btnNew.setVisible(true);
        btnNew.setForeground(Color.white);
        btnNew.setBackground(new Color(45, 168, 34));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnEdit");
                JOptionPane.showMessageDialog(null,"Now You can Add a new Item Click Add when you are done","Adding new Item",JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(null, "infoMessage", "InfoBox: " + "titleBar", JOptionPane.INFORMATION_MESSAGE);
                tfDisable();
                tfEnable();
                btnAdd.setEnabled(true);
                btnSave.setEnabled(false);btnEdit.setEnabled(false);
            }
        });
        f.add(btnNew);
        btnNew.setFocusable(false);
        btnNew.setBorder(BorderFactory.createEtchedBorder());
        SwingUtilities.updateComponentTreeUI(f);
        btnNew.setEnabled(true);

        /* This was a test label
        JLabel bro = new JLabel();
        bro.setBounds(825,150,5,100);
        bro.setBackground(Color.BLACK);
        bro.setVisible(true);
        bro.setText("asdasdjashdgasdguhg");
        f.add(bro);
         */
    }

    void btnAdd(){
        btnAdd = new JButton("Add");
        btnAdd.setBounds(950,350,65,25);
        btnAdd.setVisible(true);
        btnAdd.setForeground(Color.white);
        btnAdd.setBackground(new Color(45, 168, 34));
        int rowCount = dtm.getRowCount();
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    long id = Long.parseLong(tfID.getText().trim());
                    String name = tfName.getText().trim();
                    double price = Double.parseDouble(tfPrice.getText().trim());
                    int quantity = Integer.parseInt(tfQ.getText().trim());
                    DatabaseConn.addToItemList(id,name,price,quantity);
                    dtm.setRowCount(0);
                    DatabaseConn.displayItemList(dtm);
                    tfDisable();
                    btnAdd.setEnabled(false);
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(null,exception.toString());
                    System.out.println(exception);
                }
            }
        });
        f.add(btnAdd);
        btnAdd.setFocusable(false);
        btnAdd.setBorder(BorderFactory.createEtchedBorder());
        btnAdd.setEnabled(false);
    }

    void btnDelete(){
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(875,350,65,25);
        btnDelete.setVisible(true);
        btnDelete.setForeground(Color.white);
        btnDelete.setBackground(new Color(199, 30, 4));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //JOptionPane.showMessageDialog(null,"Delete seceded, Please refresh the data manually");
                    //DatabaseConn.deleteFromItemList(rowID);
                    //dtm.setRowCount(0);
                    //DatabaseConn.displayItemList(dtm);
                }
                catch (Exception exception){

                }
                //SwingUtilities.updateComponentTreeUI(f);
                //refresh.doClick();

            }
        });
        f.add(btnDelete);
        btnDelete.setFocusable(false);
        btnDelete.setBorder(BorderFactory.createEtchedBorder());
        btnDelete.setEnabled(false);
    }

    void btnRefresh (){
        refresh = new JButton("Refresh");
        refresh.setBounds(800,350,65,25);
        refresh.setVisible(true);
        refresh.setForeground(Color.white);
        refresh.setBackground(new Color(1, 22, 62));
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    dtm.setRowCount(0);
                    DatabaseConn.displayItemList(dtm);
                    JOptionPane.showMessageDialog(null,"Refreshed Table");
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null,exception.toString());
                }

            }
        });
        f.add(refresh);
        refresh.setFocusable(false);
        refresh.setBorder(BorderFactory.createEtchedBorder());
        btnEdit.setEnabled(false);btnSave.setEnabled(false);
    }

    void btnEdit(){
        btnEdit = new JButton("Edit");
        btnEdit.setBounds(775,150,65,25);
        btnEdit.setVisible(true);
        btnEdit.setForeground(Color.white);
        btnEdit.setBackground(new Color(255, 158, 0));
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btnEdit) {
                    System.out.println("btnEdit");
                    JOptionPane.showMessageDialog(null, "Now You can Edit Click Save when you are done");
                    tfEnable();
                    tfID.setEnabled(false);tfID.setBackground(new Color(154, 102, 102));
                    btnEdit.setEnabled(false);
                    btnSave.setEnabled(true);
                }
            }
        });
        f.add(btnEdit);
        btnEdit.setFocusable(false);
        btnEdit.setBorder(BorderFactory.createEtchedBorder());
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    void btnSave (){
        btnSave = new JButton("Save");
        btnSave.setBounds(775,185,65,25);
        btnSave.setVisible(true);
        btnSave.setForeground(Color.BLACK);
        btnSave.setBackground(new Color(12, 255, 0));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnSave");
                int id = Integer.parseInt(tfID.getText().trim());
                String name = tfName.getText().trim();
                double price = Double.parseDouble(tfPrice.getText().trim());
                int quantity = Integer.parseInt(tfQ.getText().trim());

                try {
                    DatabaseConn.updateItemFromList(id,name,price,quantity);
                    dtm.setRowCount(0);
                    DatabaseConn.displayItemList(dtm);
                }catch (Exception exception){
                    //JOptionPane.showMessageDialog(null,exception.toString());
                }


                JOptionPane.showMessageDialog(null,"Updated successfully");
                tfDisable();
                btnSave.setEnabled(false);
                refresh.doClick();
            }
        });
        f.add(btnSave);
        btnSave.setFocusable(false);
        btnSave.setBorder(BorderFactory.createEtchedBorder());
        btnSave.setEnabled(false);
    }

    void search(){
        //JTextField textField = new JTextField;
        //textField.addDocumentListener (new DocumentListener() {
        //  public void changedUpdate(DocumentEvent e) {
        //  }
        //  public void removeUpdate(DocumentEvent e) {
        //  }
        //  public void insertUpdate(DocumentEvent e) {
        //  }
        //}
        //);

        JLabel label = new JLabel("Search in table By:");
        label.setBounds(520,120,200,40);
        f.add(label);
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
        f.add(cb);
        JLabel searchText = new JLabel();
        searchText.setBounds(525,220,200,40);
        searchText.setVisible(true);
        f.add(searchText);


        search.setBounds(525,180,200,40);
        search.setVisible(true);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by =cb.getItemAt(cb.getSelectedIndex()).toString();
                DatabaseConn.search(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by =cb.getItemAt(cb.getSelectedIndex()).toString();
                DatabaseConn.search(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by =cb.getItemAt(cb.getSelectedIndex()).toString();
                DatabaseConn.search(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }
        });

        f.add(search);

    }

    StaffManageFrame(){

        JLabel test = new JLabel("This is Staff Management frame page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        f.add(test);

        staffTable();
        btnAddNew();
        loadFields();
        tfDisable();
        btnAdd();
        btnDelete();
        btnEdit();
        btnSave();
        btnRefresh();
        search();

        SwingUtilities.updateComponentTreeUI(f);
        Design.yPos=100;
        //Design.LoadDesinSP(f);

    }




    public static void main(String[] args) {
        new StaffManageFrame();
    }
}
