package ninh.main.mybarcodescanner;

public class Product {
    private String seri;
    private String nameProduct;
    private int quantity;

    public Product(String seri, String nameProduct, int quantity) {
        this.seri = seri;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
