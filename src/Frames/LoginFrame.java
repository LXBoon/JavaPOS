package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;



public class LoginFrame  implements ActionListener {
    private final JTextField tfUserName;
    private final JPasswordField tfUserPass;
    private final JButton btnLog;
    private final JFrame f = new JFrame();
    public LoginFrame(){
        JLabel lun = new JLabel("User Name: ");
        lun.setBounds(35, 5, 100, 50);
        lun.setForeground(Color.white);
        tfUserName = new JTextField();
        tfUserName.setBounds(140, 20, 120, 20);
        JLabel lUserPass = new JLabel("User Password: ");
        lUserPass.setBounds(35, 35, 100, 50);
        lUserPass.setForeground(Color.white);
        tfUserPass = new JPasswordField();
        tfUserPass.setBounds(140, 50, 120, 20);
        tfUserPass.setEchoChar('*');
        btnLog = new JButton();
        btnLog.setBounds(140, 80, 120, 20);
        btnLog.setText("Log in");
        btnLog.setBackground(Color.BLACK);
        btnLog.setForeground(Color.cyan);
        btnLog.setFocusable(false);
        btnLog.addActionListener(this);
        JCheckBox cbShowPass = new JCheckBox();
        cbShowPass.setBounds(20, 80, 120, 20);
        cbShowPass.setText("Show password");
        cbShowPass.setForeground(Color.white);
        cbShowPass.setOpaque(false);
        ImageIcon img = new ImageIcon("imgs/resturantIcon.jpg");
        f.setTitle("Log in");
        f.setIconImage(img.getImage());
        f.getContentPane().setBackground(new Color(24, 42, 71));
        f.add(lun);f.add(lUserPass);f.add(tfUserName);f.add(tfUserPass);f.add(btnLog);f.add(cbShowPass);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300,150);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        cbShowPass.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                tfUserPass.setEchoChar((char) 0);
            } else {
                tfUserPass.setEchoChar('*');
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== btnLog){
            char[] pass = tfUserPass.getPassword();
            String pas = String.valueOf(pass);
            String un = tfUserName.getText().trim();
            DatabaseConn.findUser(un,pas);
            if (DatabaseConn.foundUser){
                tfUserName.setText(null);
                tfUserPass.setText(null);
                f.dispose();
                Design.loadLandingPage();
            }else {
                JOptionPane.showMessageDialog(null,"Wrong name or password");
            }
        }
    }
}
