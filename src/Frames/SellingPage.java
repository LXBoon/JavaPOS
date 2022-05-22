package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;
import java.security.Key;
import java.sql.SQLException;

import static Frames.DatabaseConn.*;

public class SellingPage {
    static boolean no = false, q = false;
    static int rn = DatabaseConn.GetReceiptNum();

    static Font myFont = new Font("SansSerif", Font.BOLD, 20);
    static void newSellTable(){
        Design.dtmsp = new DefaultTableModel();
        Design.jtsp = new JTable(Design.dtmsp){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Design.jtsp.getSelectionModel().addListSelectionListener(event -> {
            try{
            }catch (Exception exception){
                System.out.println("val");
            }
        });
        Design.i = new JInternalFrame(("Receipt"),false,false,false,false);
        Design.i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        Design.i.setBounds(220,70,300,500);
        Design.i.setVisible(true);
        Design.i.setClosable(false);

        sellTable(Design.dtmsp,rn);

        Design.jtsp.setVisible(true);
        Design.jtsp.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(Design.jtsp);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Design.i.getContentPane().add(sp);
        Design.f.add(Design.i);
    }

    static void loadSellField(JLabel l){
        Design.btnNewSell = new JButton("New sell");
        Design.btnNewSell.setBounds(900,80,100,20);
        Design.btnNewSell.setEnabled(true);
        Design.btnNewSell.setFocusable(false);
        Design.btnNewSell.setBackground(new Color(108, 255, 141));
        Design.btnNewSell.addActionListener(e->{
            Design.textFieldQ.setEnabled(true);
            Design.textFieldQ.setText("1");
            Design.textFieldSell.setEnabled(true);
            Design.btnAddSell.setEnabled(true);
            try{

                newReceipt(rn);
                System.out.println(rn);

            }catch (Exception exception){
                System.out.println(exception);
            }

        });
        Design.f.add(Design.btnNewSell);

        l = new JLabel("Item NO:");
        l.setBounds(850,120,150,20);
        l.setVisible(true);
        Design.f.add(l);

        Design.textFieldSell = new JTextField();
        Design.textFieldSell.setBounds(900,120,150,20);
        Design.textFieldSell.setVisible(true);
        Design.textFieldSell.setEnabled(false);
        Design.textFieldSell.addActionListener(e ->{
            Design.btnAddSell.doClick();
        });
        Design.textFieldSell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Design.textFieldSell.setText(null);
                no=true;
                q=false;
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        Design.f.add(Design.textFieldSell);


        l = new JLabel("Item Q:");
        l.setBounds(850,150,150,20);
        l.setVisible(true);
        Design.f.add(l);
        Design.textFieldQ = new JTextField();
        Design.textFieldQ.setText("1");
        Design.textFieldQ.setBounds(900,150,150,20);
        Design.textFieldQ.setVisible(true);
        Design.textFieldQ.setEnabled(false);
        Design.textFieldQ.addActionListener(e->{
            Design.btnAddSell.doClick();
        });
        Design.textFieldQ.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Design.textFieldQ.setText(null);
                q=true;
                no=false;
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        Design.f.add(Design.textFieldQ);

        Design.btnAddSell = new JButton();
        Design.btnAddSell.setText("Add");
        Design.btnAddSell.setBackground(Color.green);
        Design.btnAddSell.setBounds(980,180,75,20);
        Design.btnAddSell.setFocusable(false);
        Design.btnAddSell.setEnabled(false);
        Design.btnAddSell.addActionListener(e->{
            long id = Long.parseLong(Design.textFieldSell.getText().trim());
            int quantity = Integer.parseInt(Design.textFieldQ.getText());
            double price = 0;
            try {
                price = quantity * DatabaseConn.getItemPrice(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            String name=null;
            try {
                name = getItemName(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            try {
                addSell(id,name,quantity,price,rn);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"There is something wrong with item ID or quantity");
            }

            Design.dtmsp.setRowCount(0);
            sellTable(Design.dtmsp,rn);

            System.out.println("Added");
            Design.textFieldSell.setText(null);
            Design.textFieldQ.setText("1");
            Design.textFieldSell.requestFocus();
        });
        Design.f.add(Design.btnAddSell);

    }

    static void newButton(JButton btn, String text,int x,int y){

        btn = new JButton(text);
        btn.setBounds(x,y,45,45);
        btn.setFont(myFont);
        btn.setVisible(true);
        btn.setFocusable(false);
        JButton finalBtn = btn;
        btn.addActionListener(e->{
            if (q){
                String og = Design.textFieldQ.getText();
                Design.textFieldQ.setText(og+ finalBtn.getText());
            } else if (no) {
                String og = Design.textFieldSell.getText();
                Design.textFieldSell.setText(og+ finalBtn.getText());
            }
        });

        Design.f.add(btn);
    }
    static void loadButtons(){
        newButton(Design.btnNum,"0",900,500);
        newButton(Design.btnNum,".",950,500);
        int x = 850, y = 450;
        for (int i=1; i<=9;i++){
            if (i == 4 || i == 7){ y-= 50; x = 850; }
            String str = Integer.toString(i);
            newButton(Design.btnNum,str,x,y);
            x+=50;
        }
    }

    static void tableButtons(){
        Design.btnEditSell = new JButton("Edit Quantity");
        Design.btnEditSell.setBounds(550,150,120,20);
        Design.btnEditSell.setVisible(true);
        Design.btnEditSell.setFocusable(false);
        Design.btnEditSell.setBackground(new Color(255, 158, 0));
        Design.btnEditSell.setEnabled(false);
        Design.btnEditSell.addActionListener(e->{
            Design.btnAddSell.setEnabled(false);
            Design.btnDeleteSell.setEnabled(false);
            Design.textFieldEditQ.setEnabled(true);
            Design.btnSaveSell.setEnabled(true);
        });
        Design.f.add(Design.btnEditSell);

        Design.textFieldEditQ = new JTextField();
        Design.textFieldEditQ.setBounds(680,150,120,20);
        Design.textFieldEditQ.setVisible(true);
        Design.textFieldEditQ.setEnabled(false);
        Design.f.add(Design.textFieldEditQ);

        Design.btnSaveSell = new JButton("Save");
        Design.btnSaveSell.setBounds(680,175,75,20);
        Design.btnSaveSell.setVisible(true);
        Design.btnSaveSell.setFocusable(false);
        Design.btnSaveSell.setBackground(Color.green);
        Design.btnSaveSell.setEnabled(false);
        Design.btnSaveSell.addActionListener(e->{
            Design.btnAddSell.setEnabled(true);
            Design.btnDeleteSell.setEnabled(true);
            Design.textFieldEditQ.setEnabled(false);
            Design.textFieldEditQ.setText(null);
            Design.btnSaveSell.setEnabled(false);
            Design.textFieldSell.requestFocus();
        });
        Design.f.add(Design.btnSaveSell);



        Design.btnDeleteSell = new JButton("Delete");
        Design.btnDeleteSell.setBounds(550,175,120,20);
        Design.btnDeleteSell.setVisible(true);
        Design.btnDeleteSell.setFocusable(false);
        Design.btnDeleteSell.setEnabled(false);
        Design.btnDeleteSell.setBackground(new Color(199, 30, 4));
        Design.f.add(Design.btnDeleteSell);


        JLabel label = new JLabel();
        label.setText("Total: ");
        label.setBounds(250,600,120,20);
        label.setVisible(true);
        label.setFont(myFont);
        Design.f.add(label);
    }


    public static void loadSellingPage(){
        JLabel test = new JLabel("This is selling page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        Design.f.add(test);

        newSellTable();
        loadSellField(Design.labelSell);
        loadButtons();
        tableButtons();

        SwingUtilities.updateComponentTreeUI(Design.f);
        Design.yPos=100;

    }


    public static void main(String[] args) {
        //addSell(1,"1",1,1,rn);
    }
}
