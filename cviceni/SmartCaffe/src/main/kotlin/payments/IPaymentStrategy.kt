package payments

interface IPaymentStrategy {
    fun pay(amount: Double, tableNumber: Int): String
}