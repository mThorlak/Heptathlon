package rmi_package;

import java.util.List;

public class Bill {

    private String date;
    private double total;
    private String payment;
    List<Article> articles;

    public Bill(String date, double total, String payment, List<Article> articles) {
        this.date = date;
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
