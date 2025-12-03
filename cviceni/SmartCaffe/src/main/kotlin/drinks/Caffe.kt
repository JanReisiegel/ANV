package reisiegel.jan.Drinks

import drinks.Drink
import drinks.DrinkTemplate

class Caffe(drinkName: String): Drink, DrinkTemplate(drinkName) {
    override fun serve(): String {
        return "Serving caffe"
    }
    override fun create(): String {
        return "Created caffe"
    }
}