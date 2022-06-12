package Frames;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



public class LandingPage extends JFrame {


    static Font myFontN= new Font("Courier", Font.PLAIN, 20);
    static Font myFontB= new Font("Courier", Font.BOLD, 20);

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



        //ImageIcon twitterIcon = new ImageIcon(new ImageIcon("images/twitter-icon.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        JLabel twitter = new JLabel("@MustafaW97", Main.twitterIcon, JLabel.CENTER);
        twitter.setBounds(200,400,300,70);
        twitter.setFont(myFontN);
        twitter.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://twitter.com/MustafaW97"));
                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                twitter.setFont(myFontB);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                twitter.setFont(myFontN);
            }
        });
        Design.f.add(twitter);


        //ImageIcon gitIcon = new ImageIcon(new ImageIcon("images/github-icon.png").getImage().getScaledInstance(100, 70, Image.SCALE_DEFAULT));
        JLabel gitHub = new JLabel("LXBoon", Main.gitIcon, JLabel.CENTER);
        gitHub.setBounds(400,400,300,70);
        gitHub.setFont(myFontN);
        gitHub.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/LXBoon"));
                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                gitHub.setFont(myFontB);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gitHub.setFont(myFontN);
            }
        });
        Design.f.add(gitHub);


        //ImageIcon linkedINIcon = new ImageIcon(new ImageIcon("images/linkedin-icon.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        JLabel linkedIN = new JLabel("MUSTAFA WAISULLAH", Main.linkedINIcon, JLabel.CENTER);
        linkedIN.setBounds(650,400,300,70);
        linkedIN.setFont(myFontN);
        linkedIN.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/mustafa-w-0a5813224/"));
                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkedIN.setFont(myFontB);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkedIN.setFont(myFontN);
            }
        });
        Design.f.add(linkedIN);


        //ImageIcon emailIcon = new ImageIcon(new ImageIcon("images/email-icon.jpg").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        JLabel email = new JLabel("mustafa.waisullah.1997@gmail.com\n", Main.emailIcon, JLabel.CENTER);
        email.setBounds(220,500,700,70);
        email.setFont(myFontN);
        email.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        String address = "mustafa.waisullah.1997@gmail.com";
                        Desktop.getDesktop().mail(new URI("mailto:" + address + "?subject=Hello"));
                    } catch (URISyntaxException | IOException ex) {
                        // ...
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                email.setFont(myFontB);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                email.setFont(myFontN);
            }
        });
        Design.f.add(email);




        Design.loadDesign(f);
        SwingUtilities.updateComponentTreeUI(f);
    }

    public LandingPage(){
        //componentLoad(f);
        //this loop stays last
    }


}
