package rmi_siege.tables;

public class FamilySiege implements java.io.Serializable {

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
}
