import java.util.List;

public class Product {
    private String name;
    private float price;

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String serialise() {
        var sb = new StringBuilder();
        sb.append(this.name).append('\n').append(this.price);
        return sb.toString();
    }

    public static Product deserialise(List<String> data) {
        return new Product(data.get(0), Float.parseFloat(data.get(1)));
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
