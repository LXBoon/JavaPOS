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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static Frames.DatabaseConn.displayItemList;
import static javax.swing.JOptionPane.showMessageDialog;

public class Design {

    public static int firstTime = 0;
    public static int yPos=100;
    public static boolean sp,mi,ms,r,e;
    public static Color grayish = new Color(100, 114, 140);
    public static Color fff = Color.white;
    public static JLabel lDate = new JLabel();
    public static JLabel lTime = new JLabel();
    public static JPanel leftPanel,rightPanel;

    public static JButton btnSellPage, btnInventory, btnExit;

    public static JFrame f = new JFrame();

    public static JTable jt;
    public static DefaultTableModel dtm;
    public static long rowID;
    public static JTextField tfID ,tfName,tfPrice,tfQ;
    public static  JButton btnEdit;
    public static JButton btnSave;
    public static JButton btnNew;
    public static JButton btnAdd;
    public static JButton btnDelete;
    public static JButton refresh;
    public static JLabel pageLabel;

    public static void setPageLabel(JLabel x,String y){
        x = new JLabel(y);
        x.setBounds(220,20,500,40);
        x.setFont(new Font("Serif", Font.PLAIN, 30));
        x.setVisible(true);
        f.add(x);
    }

    public static void setTable(String t){
        if (t.equals("IM")){
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
                    rowID = Long.parseLong(rowIndex);
                    tfDisable();
                    tfID.setText(rowIndex);tfName.setText(name);tfPrice.setText(price);tfQ.setText(quantity);
                    btnEdit.setEnabled(true);btnDelete.setEnabled(true);
                    btnSave.setEnabled(false);

                }
            });
            JInternalFrame i = new JInternalFrame("List");
            i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            i.setBounds(220,70,300,500);
            i.setVisible(true);
            displayItemList(dtm);
            jt.setVisible(true);
            jt.setBounds(50,50,200,200);
            JScrollPane sp=new JScrollPane(jt);
            sp.setVisible(true);
            sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            i.getContentPane().add(sp);
            f.add(i);
        }
        else if (t.equals("SP")){
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
            JInternalFrame i = new JInternalFrame("Receipt");
            i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            i.setBounds(220,70,300,500);
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
    }

    //--------------inventory Load Start----------------

    int yPoss = 200;
    JLabel lName;

    void addStuff(JLabel x, JTextField y,String z){
        x = new JLabel(z);
        x.setBounds(850,yPoss,125,30);
        x.setBorder(BorderFactory.createEtchedBorder());
        x.setOpaque(false);
        x.setVisible(true);
        y = new JTextField();
        y.setBounds(900,250,125,30);
        y.setBorder(BorderFactory.createEtchedBorder());
        y.setVisible(true);
        f.add(y);
        f.add(x);
        yPoss += 50;
    }



    public static boolean btnMI = false;
    static void tfDisable(){
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
    static void tfEnable(){
        tfID.setEnabled(true);tfName.setEnabled(true);tfPrice.setEnabled(true);
        tfQ.setEnabled(true);
        tfID.setBackground(Color.white); tfName.setBackground(Color.white);
        tfPrice.setBackground(Color.white); tfQ.setBackground(Color.white);
    }
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

        setPageLabel(pageLabel,"Inventory Management");


    }

    static void btnAddNew(){
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

    static void btnAdd(){
        btnAdd = new JButton("Add");
        btnAdd.setBounds(950,350,65,25);
        btnAdd.setVisible(true);
        btnAdd.setForeground(Color.white);
        btnAdd.setBackground(new Color(45, 168, 34));

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

    static void btnDelete(){
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
                    DatabaseConn.deleteFromItemList(rowID);
                    dtm.setRowCount(0);
                    DatabaseConn.displayItemList(dtm);
                }
                catch (Exception exception){
                    System.out.println(exception);
                }

                btnInventory.setEnabled(true);
                btnInventory.doClick();
                refresh.doClick();

            }
        });
        f.add(btnDelete);
        btnDelete.setFocusable(false);
        btnDelete.setBorder(BorderFactory.createEtchedBorder());
        btnDelete.setEnabled(false);
    }

    static void btnRefresh (){
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
                    System.out.println(exception);
                }

            }
        });
        f.add(refresh);
        refresh.setFocusable(false);
        refresh.setBorder(BorderFactory.createEtchedBorder());
        btnEdit.setEnabled(false);btnSave.setEnabled(false);
    }

    static void btnEdit(){
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
        /*


        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

         */
        try{
            btnInventory.setEnabled(true);
            btnInventory.doClick();
            refresh.doClick();
        }catch (Exception ex){

        }


    }

    static void btnSave (){
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
                    //dtm.setRowCount(0);
                    //DatabaseConn.displayItemList(dtm);
                }catch (Exception exception){
                    //JOptionPane.showMessageDialog(null,exception.toString());
                }


                JOptionPane.showMessageDialog(null,"Updated successfully");
                tfDisable();
                btnSave.setEnabled(false);
                //refresh.doClick();
                refresh.doClick(2);
            }
        });
        f.add(btnSave);
        btnSave.setFocusable(false);
        btnSave.setBorder(BorderFactory.createEtchedBorder());
        btnSave.setEnabled(false);
    }

    static void search(){
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

    static void Table(){
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
                rowID = Long.parseLong(rowIndex);
                tfDisable();
                tfID.setText(rowIndex);tfName.setText(name);tfPrice.setText(price);tfQ.setText(quantity);
                btnEdit.setEnabled(true);btnDelete.setEnabled(true);
                btnSave.setEnabled(false);

            }
        });
        JInternalFrame i = new JInternalFrame("List");
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,70,300,500);
        i.setVisible(true);
        displayItemList(dtm);
        jt.setVisible(true);
        jt.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(jt);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        f.add(i);
    }

    public static void loadInventoryPage(){
        f.dispose();
        f = new JFrame();
        f.setTitle("Manage Inventory");
        //btnAddNew();
        btnNew = new JButton("New Item");
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnAddNew");
                JOptionPane.showMessageDialog(null,"Now You can Add a new Item Click Add when you are done","Adding new Item",JOptionPane.INFORMATION_MESSAGE);
                Design.tfDisable();
                Design.tfEnable();
                //tfDisable();
                //tfEnable();
                btnAdd.setEnabled(true);
                btnSave.setEnabled(false);btnEdit.setEnabled(false);
            }
        });
        InventoryFrame.btnAddNew(btnNew,btnAdd,btnSave,btnEdit,f);

        loadFields();
        tfDisable();
        btnAdd();
        btnDelete();
        btnEdit();
        btnSave();
        btnRefresh();
        search();
        //Table();
        setTable("IM");
        //set yPos of Des,gn to 100 so btns on the left sid go back to original place
        Design.yPos=100;
        LoadFrame(f);
        btnSellPageLoad(f);
        btnInventoryLoad(f);
        btnStaffLoad(f);
        btnReportsLoad(f);
        btnExitLoad(f);
        leftPanelLoad(f);
        rightPanelLoad(f);
        SwingUtilities.updateComponentTreeUI(f);
    }
    //--------------inventory Load End----------------


    //--------------Sell Page Start----------------

    public static JTable jtsp;
    public static DefaultTableModel dtmsp;
    public static void LoadSellPage(){
        f.dispose();
        f = new JFrame();



        JLabel test = new JLabel("This is selling page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        f.add(test);

        setPageLabel(pageLabel,"Sell Page");

        setTable("SP");

        Design.yPos=100;
        LoadFrame(f);
        btnSellPageLoad(f);
        btnInventoryLoad(f);
        btnStaffLoad(f);
        btnReportsLoad(f);
        btnExitLoad(f);
        leftPanelLoad(f);
        rightPanelLoad(f);
        LoadDate(f);

    }
    //--------------Sell Page End----------------


    public static void LoadFrame(JFrame f){
        int confirmed;
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        DatabaseConn.foundUser = false;
        f.setSize(1080,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        //isOn = true;
    }


    public static void btnload(JButton x, String y, JFrame f){
        x.setVisible(true);
        x.setText(y);
        x.setFocusable(false);

        x.setBounds(40, yPos, 120, 50);
        x.setBackground(grayish);
        x.setForeground(fff);
        x.setBorder(BorderFactory.createEtchedBorder());
        SwingUtilities.updateComponentTreeUI(f);
    }

    public static void btnSellPageLoad(JFrame f){
        btnSellPage = new JButton();
        btnload(btnSellPage, "Sell Page",f);
        btnSellPage.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(evt.getSource()==btnSellPage){
                    try{
                        //f.dispose();
                        LoadSellPage();
                        btnSellPage.setEnabled(false);
                        //f.dispose();
                    }
                    catch (Exception ex){
                        System.out.println(ex);
                    }
                }
                else {
                    sp = false;
                    btnSellPage.setEnabled(true);
                }
            }
        });
        f.add(btnSellPage);
        yPos +=100;
    }
    public static void btnInventoryLoad(JFrame f){
        btnInventory = new JButton();
        btnload(btnInventory, "Manage Inventory",f);
        btnInventory.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(evt.getSource()==btnInventory){
                    try{
                        //f.dispose();
                        loadInventoryPage();
                        btnInventory.setEnabled(false);
                        //f.dispose();
                    }
                    catch (Exception ex){
                        System.out.println(ex);
                        btnInventory.setEnabled(true);
                    }


                }
                else mi = false;
            }
        });
        f.add(btnInventory);
        yPos +=100;
    }

    public static void btnStaffLoad(JFrame f){
        JButton btnStaff = new JButton();
        btnload(btnStaff,"Manage Staff",f);
        btnStaff.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(evt.getSource()==btnStaff){
                    showMessageDialog(null, "btn Manage Staff");
                    ms=true;
                }
                else ms = false;
            }
        });
        f.add(btnStaff);
        yPos +=100;
    }

    public static void btnReportsLoad(JFrame f){
        JButton btnReports = new JButton();
        btnload(btnReports,"Reports",f);
        btnReports.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(evt.getSource()==btnReports){
                    r = true;
                    showMessageDialog(null, "btn Reports");

                }
                else r = false;
            }
        });
        f.add(btnReports);
        yPos +=100;
    }


    public static void btnExitLoad(JFrame f){
        btnExit = new JButton("Exit");
        btnExit.setBounds(40, yPos+120, 100, 40);
        btnExit.setBackground(Color.red);
        btnExit.setFocusable(false);
        btnExit.setForeground(fff);
        btnExit.setBorder(BorderFactory.createEtchedBorder());
        btnExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(evt.getSource()==btnExit){
                    //btnSellPage.addActionListener(e -> System.out.println("poo"));
                    //System.out.println("poo");

                    int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        //f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        f.dispose();
                        new LoginFrame();
                    }

                    DatabaseConn.foundUser = false;

                }
            }
        });

        f.add(btnExit);
    }


    public static void leftPanelLoad(JFrame f){
        leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 200, 720);
        leftPanel.setBackground(Color.darkGray);
        f.add(leftPanel);
    }
    public static void rightPanelLoad(JFrame f){
        rightPanel = new JPanel();
        rightPanel.setBounds(200, 0, 880, 720);
        rightPanel.setBackground(Color.lightGray);
        f.add(rightPanel);
    }


    public static void LoadDate(JFrame f){
        lDate.setBounds(950, 10, 200, 50);
        lDate.setText(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "uuuu-MM-dd" )));
        lDate.setVisible(true);
        f.add(lDate);
        //lTime.setBounds(950, 30, 200, 50);
        //lDate.setText(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "uuuu-MM-dd" )));
        lTime.setText(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "HH:mm::ss" )));
        //lTime.setText(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "HH:mm::ss" )));
        //lTime.setVisible(true);
        //f.add(lTime);
    }



    public static void main(String[] args) {

        loadInventoryPage();

    }


}
