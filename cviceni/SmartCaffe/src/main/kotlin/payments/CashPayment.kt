package payments

class CashPayment: IPaymentStrategy {
    override fun pay(amount: Double, tableNumber: Int): String {
        return "Customer, on table $tableNumber, want to pay ${String.format("%.2f", amount)} using cash."
    }
}