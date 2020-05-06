package test;

import client_package.ClientShop;
import client_package.ClientSiege;

public class DatabaseFiller {

    public static void main(String[] args) throws Exception {
        FillSiege();
        FillShop();
    }

    /**
     * Fill DB Siege with some references and articles
     */
    public static void FillSiege() throws Exception {

        // Fill reference
        ClientSiege clientSiege = new ClientSiege();
        String reference1 = "L00000";
        String reference2 = "R00000";

        System.out.println("Fill reference siege in progress ...");
        for (int i = 1; i < 6; i++) {
            reference1 = reference1.substring(0, reference1.length() - 1) + i;
            reference2 = reference2.substring(0, reference2.length() - 1) + i;
            clientSiege.getQuerySiegeInterface().insertNewReference("Longboard", reference1);
            clientSiege.getQuerySiegeInterface().insertNewReference("Rugby", reference2);
        }
        System.out.println("Fill reference siege done !");

        // Fill article
        reference1 = "L00000";
        reference2 = "R00000";
        double price = 20;

        System.out.println("Fill article siege in progress ...");
        for (int i = 1; i < 6; i++) {
            reference1 = reference1.substring(0, reference1.length() - 1) + i;
            reference2 = reference2.substring(0, reference2.length() - 1) + i;
            clientSiege.getQuerySiegeInterface().insertNewArticle(reference1, price, reference1 + " description");
            clientSiege.getQuerySiegeInterface().insertNewArticle(reference2, price, reference2 + " description");
            price = price + 10;
        }
        System.out.println("Fill article siege done !");

    }

    /**
     * Fill DB Shop with some references and articles
     */
    public static void FillShop() throws Exception {

        ClientShop clientShop = new ClientShop();

        // Fill reference
        String reference2 = "R00000";
        // Add new reference not existing in siege database, the siege database will add it automatically thanks to function
        // insertNewReference coming from QueryShopInterface
        String reference3 = "F00000";

        System.out.println("Fill reference shop in progress ...");
        for (int i = 1; i < 6; i++) {
            reference2 = reference2.substring(0, reference2.length() - 1) + i;
            reference3 = reference3.substring(0, reference3.length() - 1) + i;
            clientShop.getQueryShopInterface().insertNewReference("Rugby", reference2);
            clientShop.getQueryShopInterface().insertNewReference("Football", reference3);
        }
        System.out.println("Fill reference shop done !");

        // Fill article
        reference2 = "R00000";
        reference3 = "F00000";
        double price = 10;
        int stock = 100;
        String shopName = "shop1";

        System.out.println("Fill article shop in progress ...");
        for (int i = 1; i < 6; i++) {
            reference2 = reference2.substring(0, reference2.length() - 1) + i;
            reference3 = reference3.substring(0, reference3.length() - 1) + i;
            clientShop.getQueryShopInterface().insertNewArticle(reference2, price, stock,  reference2 + " description", shopName);
            clientShop.getQueryShopInterface().insertNewArticle(reference3, price, stock,  reference3 + " description", shopName);
            price = price + 10;
            stock = stock + 20;
        }
        System.out.println("Fill article shop done !");
    }
}
