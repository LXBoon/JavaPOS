package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.Serial;
import java.security.Key;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import static Frames.DatabaseConn.*;

public class SellingPage {

    static JTable jtsp;
    static DefaultTableModel dtmsp;
    static int rn;

    static JButton btnNewSell,btnNum,btnAddSell,btnEditSell,btnSaveSell,btnDeleteSell,btnCompletePurchase;
    static JTextField textFieldSell,textFieldQ,textFieldEditQ;
    static JLabel labelSell;
    static JInternalFrame i;
    
    
    static boolean no = false, q = false, eq=false;

    
    static Font myFont = new Font("SansSerif", Font.BOLD, 20);
    static JLabel TotalPrice;
    static JTextArea area1;
    static JTextArea paidAmount;
    static JRadioButton cash,card;
    static ButtonGroup paymentType ;

    static String type,ogQty,date;
    static double paid,change,totalP;
    static int sellItemID;//the ID off sell table
    static long itemID;
    
    

    static void newSellTable(){
        dtmsp = new DefaultTableModel();
        jtsp = new JTable(dtmsp){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtsp.getSelectionModel().addListSelectionListener(event -> {
            try{
                sellItemID = Integer.parseInt(jtsp.getValueAt(jtsp.getSelectedRow(),0).toString());
                itemID = Long.parseLong(jtsp.getValueAt(jtsp.getSelectedRow(),1).toString());
                ogQty = jtsp.getValueAt(jtsp.getSelectedRow(),3).toString();
                btnDeleteSell.setEnabled(true);
                btnEditSell.setEnabled(true);
                //JOptionPane.showMessageDialog(null,ogQty);
                textFieldEditQ.setText(ogQty);

            }catch (Exception exception){
                System.out.println(exception);
            }
        });
        i = new JInternalFrame(("Receipt"),false,false,false,false);
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,70,300,500);
        i.setVisible(true);
        i.setClosable(false);

        sellTable(dtmsp,rn);

        jtsp.setVisible(true);
        jtsp.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(jtsp);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        Design.f.add(i);
    }

    static void loadSellField(JLabel l){
        btnNewSell = new JButton("New sell");
        btnNewSell.setBounds(900,80,100,20);
        btnNewSell.setEnabled(true);
        btnNewSell.setFocusable(false);
        btnNewSell.setBackground(new Color(108, 255, 141));
        btnNewSell.addActionListener(e->{
            textFieldQ.setEnabled(true);
            textFieldQ.setText("1");
            textFieldSell.setEnabled(true);
            btnAddSell.setEnabled(true);
            try{
                rn= DatabaseConn.GetReceiptNum();
                SimpleDateFormat DH= new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date datef = new Date(System.currentTimeMillis());
                date = DH.format(datef);
                newReceipt(rn,date);
                System.out.println(rn);

            }catch (Exception exception){
                System.out.println(exception);
            }

        });
        Design.f.add(btnNewSell);

        l = new JLabel("Item NO:");
        l.setBounds(850,120,150,20);
        l.setVisible(true);
        Design.f.add(l);

        textFieldSell = new JTextField();
        textFieldSell.setBounds(900,120,150,20);
        textFieldSell.setVisible(true);
        textFieldSell.setEnabled(false);
        textFieldSell.addActionListener(e ->{
            btnAddSell.doClick();
        });
        textFieldSell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textFieldSell.setText(null);
                no=true;
                q=false;
                eq =false;
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
        Design.f.add(textFieldSell);


        l = new JLabel("Item Q:");
        l.setBounds(850,150,150,20);
        l.setVisible(true);
        Design.f.add(l);
        textFieldQ = new JTextField();
        textFieldQ.setText("1");
        textFieldQ.setBounds(900,150,150,20);
        textFieldQ.setVisible(true);
        textFieldQ.setEnabled(false);
        textFieldQ.addActionListener(e->{
            btnAddSell.doClick();
        });
        textFieldQ.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textFieldQ.setText(null);
                q=true;
                no=false;
                eq =false;
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
        Design.f.add(textFieldQ);

        btnAddSell = new JButton();
        btnAddSell.setText("Add");
        btnAddSell.setBackground(Color.green);
        btnAddSell.setBounds(980,180,75,20);
        btnAddSell.setFocusable(false);
        btnAddSell.setEnabled(false);
        btnAddSell.addActionListener(e->{
            long id = Long.parseLong(textFieldSell.getText().trim());
            int quantity = Integer.parseInt(textFieldQ.getText());
            double price = 0;
            price = quantity * DatabaseConn.getItemPrice(id);
            String name=null;
            name = getItemName(id);
            addSell(id,name,quantity,price,rn);
            System.out.println(rn);
            dtmsp.setRowCount(0);
            sellTable(dtmsp,rn);
            String totPrice= String.valueOf(DatabaseConn.getTotalPrice(rn));
            TotalPrice.setText(totPrice);
            textFieldSell.setText(null);
            textFieldQ.setText("1");
            no=true;
            q=false;
            eq =false;
            textFieldSell.requestFocus();
        });
        Design.f.add(btnAddSell);

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
                String og = textFieldQ.getText();
                textFieldQ.setText(og+ finalBtn.getText());
            } else if (no) {
                String og = textFieldSell.getText();
                textFieldSell.setText(og+ finalBtn.getText());
            }
            else if (eq){
                String og = textFieldSell.getText();
                textFieldSell.setText(og+ finalBtn.getText());
            }
        });

        Design.f.add(btn);
    }
    static void loadButtons(){
        newButton(btnNum,"0",900,500);
        newButton(btnNum,".",950,500);
        int x = 850, y = 450;
        for (int i=1; i<=9;i++){
            if (i == 4 || i == 7){ y-= 50; x = 850; }
            String str = Integer.toString(i);
            newButton(btnNum,str,x,y);
            x+=50;
        }
    }

    static void tableButtons(){

        btnEditSell = new JButton("Edit Quantity");
        btnEditSell.setBounds(550,125,120,30);
        btnEditSell.setVisible(true);
        btnEditSell.setFocusable(false);
        btnEditSell.setEnabled(false);
        btnEditSell.addActionListener(e->{

            btnSaveSell.setEnabled(true);
            btnDeleteSell.setEnabled(false);
            textFieldEditQ.setEnabled(true);
            textFieldEditQ.transferFocus();
            no=false;
            q=false;
            eq=true;


        });
        btnEditSell.setBackground(new Color(255, 220, 2));
        Design.f.add(btnEditSell);

        textFieldEditQ = new JTextField();
        textFieldEditQ.setBounds(690,125,60,20);
        textFieldEditQ.setVisible(true);
        textFieldEditQ.setEnabled(false);
        textFieldEditQ.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textFieldEditQ.setText(null);
                q=true;
                no=false;
                eq =false;
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
        Design.f.add(textFieldEditQ);

        btnSaveSell = new JButton("Save");
        btnSaveSell.setBounds(690,150,120,25);
        btnSaveSell.setVisible(true);
        btnSaveSell.setFocusable(false);
        btnSaveSell.setEnabled(false);
        btnSaveSell.addActionListener(e->{
            int qty = 0;
            double price=0;
            try {
                qty = Integer.parseInt(textFieldEditQ.getText());
                price = qty * getItemPrice(itemID);

                if (qty>=1) {
                    updateQuantity(sellItemID, itemID, qty, price);
                    jtsp.clearSelection();
                    dtmsp.setRowCount(0);
                    sellTable(dtmsp, rn);
                    String totPrice = String.valueOf(DatabaseConn.getTotalPrice(rn));
                    TotalPrice.setText(totPrice);
                    textFieldSell.setText(null);
                    textFieldQ.setText("1");
                    no = true;
                    q = false;
                    eq = false;
                    textFieldSell.requestFocus();
                    textFieldEditQ.setEnabled(false);
                }else JOptionPane.showMessageDialog(null,"can't be 0 or less");
            }catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "is empty");
            }






        });
        btnSaveSell.setBackground(new Color(138, 246, 0));
        Design.f.add(btnSaveSell);


        btnDeleteSell = new JButton("Delete");
        btnDeleteSell.setBounds(550,175,120,30);
        btnDeleteSell.setVisible(true);
        btnDeleteSell.setFocusable(false);
        btnDeleteSell.setEnabled(false);
        btnDeleteSell.addActionListener(e->{
            DatabaseConn.deleteSell(sellItemID,itemID);

            jtsp.clearSelection();
            dtmsp.setRowCount(0);
            sellTable(dtmsp,rn);
            String totPrice= String.valueOf(DatabaseConn.getTotalPrice(rn));
            TotalPrice.setText(totPrice);
            textFieldSell.setText(null);
            textFieldQ.setText("1");
            no=true;
            q=false;
            eq = false;
            textFieldSell.requestFocus();

        });
        btnDeleteSell.setBackground(new Color(199, 30, 4));
        Design.f.add(btnDeleteSell);


        btnCompletePurchase = new JButton("Complete Purchase");
        btnCompletePurchase.setBounds(550,550,150,30);
        btnCompletePurchase.setVisible(true);
        btnCompletePurchase.setFocusable(false);
        btnCompletePurchase.setBackground(new Color(121, 255, 106));
        btnCompletePurchase.addActionListener(e->{

            try {
                totalP = getTotalPrice(rn);
                if (card.isSelected()) {
                    paid = totalP;
                    type = "card";
                }
                else paid = Double.parseDouble(paidAmount.getText());
                if (cash.isSelected()) {
                    change = paid - totalP;
                    type = "cash";
                }
                else change = 0;
                setPurchase(rn,totalP,paid,change,type);
                JOptionPane.showMessageDialog(null,"Total price:  "+totalP+"\nPaid: "+paid+"\nchange:  "+change+" ");

                String areaString = "--------------- RECEIPT ---------------";
                area1.setText("Receipt NO:"+rn+"\n");
                area1.setText(area1.getText()+"\nDate:"+date+"\n");
                area1.setText(area1.getText()+"\n"+areaString+"\n");

                area1.setText(area1.getText()+"\nName\tQuantity\tPrice\n");
                area1.setText(area1.getText()+"\n--------------------------------------------\n");

                for (int i =0;i<jtsp.getRowCount();i++){
                    String name = jtsp.getValueAt(i,2).toString();
                    String quantity = jtsp.getValueAt(i,3).toString();
                    String price = jtsp.getValueAt(i,4).toString();
                    area1.setText(area1.getText()+"\n"+name+"\t"+quantity+"\t"+price+"\n");
                }
                area1.setText(area1.getText()+"--------------------------------------------\n"
                        +"Total Price: "+getTotalPrice(rn)+"\nPaid amount: "+ paid+"\nChange: "+change);
                area1.setText(area1.getText()+"");
                area1.print();
                dtmsp.setRowCount(0);
                btnAddSell.setEnabled(false);
                textFieldSell.setEnabled(false);
                textFieldQ.setEnabled(false);
                paidAmount.setText(null);
                paidAmount.setVisible(false);
                TotalPrice.setText(null);

            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        Design.f.add(btnCompletePurchase);







        area1 = new JTextArea();
        area1.setBounds(600,250,200,300);
        area1.setVisible(false);
        JScrollPane sp = new JScrollPane(area1);
        Design.f.getContentPane().add(sp);
        Design.f.add(area1);


        cash = new JRadioButton("Cash");
        cash.setBounds(550,300,120,50);
        cash.setFont(myFont);
        cash.addActionListener(e->{
            if(cash.isSelected()){
                System.out.println("cash");
                paidAmount.setVisible(true);
            }

        });
        cash.setVisible(true);
        cash.setOpaque(false);
        Design.f.add(cash);

        card = new JRadioButton("Card");
        card.setBounds(550,350,120,50);
        card.setFont(myFont);
        card.addActionListener(e->{
            if(card.isSelected()){
                System.out.println("card");
                paidAmount.setVisible(false);
            }

        });
        card.setVisible(true);
        card.setOpaque(false);
        Design.f.add(card);

        paymentType= new ButtonGroup();
        paymentType.add(cash);
        paymentType.add(card);


        paidAmount = new JTextArea();
        paidAmount.setBounds(550,400,120,50);
        paidAmount.setVisible(false);
        Design.f.add(paidAmount);

        JLabel label = new JLabel();
        label.setText("Total: ");
        label.setBounds(250,600,120,20);
        label.setVisible(true);
        label.setFont(myFont);
        Design.f.add(label);

        TotalPrice = new JLabel();
        TotalPrice.setBounds(370,600,120,20);
        TotalPrice.setVisible(true);
        Design.f.add(TotalPrice);

    }


    public static void loadSellingPage(){
        JLabel test = new JLabel("This is selling page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        Design.f.add(test);
        newSellTable();
        try{
            dtmsp.setRowCount(0);
        }
        catch (Exception e){
            System.out.println(e);
        }
        loadSellField(labelSell);
        loadButtons();
        tableButtons();

        SwingUtilities.updateComponentTreeUI(Design.f);
        Design.yPos=100;

    }


    public static void main(String[] args) {
        //addSell(1,"1",1,1,rn);
    }
}
