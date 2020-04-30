package ui_package;

import client_package.MainClientShop;
import model_table.TableArticleShop;

import javax.swing.*;
import java.awt.*;

public class TableFrame extends JFrame {

    public TableFrame() throws Exception {
        super();

        MainClientShop clientShop = new MainClientShop();
        TableArticleShop modelTable = new TableArticleShop(clientShop.getQueryShopInterface().getAllArticle());
        setTitle("JTable avec modèle dynamique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // private TableArticleModel modelTable = new TableArticleModel();
        JTable table = new JTable(modelTable);

        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel boutons = new JPanel();

        // boutons.add(new JButton(new AddAction()));
        // boutons.add(new JButton(new RemoveAction()));

        getContentPane().add(boutons, BorderLayout.SOUTH);

        pack();
    }

    public static void main(String[] args) throws Exception {
        new TableFrame().setVisible(true);
    }
/*
    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter");
        }

        public void actionPerformed(ActionEvent e) {
            modele.addAmi(new Ami("Megan", "Sami", Color.green, false, Sport.NATATION));
        }
    }

    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimmer");
        }

        public void actionPerformed(ActionEvent e) {
            int[] selection = tableau.getSelectedRows();

            for(int i = selection.length - 1; i >= 0; i--){
                modele.removeAmi(selection[i]);
            }
        }
    }
    */
}