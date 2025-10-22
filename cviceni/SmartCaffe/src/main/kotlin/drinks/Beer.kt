package reisiegel.jan.Drinks

import drinks.Drink

class Beer: Drink {
    override fun serve(): String {
        return "Serving beer"
    }

    override fun create(): String {
        return "Created beer"
    }
}