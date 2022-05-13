package Frames;


import java.awt.*;

import javax.swing.*;


import static javax.swing.JOptionPane.showMessageDialog;



import java.awt.event.*;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;



public class LandingPage extends JFrame {
    //public static JInternalFrame frame2 = new JInternalFrame();
    //public static JFrame f = new JFrame();
    public static void componentLoad(JFrame f){
        JLabel test = new JLabel("This project was made by:");
        test.setVisible(true);
        test.setBounds(220,50,400,200);
        test.setFont(new Font("Serif", Font.BOLD, 30));
        f.add(test);
        JLabel test2 = new JLabel("MUSTAFA WAISULLAH");
        test2.setVisible(true);
        test2.setBounds(290,100,400,200);
        test2.setFont(new Font("Serif", Font.BOLD, 30));
        f.add(test2);
        JLabel test3 = new JLabel("As a university project");
        test3.setVisible(true);
        test3.setBounds(600,150,400,200);
        test3.setFont(new Font("Serif", Font.BOLD, 30));
        f.add(test3);


        //Design.LoadDesinSP(f);
        Design.loadDesign(f);
        SwingUtilities.updateComponentTreeUI(f);
    }

    public LandingPage(){
        //componentLoad(f);
        //this loop stays last
    }

    public static void main(String[] args) {
        new LandingPage();
    }
}
