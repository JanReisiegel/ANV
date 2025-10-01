package reisiegel.jan.Drinks

import drinks.Drink

class Tea: Drink {
    override fun serve(): String {
        return "Serving tea"
    }
}