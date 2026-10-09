public class UpiPayment extends Payment {

    public UpiPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.println("Paid INR " + amount + " using UPI.");
    }
}