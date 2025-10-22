package reisiegel.jan.Drinks

import drinks.Drink

class Caffe: Drink {
    override fun serve(): String {
        return "Serving caffe"
    }
    override fun create(): String {
        return "Created caffe"
    }
}