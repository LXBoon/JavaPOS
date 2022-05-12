package Frames;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import static Frames.DatabaseConn.displayItemList;

public class SellingPage {

    JFrame f =new JFrame();
    public static JTable jt;
    public static DefaultTableModel dtm;

    void newSellTable(){
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
    public SellingPage(){
        JLabel test = new JLabel("This is selling page");
        test.setVisible(true);
        test.setBounds(950,500,200,200);
        f.add(test);

        newSellTable();



        SwingUtilities.updateComponentTreeUI(f);
        Design.yPos=100;
        //Design.LoadDesinSP(f);
    }

    public static void main(String[] args) {
        new SellingPage();
    }

}
