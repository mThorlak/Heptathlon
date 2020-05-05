package model_table;

import rmi_siege.tables.ArticleSiege;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableArticleSiege extends AbstractTableModel {

        private List<ArticleSiege> articles = new ArrayList<>();

        private final String[] header = {"Reference", "Price", "Description"};

        public TableArticleSiege(List<ArticleSiege> articles) {
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
                return articles.get(rowIndex).getDescription();
            else
                return null;
        }

    }
