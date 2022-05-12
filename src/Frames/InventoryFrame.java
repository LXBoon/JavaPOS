package Frames;

import Frames.LandingPage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Frames.DatabaseConn.columns;
import static Frames.DatabaseConn.displayItemList;

public class InventoryFrame  {

    public static void btnAddNew(JButton x, JButton add, JButton save, JButton edit, JFrame f){
        //x = new JButton("New Item");
        x.setBounds(950,115,65,25);
        x.setVisible(true);
        x.setForeground(Color.white);
        x.setBackground(new Color(45, 168, 34));
        /*
        x.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btnAddNew");
                JOptionPane.showMessageDialog(null,"Now You can Add a new Item Click Add when you are done","Adding new Item",JOptionPane.INFORMATION_MESSAGE);
                Design.tfDisable();
                Design.tfEnable();
                //tfDisable();
                //tfEnable();
                add.setEnabled(true);
                save.setEnabled(false);edit.setEnabled(false);
            }
        });
         */
        f.add(x);
        x.setFocusable(false);
        x.setBorder(BorderFactory.createEtchedBorder());
        SwingUtilities.updateComponentTreeUI(f);
        x.setEnabled(true);
    }



}