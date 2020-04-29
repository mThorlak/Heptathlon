package Ui_package;

import rmi_shop.tables.Article;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableArticleModel extends AbstractTableModel {

        private List<Article> articles = new ArrayList<Article>();

        private final String[] header = {"Reference", "Price", "Quantity", "Description"};

        public TableArticleModel(List<Article> articles) {
            super();
            this.articles = articles;
        }

        public int getRowCount() {
            return articles.size();
        }

        public int getColumnCount() {
            return header.length;
        }

        public String getColumnName(int columnIndex) {
            return header[columnIndex];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0)
                return articles.get(rowIndex).getReference();
            else if (columnIndex == 1)
                return articles.get(rowIndex).getPrice();
            else if (columnIndex == 2)
                return articles.get(rowIndex).getStock();
            else if (columnIndex == 3)
                return articles.get(rowIndex).getDescription();
            else
                return null;
        }

        public void addAmi(Article article) {
            articles.add(article);
            fireTableRowsInserted(articles.size() - 1, articles.size() - 1);
        }

        public void removeAmi(int rowIndex) {
            articles.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
