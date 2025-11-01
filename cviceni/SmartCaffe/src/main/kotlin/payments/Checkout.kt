package payments

class Checkout(private var strategy: IPaymentStrategy) {
    fun setStrategy(newStrategy: IPaymentStrategy){
        strategy = newStrategy
    }

    fun accessPayment(amount: Double, table: Int) : String {
        return strategy.pay(amount, table)
    }
}