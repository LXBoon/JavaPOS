package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Design {
    // Design components
    public static JFrame f = new JFrame();
    public static int yPos=100;
    private static final Color grayish = new Color(100, 114, 140);
    private static final Color darkGrayish = new Color(85, 85, 87);
    private static final Color fff = Color.white;
    private static JButton btnSellPage, btnInventory, btnStaff, btnReports, btnExit;
    private static void setPageLabel(String y){
        JLabel pageLabel = new JLabel(y);
        pageLabel.setBounds(220,20,500,40);
        pageLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        pageLabel.setVisible(true);
        f.add(pageLabel);
    }
    private static void btnLoad(JButton x, String y, JFrame f){
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
    private static void btnSellPageLoad(JFrame f){
        btnSellPage = new JButton();
        btnLoad(btnSellPage, "Sell Page",f);
        btnSellPage.addActionListener(evt -> {
            if(evt.getSource()==btnSellPage){
                try{
                    LoadSellPage();
                    btnSellPage.setEnabled(false);
                }
                catch (Exception ex){
                    //JOptionPane.showMessageDialog(null,ex);
                    throw new RuntimeException(ex);
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
    private static void btnInventoryLoad(JFrame f){
        btnInventory = new JButton();
        btnLoad(btnInventory, "Manage Inventory",f);
        btnInventory.addActionListener(evt -> {
            if(evt.getSource()==btnInventory){
                try{
                    try {
                        SellingPage.btnCancelPurchase.doClick();
                    }catch (NullPointerException e){
                    }                    loadInventoryPage();
                    btnInventory.setEnabled(false);
                }
                catch (Exception ex){
                    //JOptionPane.showMessageDialog(null,ex);
                    btnInventory.setEnabled(true);
                    throw new RuntimeException(ex);
                }
            }
            else {
                btnInventory.setEnabled(true);
            }
        });
        f.add(btnInventory);
        yPos +=100;
    }
    private static void btnStaffLoad(JFrame f){
        btnStaff = new JButton();
        btnLoad(btnStaff,"Manage Staff",f);
        btnStaff.addActionListener(evt -> {
            if(evt.getSource()==btnStaff){
                //showMessageDialog(null, "btn Manage Staff");
                try {
                    SellingPage.btnCancelPurchase.doClick();
                }catch (NullPointerException e){
                }
                loadStaffPage();
                btnStaff.setEnabled(false);
            }
        });
        f.add(btnStaff);
        yPos +=100;
    }
    private static void btnReportsLoad(JFrame f){
        btnReports = new JButton();
        btnLoad(btnReports,"Reports",f);
        btnReports.addActionListener(evt -> {
            if(evt.getSource()==btnReports){
                try {
                    SellingPage.btnCancelPurchase.doClick();
                }catch (NullPointerException e){
                }


                loadReportPage();
                btnReports.setEnabled(false);
            }
        });
        f.add(btnReports);
        yPos +=100;
    }
    private static void btnExitLoad(JFrame f){
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
                    try {
                        SellingPage.btnCancelPurchase.doClick();
                    }catch (NullPointerException e){
                    }                    f.dispose();
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
    private static void leftPanelLoad(JFrame f){
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 200, 720);
        leftPanel.setBackground(Color.darkGray);
        f.add(leftPanel);
    }
    private static void rightPanelLoad(JFrame f){
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(200, 0, 880, 720);
        rightPanel.setBackground(Color.lightGray);
        f.add(rightPanel);
    }
    private static void LoadFrame(JFrame f){
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
    }
    //load landing page
    public static void loadLandingPage(){

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

    //staff page load
    public static void loadStaffPage(){
        f.dispose();
        f = new JFrame();
        f.setTitle("Staff Management Page");
        setPageLabel("Staff Management Page");
        StaffManageFrame.loadStaffManageFrame();
        loadDesign(f);
        SwingUtilities.updateComponentTreeUI(f);
    }
    //report page load
    public static void loadReportPage(){
        f.dispose();
        f = new JFrame();
        f.setTitle("Report Page");
        setPageLabel("Report Page");
        ReportFrame.loadReportFrame();
        loadDesign(f);
        SwingUtilities.updateComponentTreeUI(f);
    }

}
