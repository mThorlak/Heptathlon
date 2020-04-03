package Client_GUI;

import javax.swing.*;
import java.awt.event.*;

public class Homepage extends JDialog {
    private JPanel contentPane;
    private JButton buttonEnter;
    private JButton buttonQuit;

    public Homepage() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonEnter);

        buttonEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEnter();
            }
        });

        buttonQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onEnter() {
        // add your code here
        JOptionPane.showMessageDialog(null, "Vous êtes au bon endroit");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Homepage dialog = new Homepage();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
