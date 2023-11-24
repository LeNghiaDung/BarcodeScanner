package ninh.main.mybarcodescanner;

public class Product {
    private String seri;
    private String nameProduct;
    private int quantity;
    private String date;

    public Product(String seri, String nameProduct, int quantity,String date) {
        this.seri = seri;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
