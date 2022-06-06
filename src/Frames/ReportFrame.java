package Frames;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.Serial;

import static Frames.DatabaseConn.*;

public class ReportFrame {
    
    static DefaultTableModel dtm;
    static JTable jt;
    static JInternalFrame i,i2;
    static String table;
    static String columns[];
    static JComboBox<String> comboBox;

    static String[] column ={"Item Log","Staff Log","Receipt Table"};
    static JComboBox<String> cb=new JComboBox<>(column);
    static int rn;
    static JButton btnD,btnT;

    
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
                if (cb.getSelectedIndex()==2){
                    rn = Integer.parseInt((String) jt.getValueAt(jt.getSelectedRow(),0));
                    btnD.setEnabled(true);
                }
            }catch (Exception exception){
                System.out.println(exception);
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

    static void search(){
        JLabel label = new JLabel("Search in table By:");
        label.setBounds(300,120,200,40);
        Design.f.add(label);

        JTextField search;
        search = new JTextField();


        cb.setBounds(300, 150,90,20);
        cb.addActionListener(e -> search.setText(null));
        Design.f.add(cb);
        cb.setSelectedIndex(-1);

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
        btnD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame(rn+" Detail");
                f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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

                sellsTableSearch(dtm2,rn);

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
                print.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            jt2.print();
                        } catch (PrinterException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                f.add(print);

                JButton cancel = new JButton("Cancel");
                cancel.setBounds(100,400,120,35);
                cancel.setFocusable(false);
                cancel.setBackground(new Color(253, 90, 90));
                cancel.setVisible(true);
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f.dispose();
                    }
                });
                f.add(cancel);

            }
        });
        Design.f.add(btnD);

        btnT = new JButton("Tax and Revenue");
        btnT.setBounds(750,100,150,35);
        btnT.setFocusable(false);
        btnT.setBackground(new Color(174, 253, 103));
        btnT.setVisible(false);
        btnT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame(rn+" Detail");
                f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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


                i2 = new JInternalFrame(("Tax and Revenue"),false,false,false,false);
                i2.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
                i2.setBounds(0,0,475,350);
                i2.setVisible(true);
                i2.setClosable(false);

                sellsTableSearch(dtm2,rn);//change here

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
                print.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            jt2.print();
                        } catch (PrinterException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                f.add(print);

                JButton cancel = new JButton("Cancel");
                cancel.setBounds(100,400,120,35);
                cancel.setFocusable(false);
                cancel.setBackground(new Color(253, 90, 90));
                cancel.setVisible(true);
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f.dispose();
                    }
                });
                f.add(cancel);

            }
        });
        Design.f.add(btnT);

        comboBox = new JComboBox<>();
        comboBox.setBounds(400, 150,90,20);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search.setText(null);
            }
        });
        comboBox.setVisible(false);
        Design.f.add(comboBox);

        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int rnn = Integer.parseInt(search.getText());
                if (cb.getSelectedIndex()==1){
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);

                    sellsTableSearch(dtm, rnn);

                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());

                    searchText.setText("Searching ' "+search.getText().trim()+" ' in "+table+" By "+by);
                }
                else{
                    searchText.setText(search.getText().trim());
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    itemLogSearch(dtm,searchText.getText(),by);
                    searchText.setText("Searching ' "+search.getText().trim()+" ' in "+table+" By "+by);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                int rnn = Integer.parseInt(search.getText());
                if (cb.getSelectedIndex()==1){
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    sellsTableSearch(dtm, rnn);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    searchText.setText("Searching ' "+search.getText().trim()+" ' in "+table+" By "+by);
                }
                else{
                    searchText.setText(search.getText().trim());
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    itemLogSearch(dtm,searchText.getText(),by);
                    searchText.setText("Searching ' "+search.getText().trim()+" ' in "+table+" By "+by);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                int rnn = Integer.parseInt(search.getText());
                if (cb.getSelectedIndex()==1){
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);

                    sellsTableSearch(dtm, rnn);

                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());

                    searchText.setText("Searching ' "+search.getText().trim()+" ' in "+table+" By "+by);
                }
                else{
                    searchText.setText(search.getText().trim());
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    String by = comboBox.getItemAt(comboBox.getSelectedIndex());
                    itemLogSearch(dtm,searchText.getText(),by);
                    searchText.setText("Searching ' "+search.getText().trim()+" ' in "+table+" By "+by);
                }
            }
        });
        Design.f.add(search);

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cb.getSelectedIndex()==0){
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    DatabaseConn.itemLogTable(dtm);
                    btnD.setVisible(false);


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

                    search.setVisible(true);
                    comboBox.removeAllItems();
                    for (int i =0;i<DatabaseConn.columns.length;i++){
                        comboBox.addItem(DatabaseConn.columns[i]);
                    }
                    comboBox.setVisible(true);
                } else if (cb.getSelectedIndex()==2) {
                    dtm.setRowCount(0);
                    dtm.setColumnCount(0);
                    DatabaseConn.receiptTable(dtm);
                    table ="receipt_table";

                    btnD.setVisible(true);btnT.setVisible(true);

                    search.setVisible(false);
                    comboBox.removeAllItems();
                    comboBox.setVisible(false);
                }
            }
        });



    }

    static void loadReportFrame(){
        reportTable();
        search();
    }

}
