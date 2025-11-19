package reisiegel.jan

import DrinkFactory
import commands.ICommand
import commands.OrderCommand
import commands.PaymentCommand
import drinks.CustomDrink
import observers.IObserver
import payments.Checkout
import payments.CreditPayment
import payments.IPaymentStrategy

class CaffeConfig private constructor(name: String): DrinkFactory() {
    private var caffeName: String = name
    private val checkoutObject: Checkout = Checkout(CreditPayment())

    companion object{
        @Volatile
        private var instance: CaffeConfig? = null

        fun getInstance(newName: String) = instance ?: synchronized(this){
            instance?: CaffeConfig(newName).also { instance = it }
        }

        fun deleteInstance(){
            instance = null
        }

    }

    fun checkout(paymentStrategy: IPaymentStrategy, amount: Double, tableNumber: Int){
        checkoutObject.setStrategy(paymentStrategy)
        val paymentCommand = PaymentCommand(checkoutObservers.toList(), checkoutObject, amount, tableNumber)
        paymentCommand.execute()
    }

    fun getCaffeName(): String{
        return caffeName
    }

    override fun serveDrink(type: String, milk: Boolean , sugar: Boolean, caramel: Boolean, honey: Boolean, cinnamon: Boolean): String{
        return "${createDrink(type, milk, sugar, caramel, honey, cinnamon)} in $caffeName"
    }

    override fun createDrink(type: String, milk: Boolean, sugar: Boolean, caramel: Boolean, honey: Boolean, cinnamon: Boolean): CustomDrink {
        val drinkBuilder = CustomDrink.Builder(type)
        if (milk)
            drinkBuilder.milk()
        if (sugar)
            drinkBuilder.sugar()
        if (caramel)
            drinkBuilder.caramel()
        if (honey)
            drinkBuilder.honey()
        if (cinnamon)
            drinkBuilder.cinnamon()
        val drink: CustomDrink = drinkBuilder.build()
        val orderCommand = OrderCommand(observers.toList(), drink, caffeName)
        orderCommand.execute()
        return drink
    }

    override fun addObserver(observer: IObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: IObserver) {
        observers.remove(observer)
    }

    override fun addCheckoutObserver(observer: IObserver) {
        checkoutObservers.add(observer)
    }

    override fun removeCheckoutObserver(observer: IObserver) {
        checkoutObservers.remove(observer)
    }
}