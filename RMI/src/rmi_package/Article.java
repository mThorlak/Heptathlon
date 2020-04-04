package rmi_package;

public class Article implements java.io.Serializable {

    private int Reference, Stock;
    private float Price;
    private String Family;

    public int getReference() {
        return Reference;
    }

    public void setReference(int reference) {
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

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }
}
