package rmi_general;

import rmi_shop.tables.Article;

import java.util.List;

public class Bill {

    private String date;
    private String id;
    private double total;
    private String payment;
    List<Article> articles;

    public Bill(String date, String id, double total, String payment, List<Article> articles) {
        this.date = date;
        this.id = id;
        this.total = total;
        this.payment = payment;
        this.articles = articles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
