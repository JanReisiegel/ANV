package reisiegel.jan.Drinks

import drinks.Drink
import drinks.DrinkTemplate

class Beer(drinkName: String): Drink, DrinkTemplate(drinkName) {
    override fun serve(): String {
        return "Serving beer"
    }

    override fun create(): String {
        return "Created beer"
    }
}