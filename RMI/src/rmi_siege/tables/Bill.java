package rmi_siege.tables;

import rmi_shop.tables.Article;

import java.sql.Timestamp;
import java.util.List;

public class Bill implements java.io.Serializable {

    private String date;
    private String id;
    private String shop;
    private float total;
    private String payment;
    List<Article> articles;
    private boolean paid;

    public Bill(String date, String shop, float total, String payment, List<Article> articles) {
        this.date = date;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.id = "shop1:" + ts.getTime();
        this.shop = shop;
        this.total = total;
        this.payment = payment;
        this.articles = articles;
    }

    public Bill(String date, String id, String shop, float total, String payment, List<Article> articles) {
        this.date = date;
        this.id = id;
        this.shop = shop;
        this.total = total;
        this.payment = payment;
        this.articles = articles;
    }

    public Bill(String date, String id, String shop, float total, String payment, List<Article> articles, boolean paid) {
        this.date = date;
        this.id = id;
        this.shop = shop;
        this.total = total;
        this.payment = payment;
        this.articles = articles;
        this.paid = paid;
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

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", shop='" + shop + '\'' +
                ", total=" + total +
                ", payment='" + payment + '\'' +
                ", articles=" + articles +
                ", paid=" + paid +
                '}';
    }
}
