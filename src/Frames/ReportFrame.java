package Frames;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;

import static Frames.DatabaseConn.searchStaff;
import static Frames.DatabaseConn.showStaffTable;

public class ReportFrame {
    
    static DefaultTableModel dtm;
    static JTable jt;
    static JInternalFrame i;


    
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


            }catch (Exception exception){
                System.out.println(exception);
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
        String[] columns ={"Item Log","Sell Log","Staff Log"};
        JComboBox<String> cb=new JComboBox<>(columns);
        cb.setBounds(300, 150,90,20);
        cb.addActionListener(e -> search.setText(null));
        Design.f.add(cb);
        JLabel searchText = new JLabel();
        searchText.setBounds(300,220,500,40);
        searchText.setVisible(true);
        Design.f.add(searchText);
        search.setBounds(300,180,200,40);
        search.setVisible(true);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                searchStaff(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                searchStaff(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchText.setText(search.getText().trim());
                dtm.setRowCount(0);
                String by = cb.getItemAt(cb.getSelectedIndex());
                searchStaff(dtm,searchText.getText(),by);
                searchText.setText("Searching ' "+search.getText().trim()+" ' in table By "+by);
            }
        });
        Design.f.add(search);

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cb.getSelectedIndex()==0){
                    DatabaseConn.logTable(dtm);
                }
            }
        });
    }

    static void loadReportFrame(){
        reportTable();
        search();
    }

}
