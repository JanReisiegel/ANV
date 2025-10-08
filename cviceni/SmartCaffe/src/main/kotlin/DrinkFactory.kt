import drinks.CustomDrink

abstract class DrinkFactory {
    abstract fun createDrink(type: String, milk: Boolean, sugar: Boolean, caramel: Boolean, honey: Boolean, cinnamon: Boolean): CustomDrink
    open fun serveDrink(type: String, milk: Boolean = false, sugar: Boolean = false, caramel: Boolean = false, honey: Boolean = false, cinnamon: Boolean = false): String{
        throw NotImplementedError()
    }
}