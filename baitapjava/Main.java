import java.util.*;

// ========== Interface PaymentMethod ==========
interface PaymentMethod {
    void pay(double amount);
}

// ========== Các phương thức thanh toán ==========
class CashPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán tiền mặt thành công. Số tiền: " + amount + " VND.");
    }
}

class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán bằng thẻ tín dụng thành công. Số tiền: " + amount + " VND.");
    }
}

class MomoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán bằng ví Momo thành công. Số tiền: " + amount + " VND.");
    }
}

// ========== Lớp trừu tượng Product ==========
abstract class Product {
    protected String id;
    protected String name;
    protected double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getInfo();
}

// ========== Các loại sản phẩm cụ thể ==========
class ElectronicProduct extends Product {
    private String imei;
    private int warrantyMonths;

    public ElectronicProduct(String id, String name, double price, String imei, int warrantyMonths) {
        super(id, name, price);
        this.imei = imei;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getInfo() {
        return String.format("Điện tử: %s - IMEI: %s, BH: %d tháng, Giá: %.0f VND",
                name, imei, warrantyMonths, price);
    }
}

class FoodProduct extends Product {
    private String expiryDate;

    public FoodProduct(String id, String name, double price, String expiryDate) {
        super(id, name, price);
        this.expiryDate = expiryDate;
    }

    @Override
    public String getInfo() {
        return String.format("Thực phẩm: %s - HSD: %s, Giá: %.0f VND", name, expiryDate, price);
    }
}

// ========== Lớp Order ==========
class Order {
    private String customerName;
    private List<Product> products;
    private PaymentMethod paymentMethod;

    public Order(String customerName) {
        this.customerName = customerName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }

    public void setPaymentMethod(PaymentMethod pm) {
        this.paymentMethod = pm;
    }

    public void checkout() {
        double total = calculateTotal();
        System.out.println("=================================");
        System.out.println("Khách hàng: " + customerName);
        System.out.println("Danh sách sản phẩm:");
        for (Product p : products) {
            System.out.println(" - " + p.getInfo());
        }
        System.out.println("Tổng tiền: " + total + " VND.");
        if (paymentMethod != null) {
            paymentMethod.pay(total);
        } else {
            System.out.println("Chưa chọn phương thức thanh toán!");
        }
        System.out.println("=================================\n");
    }
}

// ========== Lớp Main (Demo) ==========
public class Main {
    public static void main(String[] args) {
        // Tạo danh sách sản phẩm
        Product sp1 = new ElectronicProduct("E001", "Điện thoại iPhone 15", 25000000, "IMEI12345", 12);
        Product sp2 = new FoodProduct("F001", "Sữa tươi Vinamilk", 30000, "20/10/2025");
        Product sp3 = new ElectronicProduct("E002", "Laptop ASUS", 18000000, "IMEI67890", 24);

        // Đơn hàng 1 - Thanh toán tiền mặt
        Order order1 = new Order("Nguyễn Văn A");
        order1.addProduct(sp1);
        order1.addProduct(sp2);
        order1.setPaymentMethod(new CashPayment());
        order1.checkout();

        // Đơn hàng 2 - Thanh toán thẻ tín dụng
        Order order2 = new Order("Nguyễn Văn B");
        order2.addProduct(sp2);
        order2.addProduct(sp3);
        order2.setPaymentMethod(new CreditCardPayment());
        order2.checkout();

        // Đơn hàng 3 - Thanh toán qua Momo
        Order order3 = new Order("Nguyễn Văn C");
        order3.addProduct(sp1);
        order3.addProduct(sp3);
        order3.setPaymentMethod(new MomoPayment());
        order3.checkout();
    }
}
