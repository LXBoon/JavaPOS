package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.Serial;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Frames.DatabaseConn.*;

public class SellingPage {

    static JTable jts;
    static DefaultTableModel dtm;
    static int rn;

    static JButton btnNewSell;
    static JButton btnAddSell;
    static JButton btnEditSell;
    static JButton btnSaveSell;
    static JButton btnDeleteSell;
    static JButton btnCompletePurchase;
    static JButton refresh;
    static JButton btnCancelPurchase;
    static JTextField textFieldSell,textFieldQ,textFieldEditQ;
    static JInternalFrame i;
    
    
    static boolean no = false, q = false, eq=false;

    
    static Font myFont = new Font("SansSerif", Font.BOLD, 20);
    static JLabel TotalPrice,totalTax;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    static JTextArea area1;
    static JTextArea paidAmount;
    static JRadioButton cash,card;
    static ButtonGroup paymentType ;

    static String type,ogQty,date;
    static double paid,change,totalP,totTax,totPrice;
    static int sellItemID;//the ID off sell table
    static long itemID;

    static void newSellTable(){
        dtm = new DefaultTableModel();
        jts = new JTable(dtm){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        jts.getSelectionModel().addListSelectionListener(e -> {
            try{
                sellItemID = Integer.parseInt(jts.getValueAt(jts.getSelectedRow(),0).toString());
                itemID = Long.parseLong(jts.getValueAt(jts.getSelectedRow(),1).toString());
                ogQty = jts.getValueAt(jts.getSelectedRow(),3).toString();
                btnDeleteSell.setEnabled(true);
                btnEditSell.setEnabled(true);
                //JOptionPane.showMessageDialog(null,ogQty);
                textFieldEditQ.setText(ogQty);

            }catch (Exception exception){
                throw new RuntimeException(exception);
            }
        });
        i = new JInternalFrame(("Receipt"),false,false,false,false);
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,70,300,500);
        i.setVisible(true);
        i.setClosable(false);

        sellTable(dtm,rn);

        jts.setVisible(true);
        jts.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(jts);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        Design.f.add(i);
    }

    static void loadSellField(){
        btnNewSell = new JButton("New sell");
        btnNewSell.setBounds(900,80,100,20);
        btnNewSell.setEnabled(true);
        btnNewSell.setFocusable(false);
        btnNewSell.setBackground(new Color(108, 255, 141));
        btnNewSell.addActionListener(e->{
            totalP = 0;
            totTax = 0;
            btnNewSell.setEnabled(false);
            textFieldQ.setEnabled(true);
            textFieldQ.setText("1");
            textFieldSell.setEnabled(true);
            btnAddSell.setEnabled(true);
            btnCompletePurchase.setEnabled(true);
            btnCancelPurchase.setEnabled(true);
            try{
                rn= DatabaseConn.GetReceiptNum();
                SimpleDateFormat DH= new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date datef = new Date(System.currentTimeMillis());
                date = DH.format(datef);
                newReceipt(rn,date);
            }catch (Exception exception){
                throw new RuntimeException(exception);
            }

        });
        Design.f.add(btnNewSell);

        JLabel l = new JLabel("Item NO:");
        l.setBounds(850,120,150,20);
        l.setVisible(true);
        Design.f.add(l);

        textFieldSell = new JTextField();
        textFieldSell.setBounds(900,120,150,20);
        textFieldSell.setVisible(true);
        textFieldSell.setEnabled(false);
        textFieldSell.addActionListener(e -> btnAddSell.doClick());
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
        textFieldQ.addActionListener(e-> btnAddSell.doClick());
        textFieldQ.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textFieldQ.setText(null);
                no=false;
                q=true;
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
            long id = Long.parseLong(textFieldSell.getText());
            int ItemQuantity = DatabaseConn.getItemQuantity(id);
            if (ItemQuantity>0){
                int quantity = Integer.parseInt(textFieldQ.getText());
                if (ItemQuantity-quantity>=0){
                    double price = quantity * DatabaseConn.getItemPrice(id);
                    double tax = getItemTax(id);
                    String name = getItemName(id);
                    double taxX = price * tax / 100;
                    price += taxX;
                    price = Double.parseDouble(df.format(price));
                    addSell(id,name,quantity,price,tax,rn);

                    totTax += taxX;
                    //refresh.doClick();
                    refresh();
                    totPrice= DatabaseConn.getTotalPrice(rn);
                    TotalPrice.setText(df.format(totPrice));
                    //totTax = DatabaseConn.getTotalTax(rn);
                    totalTax.setText(df.format(totTax));
                    textFieldSell.setText(null);
                    textFieldQ.setText("1");
                    no=true;
                    q=false;
                    eq =false;
                    textFieldSell.requestFocus();
                }
                else {
                    JOptionPane.showMessageDialog(null,"Can not sell Item amount that exceeds the amount in Inventory\n" +
                            "Inventory Item Count: "+ItemQuantity);
                    textFieldSell.setText(null);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Ran out of Item in Inventory");
                textFieldSell.setText(null);
            }
        });
        Design.f.add(btnAddSell);

    }

    static void newButton(String text, int x, int y){

        JButton btn = new JButton(text);
        btn.setBounds(x,y,45,45);
        btn.setFont(myFont);
        btn.setVisible(true);
        btn.setFocusable(false);
        btn.addActionListener(e->{
            if (q){
                String og = textFieldQ.getText();
                textFieldQ.setText(og+ btn.getText());
            } else if (no) {
                String og = textFieldSell.getText();
                textFieldSell.setText(og+ btn.getText());
            }
            else if (eq){
                String og = textFieldEditQ.getText();
                textFieldEditQ.setText(og+ btn.getText());
            }
        });

        Design.f.add(btn);
    }
    static void loadButtons(){
        newButton("0",900,500);
        newButton(".",950,500);
        int x = 850, y = 450;
        for (int i=1; i<=9;i++){
            if (i == 4 || i == 7){ y-= 50; x = 850; }
            String str = Integer.toString(i);
            newButton(str,x,y);
            x+=50;
        }
    }

    static void refresh(){
        boolean de;
        try{
            dtm.setColumnCount(0);
            dtm.setRowCount(0);
            de = true;
        }catch (Exception exception){
            de = false;
            refresh.doClick();
        }
        if (de) {
            DatabaseConn.sellTable(dtm,rn);
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
            eq =true;
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
                no=false;
                q=false;
                eq =true;
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
            int qty;
            double price;
            try {
                qty = Integer.parseInt(textFieldEditQ.getText());
                long id = itemID;
                int ItemQuantity = DatabaseConn.getItemQuantity(id);
                if (qty>=1) {
                    if (ItemQuantity-qty>=0){
                        int quantity = Integer.parseInt(ogQty);
                        price = quantity * DatabaseConn.getItemPrice(id);
                        double tax = getItemTax(id);
                        double taxX = price * tax / 100;
                        totTax -= taxX;
                        id = itemID;
                        quantity = qty;
                        price = quantity * DatabaseConn.getItemPrice(id);
                        tax = getItemTax(id);
                        taxX = price * tax / 100;
                        totTax += taxX;
                        totalTax.setText(df.format(totTax));
                        textFieldSell.setText(null);
                        textFieldQ.setText("1");
                        no = true;
                        q = false;
                        eq = false;
                        textFieldSell.requestFocus();
                        textFieldEditQ.setEnabled(false);
                        updateQuantity(sellItemID, itemID, qty, price);
                        totPrice= DatabaseConn.getTotalPrice(rn);
                        totPrice+=taxX;
                        TotalPrice.setText(df.format(totPrice));
                        DatabaseConn.sellTable(dtm,rn);
                        //refresh.doClick();
                        refresh();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Can not sell Item amount that exceeds the amount in Inventory\n" +
                                "Inventory Item Count: "+ItemQuantity);
                    }
                }else JOptionPane.showMessageDialog(null,"can't be 0 or less");
            }catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "is empty");
            }
        });
        btnSaveSell.setBackground(new Color(138, 246, 0));
        Design.f.add(btnSaveSell);
        refresh = new JButton("Refresh");
        refresh.setBounds(750,400,65,25);
        refresh.setVisible(true);
        refresh.setForeground(Color.white);
        refresh.setBackground(new Color(1, 22, 62));
        refresh.addActionListener(e -> {
            boolean de;
            try{
                dtm.setColumnCount(0);
                dtm.setRowCount(0);
                de = true;
            }catch (Exception exception){
                de = false;
                refresh.doClick();
            }
            if (de) {
                DatabaseConn.sellTable(dtm,rn);
            }
        });
        Design.f.add(refresh);
        refresh.setFocusable(false);
        refresh.setBorder(BorderFactory.createEtchedBorder());
        refresh.setVisible(false);
        SwingUtilities.updateComponentTreeUI(Design.f);
        btnDeleteSell = new JButton("Delete");
        btnDeleteSell.setBounds(550,175,120,30);
        btnDeleteSell.setVisible(true);
        btnDeleteSell.setFocusable(false);
        btnDeleteSell.setEnabled(false);
        btnDeleteSell.addActionListener(e->{
            long id = itemID;
            int quantity = Integer.parseInt(ogQty);
            double price = quantity * DatabaseConn.getItemPrice(id);
            double tax = getItemTax(id);
            double taxX = price * tax / 100;
            totTax -= taxX;
            totalTax.setText(df.format(totTax));
            textFieldSell.setText(null);
            textFieldQ.setText("1");
            no=true;
            q=false;
            eq = false;
            textFieldSell.requestFocus();
            DatabaseConn.deleteSell(sellItemID,itemID);
            refresh();
            totPrice = DatabaseConn.getTotalPrice(rn);
            TotalPrice.setText(df.format(totPrice));
        });
        btnDeleteSell.setBackground(new Color(199, 30, 4));
        btnDeleteSell.setForeground(Color.white);
        Design.f.add(btnDeleteSell);
        btnCompletePurchase = new JButton("Complete Purchase");
        btnCompletePurchase.setBounds(550,500,150,30);
        btnCompletePurchase.setVisible(true);
        btnCompletePurchase.setFocusable(false);
        btnCompletePurchase.setEnabled(false);
        btnCompletePurchase.setBackground(new Color(121, 255, 106));
        btnCompletePurchase.addActionListener(e->{
            try {
                totTax = Double.parseDouble(totalTax.getText());
                totalP = Double.parseDouble(TotalPrice.getText());
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
                String ch =df.format(change);
                double chng= Double.parseDouble(ch);
                setPurchase(rn,totalP,paid,chng,type,totTax);
                JOptionPane.showMessageDialog(null,"Total price:  "+totalP+"\nPaid: "+paid+"\nchange:  "+chng+" ");
                String areaString = "------------------- RECEIPT -------------------";
                area1.setText("Receipt NO:"+rn+"\n");
                area1.setText(area1.getText()+"\nDate:"+date+"\n");
                area1.setText(area1.getText()+"\n"+areaString+"\n");
                area1.setText(area1.getText()+"\nName\tQuantity\tPrice\tTax\n");
                area1.setText(area1.getText()+"\n----------------------------------------------------\n");
                for (int i = 0; i< jts.getRowCount(); i++){
                    String name = jts.getValueAt(i,2).toString();
                    String quantity = jts.getValueAt(i,3).toString();
                    String price = jts.getValueAt(i,4).toString();
                    String tax = jts.getValueAt(i,5).toString();
                    area1.setText(area1.getText()+"\n"+name+"\t"+quantity+"\t"+price+"\t"+tax+"\n");
                }
                area1.setText(area1.getText()+"-----------------------------------------------------\n"
                        +"Total Price: "+totalP+"\nTotal Tax:"+totTax+"\nPaid amount: "+ paid+"\nChange: "+chng);
                area1.setText(area1.getText()+"");
                area1.print();
                dtm.setRowCount(0);dtm.setColumnCount(0);
                btnAddSell.setEnabled(false);textFieldSell.setEnabled(false);
                textFieldQ.setEnabled(false);btnNewSell.setEnabled(true);
                paidAmount.setText(null);paidAmount.setVisible(false);
                TotalPrice.setText(null);totalTax.setText(null);
                totalP=0;
                totTax=0;
                btnCompletePurchase.setEnabled(false);
                btnCancelPurchase.setEnabled(false);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                throw new RuntimeException(ex);
            }
        });
        Design.f.add(btnCompletePurchase);
        btnCancelPurchase = new JButton("Cansel Purchase");
        btnCancelPurchase.setBounds(550,550,150,30);
        btnCancelPurchase.setVisible(true);
        btnCancelPurchase.setFocusable(false);
        btnCancelPurchase.setEnabled(false);
        btnCancelPurchase.setBackground(new Color(243, 35, 35));
        btnCancelPurchase.setForeground(Color.white);
        btnCancelPurchase.addActionListener(e->{
            try {

                int rowCount = jts.getRowCount();

                for (int i =0; i<rowCount; i++){
                    int id = (int) jts.getValueAt(i,0);
                    long itemID = (long) jts.getValueAt(i,1);
                    DatabaseConn.cancelPurchase(id,itemID);
                }
                dtm.setRowCount(0);
                dtm.setColumnCount(0);
                btnAddSell.setEnabled(false);
                textFieldSell.setEnabled(false);
                textFieldQ.setEnabled(false);
                paidAmount.setText(null);
                paidAmount.setVisible(false);
                TotalPrice.setText(null);
                totalTax.setText(null);
                btnNewSell.setEnabled(true);
                totalP=0;
                totTax=0;
                btnCompletePurchase.setEnabled(false);
                btnCancelPurchase.setEnabled(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                throw new RuntimeException(ex);
            }
        });
        Design.f.add(btnCancelPurchase);
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

        JLabel labelTax = new JLabel();
        labelTax.setText("Tax: ");
        labelTax.setBounds(250,622,120,20);
        labelTax.setVisible(true);
        labelTax.setFont(myFont);
        Design.f.add(labelTax);

        totalTax = new JLabel();
        totalTax.setBounds(370,622,120,20);
        totalTax.setVisible(true);
        Design.f.add(totalTax);

    }


    public static void loadSellingPage(){

        newSellTable();
        try{
            dtm.setRowCount(0);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        loadSellField();
        loadButtons();
        tableButtons();

        SwingUtilities.updateComponentTreeUI(Design.f);
        Design.yPos=100;

    }


    public static void main(String[] args) {
        //addSell(1,"1",1,1,rn);
    }
}
