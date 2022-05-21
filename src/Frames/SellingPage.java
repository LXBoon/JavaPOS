package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.Serial;

import static Frames.DatabaseConn.displayItemList;

public class SellingPage {
    static void newSellTable(DefaultTableModel dtm, JTable jt){
        Design.dtm = new DefaultTableModel();
        Design.jt = new JTable(Design.dtm){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Design.jt.getSelectionModel().addListSelectionListener(event -> {
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
        Design.jt.setVisible(true);
        Design.jt.setBounds(50,50,200,200);
        JScrollPane sp=new JScrollPane(Design.jt);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Design.i.getContentPane().add(sp);
        Design.f.add(Design.i);
    }
    static void newButton(JButton btn, String text,int x,int y){
        Font myFont = new Font("SansSerif", Font.BOLD, 20);


        btn = new JButton(text);
        btn.setBounds(x,y,45,45);
        btn.setFont(myFont);
        //btn.setVisible(true);
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
    public static void loadSellingPage(){
        JLabel test = new JLabel("This is selling page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        Design.f.add(test);

        newSellTable(Design.dtmsp,Design.jtsp);
        loadButtons();

        SwingUtilities.updateComponentTreeUI(Design.f);
        Design.yPos=100;

    }



}
