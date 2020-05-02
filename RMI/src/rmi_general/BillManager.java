package rmi_general;


import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import rmi_shop.tables.Article;
import rmi_siege.tables.Bill;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BillManager {

    private final String BILL_PATH = "Server/resources/bill.csv";
    private final String BILL_PAID_PATH = "Server/resources/bill_paid.csv";
    private final char SEPARATOR = ';';

    public String getBillPath() {
        return BILL_PATH;
    }

    public String getBillPaidPath() {
        return BILL_PAID_PATH;
    }

    public List<String[]> readLineByLine(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();

            List<String[]> bills = new ArrayList<>();
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                bills.add(nextRecord);
            }
            csvReader.close();
            return bills;

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeNewBill(Bill bill, boolean inPaidBill) throws IOException {
        String path;
        if (inPaidBill)
            path = BILL_PAID_PATH;
        else
            path = BILL_PATH;
        try (
                FileWriter writer = new FileWriter(path, true);
                CSVWriter csvWriter = new CSVWriter(writer,
                        SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            List<Article> articleBought = bill.getArticles();
            List<String[]> allBills = readLineByLine(path);
            String articleFormatted = "";
            String idBill;

            if (allBills.isEmpty()) {
                idBill = bill.getId();
                String[] headerRecord = {"Date", "IDBill", "Shop", "Total", "Payment", "References"};
                csvWriter.writeNext(headerRecord);
            } else {
                String[] lastLine = allBills.get(allBills.size() - 1);
                idBill = bill.getId();
                if (idBill.equals(lastLine[1])) {
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    idBill = bill.getShop() + ts.getTime()+1;
                }
            }

            int cpt = 0;
            for (Article article : articleBought) {
                cpt++;
                articleFormatted = articleFormatted.concat("|" + article.getReference());
                articleFormatted = articleFormatted.concat("|" + article.getPrice());
                if (cpt == articleBought.size()) {
                    articleFormatted = articleFormatted.concat("|" + article.getStock() + "|;");
                } else
                    articleFormatted = articleFormatted.concat("|" + article.getStock() + "|,");
            }

            csvWriter.writeNext(
                    new String[]{
                            bill.getDate(),
                            idBill,
                            bill.getShop(),
                            Float.toString(bill.getTotal()),
                            bill.getPayment(),
                            articleFormatted
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payBill(String idBill) throws IOException {

        List<String[]> allBills = readLineByLine(BILL_PATH);
        String[] billPaid;
        int cpt = 0;
        for (String[] billString : allBills) {
            // Not compare header
            if (cpt == 0) {
                cpt++;
                continue;
            }
            if (billString[1].equals(idBill)) {
                billPaid = billString;
                allBills.remove(cpt);
                Bill bill = convertLineInBill(billString);
                writeNewBill(bill, true);
                break;
            }
            cpt++;
        }

        FileWriter fileWriter = new FileWriter(BILL_PATH);
        CSVWriter csvWriter = new CSVWriter(fileWriter,
                SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        csvWriter.writeAll(allBills);
        csvWriter.close();
    }

    public Bill convertLineInBill(String[] lineCSV) throws FileNotFoundException {

        String date = lineCSV[0];
        String id = lineCSV[1];
        String shop = lineCSV[2];
        float total = Float.parseFloat(lineCSV[3]);
        String payment = lineCSV[4];
        String stringArticles = lineCSV[5];

        List<Article> articles = new ArrayList<>();
        boolean isDone = false;
        boolean referenceIsDone = false;
        boolean priceIsDone = false;
        boolean quantityIsDone = false;

        String reference = "";
        float price = 0;
        int quantity = 0;

        while (!isDone) {
            if (stringArticles.charAt(0) == '|' && !referenceIsDone) {
                stringArticles = stringArticles.substring(1);
                reference = stringArticles.substring(0, stringArticles.indexOf('|'));
                referenceIsDone = true;
                stringArticles = stringArticles.substring(stringArticles.indexOf('|'));
            }
            if (stringArticles.charAt(0) == '|' && referenceIsDone && !priceIsDone) {
                stringArticles = stringArticles.substring(1);
                price = Float.parseFloat(stringArticles.substring(0, stringArticles.indexOf('|')));
                priceIsDone = true;
                stringArticles = stringArticles.substring(stringArticles.indexOf('|'));
            }
            if (stringArticles.charAt(0) == '|' && referenceIsDone && priceIsDone && !quantityIsDone) {
                stringArticles = stringArticles.substring(1);
                quantity = Integer.parseInt(stringArticles.substring(0, stringArticles.indexOf('|')));
                quantityIsDone = true;
                stringArticles = stringArticles.substring(stringArticles.indexOf('|'));
            }
            if (stringArticles.length() > 1) {
                if (stringArticles.charAt(1) == ',' && referenceIsDone && priceIsDone) {
                    stringArticles = stringArticles.substring(2);
                    referenceIsDone = false;
                    priceIsDone = false;
                    quantityIsDone = false;
                    articles.add(new Article(reference, price, quantity));
                }
            }
            else {
                isDone = true;
                articles.add(new Article(reference, price, quantity));
            }
        }

        return new Bill(date, id, shop, total, payment, articles);
    }

}

