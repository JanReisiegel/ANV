package reisiegel.jan.Drinks

import drinks.Drink
import drinks.DrinkTemplate

class Tea(drinkName: String): Drink, DrinkTemplate(drinkName) {
    override fun serve(): String {
        return "Serving tea"
    }
    override fun create(): String {
        return "Created tea"
    }
}