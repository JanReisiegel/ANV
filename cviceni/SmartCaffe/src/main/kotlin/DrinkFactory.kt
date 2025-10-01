package reisiegel.jan

import reisiegel.jan.Drinks.Drink

abstract class DrinkFactory {
    protected abstract fun createDrink(type: String): Drink;
    open fun serveDrink(type: String): String{
        return createDrink(type).Serve()
    }
}