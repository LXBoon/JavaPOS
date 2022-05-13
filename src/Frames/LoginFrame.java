package Frames;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;



public class LoginFrame  implements ActionListener {
    JLabel lun;
    JLabel lupass;
    public JTextField tfun;
    public JPasswordField tfupass;
    public JButton btnlog;
    public JCheckBox cbshowpass;
    public boolean uGood = false;
    public JFrame f = new JFrame();



    public LoginFrame(){

        //LandingPage.isOn = false;
        lun = new JLabel("User Name: ");
        lun.setBounds(35, 5, 100, 50);
        lun.setForeground(Color.white);
        tfun = new JTextField();
        tfun.setBounds(140, 20, 120, 20);


        lupass = new JLabel("User Password: ");
        lupass.setBounds(35, 35, 100, 50);
        lupass.setForeground(Color.white);
        tfupass = new JPasswordField();
        tfupass.setBounds(140, 50, 120, 20);
        tfupass.setEchoChar('*');

        btnlog= new JButton();
        btnlog.setBounds(140, 80, 120, 20);
        btnlog.setText("Log in");
        btnlog.setBackground(Color.BLACK);
        btnlog.setForeground(Color.cyan);
        btnlog.setFocusable(false);
        btnlog.addActionListener(this);


        cbshowpass = new JCheckBox();
        cbshowpass.setBounds(20, 80, 120, 20);
        cbshowpass.setText("Show password");
        cbshowpass.setForeground(Color.white);
        cbshowpass.setOpaque(false);



        ImageIcon img = new ImageIcon("imgs/resturantIcon.jpg");
        f.setTitle("Log in");
        f.setIconImage(img.getImage());
        f.getContentPane().setBackground(new Color(24, 42, 71));
        f.add(lun);f.add(lupass);f.add(tfun);f.add(tfupass);f.add(btnlog);f.add(cbshowpass);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300,150);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);


        cbshowpass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tfupass.setEchoChar((char) 0);
                } else {
                    tfupass.setEchoChar('*');

                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnlog){

            char[] pass = tfupass.getPassword();
            String pas = String.valueOf(pass);
            String un = tfun.getText().trim();
            DatabaseConn.findUser(un,pas);
            if (DatabaseConn.foundUser == true){
                System.out.println("Log in seceded");
                uGood = true;
                tfun.setText(null);
                tfupass.setText(null);
                f.dispose();
                Design.loadLandingPage();

            }else {
                uGood = false;
                System.out.println("Log in failed");
            }
        }
    }
}
