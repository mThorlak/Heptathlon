package ui_package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Homepage {
    private JPanel MainPanel;
    private JButton EnterButton;

    public static void main(String[] args) {
        Homepage frame = new Homepage();
    }

    public Homepage() {
        JFrame homepage = new JFrame("Homepage");
        GeneralFrameSettings generalFrameSettings = new GeneralFrameSettings(homepage);
        homepage.setContentPane(MainPanel);
        homepage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        homepage.setSize(300, 200);
        homepage.setLocation(generalFrameSettings.getLocationX(), generalFrameSettings.getLocationY());
        homepage.setVisible(true);

        EnterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    homepage.dispose();
                    new Getterpage();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                } catch (NotBoundException notBoundException) {
                    notBoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
    }
}
