package payments

class ProxyCardPayment(private val autentificated: Boolean) : IPaymentStrategy {
    private val paymentMethod : IPaymentStrategy = CreditPayment()
    override fun pay(amount: Double, tableNumber: Int): String {
        if (!autentificated)
            return ("Access denied. Please log in first.")
        else
            return paymentMethod.pay(amount, tableNumber) + " Access Granted"
    }

}