package reisiegel.jan.Drinks

import drinks.Drink
import drinks.DrinkTemplate

class EmptyBottle(drinkName: String): Drink, DrinkTemplate(drinkName) {
    override fun serve(): String {
        return "Serving empty bottle"
    }
    override fun create(): String {
        return "Created empty bottle"
    }
}