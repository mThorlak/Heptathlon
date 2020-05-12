package rmi_siege.tables;

public class ArticleSiege implements java.io.Serializable {

    private String Reference, Description;
    private float Price;

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
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

    @Override
    public String toString() {
        return "Article siege : " +
                " Reference = '" + Reference + '\'' +
                ", Description = '" + Description + '\'' +
                ", Price = " + Price;
    }
}
