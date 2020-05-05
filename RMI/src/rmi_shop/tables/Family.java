package rmi_shop.tables;

public class Family implements java.io.Serializable {

    private String Family, Reference;

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    @Override
    public String toString() {
        return "Family{" +
                "Family='" + Family + '\'' +
                ", Reference='" + Reference + '\'' +
                '}';
    }
}
