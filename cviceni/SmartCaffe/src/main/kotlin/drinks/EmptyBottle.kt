package reisiegel.jan.Drinks

import drinks.Drink

class EmptyBottle: Drink {
    override fun serve(): String {
        return "Serving empty bottle"
    }
}