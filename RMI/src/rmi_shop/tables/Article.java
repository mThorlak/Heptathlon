package rmi_shop.tables;

public class Article implements java.io.Serializable {

    private String Reference, Description;
    private float Price;
    private int Stock;

    public Article() { }

    public Article(String reference, float price, int quantity) {
        this.Reference = reference;
        this.Price = price;
        this.Stock = quantity;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
