import drinks.CustomDrink
import observers.OrderSubject

abstract class DrinkFactory: OrderSubject() {
    abstract fun createDrink(type: String, milk: Boolean = false, sugar: Boolean = false, caramel: Boolean = false, honey: Boolean = false, cinnamon: Boolean = false): CustomDrink
    open fun serveDrink(type: String, milk: Boolean = false, sugar: Boolean = false, caramel: Boolean = false, honey: Boolean = false, cinnamon: Boolean = false): String{
        throw NotImplementedError()
    }
}