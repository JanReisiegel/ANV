package commands

import observers.IObserver
import observers.OrderSubject
import payments.Checkout
import payments.IPaymentStrategy

class PaymentCommand(
    var checkouts: List<IObserver>,
    val checkout: Checkout,
    val amount: Double,
    val tableNumber: Int
) : ICommand {
    override fun execute() {
        checkouts.forEach { it.update(checkout.accessPayment(amount, tableNumber)) }
    }
}