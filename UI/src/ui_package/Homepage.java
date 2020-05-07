package ui_package;

import ui_package.ui_general.GeneralFrameSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Homepage {
    private JPanel MainPanel;
    private JButton EnterButton;
    private JLabel jLabelLogo;
    private JPanel panelLogo;
    private BufferedImage image;

    public static void main(String[] args) {
        new Homepage();
    }

    public Homepage() {
        JFrame homepage = new JFrame("Heptathlon");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(homepage);
        homepage.setContentPane(MainPanel);
        homepage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        homepage.setSize(300, 200);
        homepage.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());

        homepage.pack();
        homepage.setVisible(true);

        EnterButton.addActionListener(e -> {
            try {
                new ArticleManager();
                homepage.dispose();
            } catch (Exception remoteException) {
                remoteException.printStackTrace();
            }
        });
    }

    private void createUIComponents() throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("UI/resources/logo.png"));
        jLabelLogo = new JLabel(new ImageIcon(myPicture));
    }
}
