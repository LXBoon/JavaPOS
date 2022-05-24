package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.swing.JOptionPane.showMessageDialog;

public class Design {

    // Design components
    public static JFrame f = new JFrame();
    public  static JDesktopPane desktopPane = new JDesktopPane();
    public static int yPos=100;
    public static Color grayish = new Color(100, 114, 140);
    public static Color darkGrayish = new Color(85, 85, 87);
    public static Color fff = Color.white;
    public static JLabel lDate,lTime;
    public static JPanel leftPanel,rightPanel;
    public static JButton btnSellPage, btnInventory, btnStaff, btnReports, btnExit;

    public static void setPageLabel(String y){
        pageLabel = new JLabel(y);
        pageLabel.setBounds(220,20,500,40);
        pageLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        pageLabel.setVisible(true);
        f.add(pageLabel);
    }

    public static void btnLoad(JButton x, String y, JFrame f){
        x.setVisible(true);
        x.setText(y);
        x.setFocusable(false);
        x.setBounds(40, yPos, 120, 50);
        x.setBackground(grayish);
        x.setForeground(fff);
        x.setBorder(BorderFactory.createEtchedBorder());
        x.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                x.setBackground(darkGrayish);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                x.setBackground(grayish);
            }
        });


        SwingUtilities.updateComponentTreeUI(f);
    }
    public static void btnSellPageLoad(JFrame f){
        btnSellPage = new JButton();
        btnLoad(btnSellPage, "Sell Page",f);
        btnSellPage.addActionListener(evt -> {
            if(evt.getSource()==btnSellPage){
                try{
                    LoadSellPage();
                    btnSellPage.setEnabled(false);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }
            }
            else {
                //sp = false;
                btnSellPage.setEnabled(true);
            }
        });
        f.add(btnSellPage);
        yPos +=100;
    }
    public static void btnInventoryLoad(JFrame f){
        btnInventory = new JButton();
        btnLoad(btnInventory, "Manage Inventory",f);
        btnInventory.addActionListener(evt -> {
            if(evt.getSource()==btnInventory){
                try{
                    loadInventoryPage();
                    btnInventory.setEnabled(false);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                    btnInventory.setEnabled(true);
                }
            }
            else {
                btnInventory.setEnabled(true);
            }
        });
        f.add(btnInventory);
        yPos +=100;
    }
    public static void btnStaffLoad(JFrame f){
        btnStaff = new JButton();
        btnLoad(btnStaff,"Manage Staff",f);
        btnStaff.addActionListener(evt -> {
            if(evt.getSource()==btnStaff){
                showMessageDialog(null, "btn Manage Staff");
            }
        });
        f.add(btnStaff);
        yPos +=100;
    }
    public static void btnReportsLoad(JFrame f){
        btnReports = new JButton();
        btnLoad(btnReports,"Reports",f);
        btnReports.addActionListener(evt -> {
            if(evt.getSource()==btnReports){
                showMessageDialog(null, "btn Reports");

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
        btnExit.addActionListener(evt -> {
            if(evt.getSource()==btnExit){
                int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    //f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    f.dispose();
                }
                DatabaseConn.foundUser = false;
            }
        });
        btnExit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnExit.setBackground(new Color(128, 5, 5));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setBackground(Color.red);
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
        lDate = new JLabel();
        lDate.setBounds(950, 10, 200, 50);
        lDate.setText(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "uuuu-MM-dd" )));
        lDate.setVisible(true);
        f.add(lDate);
        lTime=new JLabel();
        lTime.setBounds(950, 30, 200, 50);
        lTime.setText(LocalDateTime.now().format( DateTimeFormatter.ofPattern( "HH:mm:ss" )));
        lTime.setVisible(true);
        f.add(lTime);
    }
    public static void loadDesign(JFrame f){
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
        LoadDate(f);
    }
    public static void LoadFrame(JFrame f){
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        DatabaseConn.foundUser = false;
        f.setSize(1080,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        //isOn = true;
    }


    //inventory components

    public static JTable jt;
    public static DefaultTableModel dtm;
    public static long rowID;
    public static JTextField tfID ,tfName,tfPrice,tfQ;
    public static  JButton btnEdit,refresh,btnDelete,btnAdd,btnNew,btnSave;
    public static JLabel pageLabel;
    public static JInternalFrame i ;

    //Sell page components
    public static JTable jtsp;
    public static DefaultTableModel dtmsp;
    public static int rn;

    public static JButton btnNewSell,btnNum,btnAddSell,btnEditSell,btnSaveSell,btnDeleteSell,btnCompletePurchase;
    public static JTextField textFieldSell,textFieldQ,textFieldEditQ;
    public static JLabel labelSell;




    //load landing page
    public static void loadLandingPage(){
        setPageLabel("About page");
        LandingPage.componentLoad(f);
    }


    //inventory frame load
    public static void loadInventoryPage(){

        f.dispose();
        f = new JFrame();
        f.setTitle("Manage Inventory");
        setPageLabel("Inventory Management");
        InventoryFrame.inventoryDesign();
        loadDesign(f);
        SwingUtilities.updateComponentTreeUI(f);
    }




    //load sell page
    public static void LoadSellPage(){
        f.dispose();
        f = new JFrame();
        f.setTitle("Selling Page");
        setPageLabel("Sell Page");
        SellingPage.loadSellingPage();
        loadDesign(f);
        SwingUtilities.updateComponentTreeUI(f);
    }










}
