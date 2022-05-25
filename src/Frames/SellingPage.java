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
    static boolean no = false, q = false, e=false;


    static Font myFont = new Font("SansSerif", Font.BOLD, 20);
    static JLabel TotalPrice;
    static JTextArea area1;
    static JTextArea paidAmount;
    static JRadioButton cash,card;
    static ButtonGroup paymentType ;

    static String type,name,ogQty,date;
    static double paid,change,totalP;

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
                name = Design.jtsp.getValueAt(Design.jtsp.getSelectedRow(),0).toString();
                ogQty = Design.jtsp.getValueAt(Design.jtsp.getSelectedRow(),1).toString();
                JOptionPane.showMessageDialog(null,Design.rn);
                Design.textFieldEditQ.setText(ogQty);
                Design.btnEditSell.setEnabled(true);
                Design.btnDeleteSell.setEnabled(true);
            }catch (Exception exception){
                System.out.println("val");
            }
        });
        Design.i = new JInternalFrame(("Receipt"),false,false,false,false);
        Design.i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        Design.i.setBounds(220,70,300,500);
        Design.i.setVisible(true);
        Design.i.setClosable(false);

        sellTable(Design.dtmsp,Design.rn);

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
                Design.rn= DatabaseConn.GetReceiptNum();
                SimpleDateFormat DH= new SimpleDateFormat("yyyy-MM-dd HH-mm:ss");
                Date datef = new Date(System.currentTimeMillis());
                date = DH.format(datef);
                newReceipt(Design.rn,date);
                System.out.println(Design.rn);

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
                addSell(id,name,quantity,price,Design.rn);
                System.out.println(Design.rn);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"There is something wrong with item ID or quantity");
            }

            Design.dtmsp.setRowCount(0);
            sellTable(Design.dtmsp,Design.rn);
            String totPrice= String.valueOf(DatabaseConn.getTotalPrice(Design.rn));
            TotalPrice.setText(totPrice);

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
        Design.btnDeleteSell = new JButton("Delete");
        Design.btnDeleteSell.setBounds(550,175,120,20);
        Design.btnDeleteSell.setVisible(true);
        Design.btnDeleteSell.setFocusable(false);
        Design.btnDeleteSell.setEnabled(false);
        Design.btnDeleteSell.setBackground(new Color(199, 30, 4));
        Design.f.add(Design.btnDeleteSell);


        Design.btnCompletePurchase = new JButton("Complete Purchase");
        Design.btnCompletePurchase.setBounds(550,550,150,30);
        Design.btnCompletePurchase.setVisible(true);
        Design.btnCompletePurchase.setFocusable(false);
        Design.btnCompletePurchase.setBackground(new Color(121, 255, 106));
        Design.btnCompletePurchase.addActionListener(e->{

            try {
                totalP = getTotalPrice(Design.rn);
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
                setPurchase(Design.rn,totalP,paid,change,type);
                JOptionPane.showMessageDialog(null,"Total price:  "+totalP+"\nPaid: "+paid+"\nchange:  "+change+" ");

                String areaString = "--------------- RECEIPT ---------------";
                area1.setText("Receipt NO:"+Design.rn+"\n");
                area1.setText(area1.getText()+"\nDate:"+date+"\n");
                area1.setText(area1.getText()+"\n"+areaString+"\n");
                /*
                area1.setText(area1.getText()+"\nName\tQuantity\tPrice\n");
                area1.setText(area1.getText()+"\n----\t--------\t-----\n");
                 */
                for (int i =0;i<Design.jtsp.getRowCount();i++){
                    String name = Design.jtsp.getValueAt(i,2).toString();
                    String quantity = Design.jtsp.getValueAt(i,3).toString();
                    String price = Design.jtsp.getValueAt(i,4).toString();
                    area1.setText(area1.getText()+"\n"+name+"\t"+quantity+"\t"+price+"\n");
                }
                area1.setText(area1.getText()+"--------------------------------------\n"
                        +"Total Price: "+getTotalPrice(Design.rn)+"\nPaid amount: "+ paid+"\nChange: "+change);
                area1.setText(area1.getText()+"");
                area1.print();
                Design.dtmsp.setRowCount(0);
                Design.btnNewSell.doClick();
                q=false;
                no=true;


            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        Design.f.add(Design.btnCompletePurchase);



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

    }


    public static void loadSellingPage(){
        JLabel test = new JLabel("This is selling page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        Design.f.add(test);



        newSellTable();
        try{
            Design.dtmsp.setRowCount(0);
        }
        catch (Exception e){
            System.out.println(e);
        }
        loadSellField(Design.labelSell);
        loadButtons();
        tableButtons();

        SwingUtilities.updateComponentTreeUI(Design.f);
        Design.yPos=100;

    }


    public static void main(String[] args) {
        //addSell(1,"1",1,1,Design.rn);
    }
}
