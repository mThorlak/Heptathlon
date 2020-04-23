package rmi_general;


import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import rmi_shop.tables.Article;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {

    private final static String BILL_PATH = "Server/resources/bill.csv";
    private final static String BILL_PAID_PATH = "Server/resources/bill_paid.csv";
    private final static char SEPARATOR = ';';

    public List<String[]> readLineByLine() {
        try {
            FileReader reader = new FileReader(BILL_PATH);

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

    public void writeNewBill(Bill bill) throws IOException {
        try (
                FileWriter writer = new FileWriter(BILL_PATH, true);
                CSVWriter csvWriter = new CSVWriter(writer,
                        SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            List<Article> articleBought = bill.getArticles();
            List<String[]> allBills = readLineByLine();
            String articleFormatted = "";
            String idBill;

            if (allBills.isEmpty()) {
                idBill = "1";
                String[] headerRecord = {"Date", "IDBill", "Total", "Payment", "References"};
                csvWriter.writeNext(headerRecord);
            } else {
                String[] lastLine = allBills.get(allBills.size() - 1);
                idBill = Integer.toString(Integer.parseInt(lastLine[1]) + 1);
            }

            int cpt = 0;
            for (Article article : articleBought) {
                cpt++;
                articleFormatted = articleFormatted.concat("|" + article.getReference());
                if (cpt == articleBought.size()) {
                    articleFormatted = articleFormatted.concat("|" + article.getPrice() + "|;");
                } else
                    articleFormatted = articleFormatted.concat("|" + article.getPrice() + "|,");
            }

            csvWriter.writeNext(
                    new String[]{
                            bill.getDate(),
                            idBill,
                            Double.toString(bill.getTotal()),
                            bill.getPayment(),
                            articleFormatted
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payBill(int idBill) throws IOException {

        List<String[]> allBills = readLineByLine();
        String[] billPaid;
        int cpt = 0;
        for (String[] bill : allBills) {
            // Not compare header
            if (cpt == 0) {
                cpt++;
                continue;
            }
            if (Integer.parseInt(bill[1]) == idBill) {
                billPaid = bill;
                allBills.remove(cpt);
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

    /*
    TODO : create column in csv and use it to make object with line simplier
     https://www.callicoder.com/java-read-write-csv-file-opencsv/
     */
/*
    public Bill convertLineInBill (String[] lineBill) {



        // Bill bill = new Bill();
        return new bill;
    }
    */
}

