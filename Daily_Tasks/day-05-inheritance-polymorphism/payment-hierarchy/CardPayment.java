public class CardPayment extends Payment implements Refundable {

    public CardPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.println("Paid INR " + amount + " using Card.");
    }
    @Override
public void refund() {
    System.out.println("Refund processed for Card payment: INR " + amount);
}
}