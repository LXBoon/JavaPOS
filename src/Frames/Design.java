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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static Frames.DatabaseConn.displayItemList;
import static javax.swing.JOptionPane.showMessageDialog;

public class Design {

    public static int firstTime = 0;
    public static int yPos=100;
    public static boolean sp,mi,ms,r,e;
    public static Color grayish = new Color(100, 114, 140);
    public static Color darkGrayish = new Color(85, 85, 87);
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
    public static JInternalFrame i ;

    public static void setPageLabel(JLabel x,String y){
        x = new JLabel(y);
        x.setBounds(220,20,500,40);
        x.setFont(new Font("Serif", Font.PLAIN, 30));
        x.setVisible(true);
        f.add(x);
    }

    //--------------inventory Load Start----------------

    public static void loadInventoryPage(){

        f.dispose();
        f = new JFrame();

        f.setTitle("Manage Inventory");


        InventoryFrame.inventoryDesign();
        loadDesign(f);
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


    public static void btnload(JButton x, String y, JFrame f){
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
                    }

                    DatabaseConn.foundUser = false;

                }
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


    public static void loadLandingPage(){
        LandingPage.componentLoad(f);
    }

    public static void main(String[] args) {

        loadInventoryPage();

    }


}
