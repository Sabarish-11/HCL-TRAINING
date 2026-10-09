public abstract class Payment {

    protected double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    public abstract void pay();

    public void pay(String reference) {
        System.out.println("Payment reference: " + reference);
    }

    public void pay(double amount, String reference) {
        this.amount = amount;
        System.out.println("Payment of " + amount + " completed. Reference: " + reference);
    }

    public double getAmount() {
        return amount;
    }
}