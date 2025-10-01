package reisiegel.jan.Drinks

import drinks.Drink

class Caffe: Drink {
    override fun serve(): String {
        return "Serving caffe"
    }
}