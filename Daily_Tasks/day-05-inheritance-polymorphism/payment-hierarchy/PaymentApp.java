public class PaymentApp {

    public static void main(String[] args) {

        Payment[] payments = {
                new CardPayment(1500),
                new UpiPayment(800),
                new CashPayment(500)
        };

        System.out.println("===== RUNTIME POLYMORPHISM =====");

        for (Payment payment : payments) {
            payment.pay();
        }

        System.out.println("\n===== METHOD OVERLOADING =====");

        Payment payment = new CardPayment(2000);

        payment.pay();
        payment.pay("TXN-1001");
        payment.pay(2500, "TXN-1002");

        System.out.println("\n===== INTERFACE =====");

        Refundable refundable = new CardPayment(1200);
        refundable.refund();
    }
}