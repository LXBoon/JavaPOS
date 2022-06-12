package Frames;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {


    static URL twitterURL = Main.class.getResource("/images/twitter-icon.png");//assuming your package name is images
    static Image tPicture = Toolkit.getDefaultToolkit().getImage(twitterURL);
    static URL gitUrl = Main.class.getResource("/images/github-icon.png");//assuming your package name is images
    static Image gitPicture = Toolkit.getDefaultToolkit().getImage(gitUrl);
    static URL linkURL = Main.class.getResource("/images/linkedin-icon.png");//assuming your package name is images

    static Image linkPicture = Toolkit.getDefaultToolkit().getImage(linkURL);
    static URL mailURL = Main.class.getResource("/images/email-icon.jpg");//assuming your package name is images
    static Image mailPicture = Toolkit.getDefaultToolkit().getImage(mailURL);




    public static ImageIcon twitterIcon = new ImageIcon(new ImageIcon(tPicture).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));

    //public static ImageIcon twitterIcon = new ImageIcon(new ImageIcon("src/twitter-icon.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    public static ImageIcon gitIcon = new ImageIcon(new ImageIcon(gitPicture).getImage().getScaledInstance(100, 70, Image.SCALE_DEFAULT));
    public static ImageIcon linkedINIcon = new ImageIcon(new ImageIcon(linkPicture).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    public static ImageIcon emailIcon = new ImageIcon(new ImageIcon(mailPicture).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));


    public static void main(String[] args) {
        new LoginFrame();
    }
}
