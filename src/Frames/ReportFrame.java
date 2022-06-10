package Frames;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.Serial;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static Frames.DatabaseConn.*;

public class ReportFrame {
    
    static DefaultTableModel dtm;
    static JTable jt;
    static JInternalFrame i,i2;
    static String table;
    static JComboBox<String> comboBox;

    static String[] column ={"Item Log","Staff Log","Receipt Table"};
    static JComboBox<String> cb=new JComboBox<>(column);
    static int r;
    static JButton btnD,btnT,printTable;
    static int fYear =0, fMonth=0, fDay=0 ;
    static int lYear =0, lMonth=0, lDay=0 ;
    static double profit;
    static double tax;
    //static JTextArea area;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    static void reportTable(){
        dtm = new DefaultTableModel();
        jt = new JTable(dtm){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };
        jt.getSelectionModel().addListSelectionListener(event -> {
            try{
                r = Integer.parseInt((String) jt.getValueAt(jt.getSelectedRow(),0));
                btnD.setEnabled(true);

            }catch (Exception exception){
                throw new RuntimeException(exception);
            }
        });
        i = new JInternalFrame(("Reports"),false,false,false,false);
        i.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i.setBounds(220,250,775,400);
        i.setVisible(true);
        i.setClosable(false);
        i.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                i.setLocation(220,250);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                i.setLocation(220,250);
            }
        });
        i.setEnabled(false);



        jt.setVisible(true);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.setDragEnabled(false);


        JScrollPane sp=new JScrollPane(jt);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i.getContentPane().add(sp);
        Design.f.add(i);
    }

    static void loadDetailsFrame(){
        JFrame f = new JFrame(r+" Detail");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DatabaseConn.foundUser = false;
        f.setSize(500,500);
        f.setLayout(null);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        f.setVisible(true);
        DefaultTableModel dtm2 = new DefaultTableModel();
        JTable jt2 = new JTable(dtm2){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };

        jt2.setVisible(true);
        i2 = new JInternalFrame(("List"),false,false,false,false);
        i2.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i2.setBounds(0,0,475,350);
        i2.setVisible(true);
        i2.setClosable(false);
        DatabaseConn.sellsTableSearchR(dtm2, r);
        jt2.setVisible(true);
        JScrollPane sp=new JScrollPane(jt2);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i2.getContentPane().add(sp);
        f.add(i2);
        JButton print = new JButton("Print");
        print.setBounds(250,400,120,35);
        print.setFocusable(false);
        print.setBackground(new Color(66, 253, 67));
        print.setVisible(true);
        print.addActionListener(e1 -> {
            try {
                jt2.print();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        });
        f.add(print);
        JButton cancel = new JButton("Cancel");
        cancel.setBounds(100,400,120,35);
        cancel.setFocusable(false);
        cancel.setBackground(new Color(253, 90, 90));
        cancel.setVisible(true);
        cancel.addActionListener(e12 -> f.dispose());
        f.add(cancel);
    }

    static void loadTaxAndRevenueFrame(){
        JFrame f = new JFrame("Tax and Revenue");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        foundUser = false;
        f.setSize(800,800);
        f.setLayout(null);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setForeground(new Color(0, 23, 64));
        f.setVisible(true);
        SimpleDateFormat y= new SimpleDateFormat("yyyy");
        SimpleDateFormat m= new SimpleDateFormat("M");
        SimpleDateFormat d= new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());

        int year = Integer.parseInt(y.format(date));
        int month = Integer.parseInt(m.format(date));
        int day = Integer.parseInt(d.format(date));
        JLabel label1 = new JLabel("From:");
        label1.setBounds(20,5,50,50);
        label1.setVisible(true);
        f.add(label1);
        label1 = new JLabel("To");
        label1.setBounds(250,5,50,50);
        label1.setVisible(true);
        f.add(label1);
        UtilDateModel model = new UtilDateModel();
        model.setDate(year,month,day);
        model.setValue(date);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePanel.setBounds(20,40,200,150);
        datePanel.setVisible(true);
        datePanel.addActionListener(e13 -> {
            Date s = (Date) datePanel.getModel().getValue();
            fYear = Integer.parseInt(y.format(s));
            fMonth = Integer.parseInt(m.format(s));
            fDay = Integer.parseInt(d.format(s));
        });
        f.add(datePanel);
        UtilDateModel model2 = new UtilDateModel();
        model2.setDate(year,month,day);
        model2.setValue(date);
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        datePanel2.setBounds(250,40,200,150);
        datePanel2.setVisible(true);
        datePanel2.addActionListener(e14 -> {
            Date s = (Date) datePanel2.getModel().getValue();
            lYear = Integer.parseInt(y.format(s));
            lMonth = Integer.parseInt(m.format(s));
            lDay = Integer.parseInt(d.format(s));
        });
        f.add(datePanel2);
        DefaultTableModel dtm2 = new DefaultTableModel();
        JTable jt2 = new JTable(dtm2){
            @Serial
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };

        jt2.setVisible(true);
        i2 = new JInternalFrame(("Tax and Revenue"),false,false,false,false);
        i2.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        i2.setBounds(10,220,700,350);
        i2.setVisible(true);
        i2.setClosable(false);
        jt2.setVisible(true);
        JScrollPane sp=new JScrollPane(jt2);
        sp.setVisible(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        i2.getContentPane().add(sp);
        f.add(i2);
        JButton print = new JButton("Print");
        print.setBounds(400,670,120,35);
        print.setFocusable(false);
        print.setBackground(new Color(66, 253, 67));
        print.setVisible(true);
        print.addActionListener(e15 -> {
            try {
                profit=0;tax=0;
                profit = calculateProfit(fYear,fMonth,fDay,lYear,lMonth,lDay);
                tax =calculateTax(fYear,fMonth,fDay,lYear,lMonth,lDay);
                MessageFormat hdr = new MessageFormat("From: "+fYear+"-"+fMonth+"-"+fDay+"    To: "+lYear+"-"+lMonth+"-"+lDay);
                MessageFormat ftr = new MessageFormat("Sells: "+profit+"           Tax: "+tax+"              Gross Profit:"+(profit-tax)+" ");
                jt2.print(JTable.PrintMode.FIT_WIDTH,hdr,ftr);
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        });
        f.add(print);
        JButton cancel = new JButton("Cancel");
        cancel.setBounds(100,670,120,35);
        cancel.setFocusable(false);
        cancel.setBackground(new Color(253, 90, 90));
        cancel.setVisible(true);
        cancel.addActionListener(e16 -> f.dispose());
        f.add(cancel);
        f.setForeground(new Color(0, 23, 64));
        JLabel l = new JLabel("Sales: ");
        l.setBounds(550,90,120,30);
        l.setVisible(true);
        f.add(l);
        JLabel l2 = new JLabel();
        l2.setBounds(590,90,120,30);
        l2.setVisible(true);
        f.add(l2);
        JLabel l3 = new JLabel("Taxes: ");
        l3.setBounds(550,120,120,30);
        l3.setVisible(true);
        f.add(l3);
        JLabel l4 = new JLabel();
        l4.setBounds(590,120,120,30);
        l4.setVisible(true);
        f.add(l4);
        JLabel l5 = new JLabel();
        l5.setBounds(550,150,120,30);
        l5.setVisible(true);
        f.add(l5);
        JButton btnCalculate = new JButton("Calculate: ");
        btnCalculate.setBounds(500,50,120,35);
        btnCalculate.setFocusable(false);
        btnCalculate.setBackground(new Color(66, 253, 67));
        btnCalculate.setVisible(true);
        btnCalculate.addActionListener(e17 -> {
            dtm2.setRowCount(0);
            dtm2.setColumnCount(0);
            DatabaseConn.receiptTableBetween(dtm2,fYear,fMonth,fDay,lYear,lMonth,lDay);
            profit=0;tax=0;
            profit = DatabaseConn.calculateProfit(fYear,fMonth,fDay,lYear,lMonth,lDay);
            tax = DatabaseConn.calculateTax(fYear,fMonth,fDay,lYear,lMonth,lDay);
            profit = Double.parseDouble(df.format(profit));
            tax = Double.parseDouble(df.format(tax));
            l2.setText(df.format(profit));
            l4.setText(df.format(tax));
            l5.setText("Gross profit:  "+ df.format(profit - tax));
            /*
            area = new JTextArea();
            area.setText(null);
            area.setText("Profit:\t"+profit);
            area.setText(area.getText()+"\nTax:\t"+tax);
            area.setText(area.getText()+"\nGross Profit:\t"+(profit-tax));
            area.setText(area.getText()+"\n---------------------\n");
             */
        });
        f.add(btnCalculate);
    }

    static void search(){
        JLabel label = new JLabel("Search in table By:");
        label.setBounds(300,120,200,40);
        Design.f.add(label);

        JTextField search;
        search = new JTextField();


        cb.setBounds(300, 150,90,20);
        cb.addActionListener(e -> search.setText(null));
        Design.f.add(cb);
        cb.setSelectedIndex(0);

        JLabel searchText = new JLabel();
        searchText.setBounds(300,220,500,40);
        searchText.setVisible(true);
        Design.f.add(searchText);
        search.setBounds(300,180,200,40);
        search.setVisible(true);

        btnD = new JButton("Show Details");
        btnD.setBounds(750,150,120,35);
        btnD.setFocusable(false);
        btnD.setBackground(new Color(66, 253, 67));
        btnD.setVisible(false);
        btnD.setEnabled(false);
        btnD.addActionListener(e -> loadDetailsFrame());
        Design.f.add(btnD);

        btnT = new JButton("Tax and Revenue");
        btnT.setBounds(750,100,150,35);
        btnT.setFocusable(false);
        btnT.setBackground(new Color(174, 253, 103));
        btnT.setVisible(false);
        btnT.addActionListener(e -> loadTaxAndRevenueFrame());
        Design.f.add(btnT);

        comboBox = new JComboBox<>();
        comboBox.setBounds(400, 150,90,20);
        comboBox.addActionListener(e -> search.setText(null));
        comboBox.setVisible(false);
        Design.f.add(comboBox);

        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String rnn = search.getText();
                if (cb.getSelectedIndex() == 0) {
                    searchText.setText(search.getText().trim());
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.itemLogSearch(dtm, searchText.getText(), by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                } else if (cb.getSelectedIndex() == 1) {

                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.staffLogTableSearch(dtm, rnn, by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                } else if (cb.getSelectedIndex() == 2) {
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.sellsTableSearch(dtm, rnn, by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                    search.setVisible(false);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String rnn = search.getText();
                if (cb.getSelectedIndex() == 0) {

                    searchText.setText(search.getText().trim());
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.itemLogSearch(dtm, searchText.getText(), by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                } else if (cb.getSelectedIndex() == 1) {

                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.staffLogTableSearch(dtm, rnn, by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                } else if (cb.getSelectedIndex() == 2) {
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.sellsTableSearch(dtm, rnn, by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String rnn = search.getText();
                if (cb.getSelectedIndex() == 0) {
                    searchText.setText(search.getText().trim());
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.itemLogSearch(dtm, searchText.getText(), by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);

                } else if (cb.getSelectedIndex() == 1) {

                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.staffLogTableSearch(dtm, rnn, by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);


                } else if (cb.getSelectedIndex() == 2) {
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    DatabaseConn.sellsTableSearch(dtm, rnn, by);
                    searchText.setText("Searching ' " + search.getText().trim() + " ' in " + table + " By " + by);
                }
            }
        });
        Design.f.add(search);

        cb.addActionListener(e -> {
            if (cb.getSelectedIndex()==0){
                dtm.setRowCount(0);
                dtm.setColumnCount(0);
                DatabaseConn.itemLogTable(dtm);
                btnD.setVisible(false);
                btnT.setVisible(false);
                printTable.setVisible(true);
                table ="item_log_table";
                search.setVisible(true);
                comboBox.removeAllItems();
                for (int i =0;i<DatabaseConn.columns.length;i++){
                    comboBox.addItem(DatabaseConn.columns[i]);
                }
                comboBox.setVisible(true);
            }
            else if (cb.getSelectedIndex()==1){
                dtm.setRowCount(0);
                dtm.setColumnCount(0);
                DatabaseConn.staffLogTable(dtm);
                table ="staff_log_table";
                btnD.setVisible(false);
                btnT.setVisible(false);
                printTable.setVisible(true);
                search.setVisible(true);
                comboBox.removeAllItems();
                for (int i =0;i<DatabaseConn.columns.length;i++){
                    comboBox.addItem(DatabaseConn.columns[i]);
                }
                comboBox.setVisible(true);
            }
            else if (cb.getSelectedIndex()==2) {
                dtm.setRowCount(0);
                dtm.setColumnCount(0);
                DatabaseConn.receiptTable(dtm);
                table ="receipt_table";
                btnD.setVisible(true);
                btnT.setVisible(true);
                printTable.setVisible(true);
                search.setVisible(false);
                comboBox.removeAllItems();
                comboBox.setVisible(false);
            }
        });


        printTable = new JButton("Print");
        printTable.setBounds(600,180,120,35);
        printTable.setFocusable(false);
        printTable.setBackground(new Color(66, 253, 67));
        printTable.setVisible(false);
        printTable.addActionListener(e -> {
            try {
                jt.print(JTable.PrintMode.FIT_WIDTH);
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        });
        Design.f.add(printTable);
    }

    static void loadReportFrame(){
        reportTable();
        search();
    }

}
