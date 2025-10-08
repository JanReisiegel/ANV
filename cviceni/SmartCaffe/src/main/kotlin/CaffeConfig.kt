package reisiegel.jan

import DrinkFactory
import drinks.CustomDrink
import reisiegel.jan.Drinks.Beer
import reisiegel.jan.Drinks.Caffe
import drinks.Drink
import reisiegel.jan.Drinks.EmptyBottle
import reisiegel.jan.Drinks.Tea

class CaffeConfig private constructor(name: String): DrinkFactory() {
    private var caffeName: String = name

    companion object{
        @Volatile
        private var instance: CaffeConfig? = null

        fun getInstance(newName: String) = instance ?: synchronized(this){
            instance?: CaffeConfig(newName).also { instance = it }
        }

        fun deleteInstance(){
            instance = null
        }

    }

    fun getCaffeName(): String{
        return caffeName
    }

    override fun serveDrink(type: String, milk: Boolean , sugar: Boolean, caramel: Boolean, honey: Boolean, cinnamon: Boolean): String{
        return "${createDrink(type, milk, sugar, caramel, honey, cinnamon)} in $caffeName"
    }

    override fun createDrink(type: String, milk: Boolean, sugar: Boolean, caramel: Boolean, honey: Boolean, cinnamon: Boolean): CustomDrink {
        val drinkBuilder = CustomDrink.Builder(type)
        if (milk)
            drinkBuilder.milk()
        if (sugar)
            drinkBuilder.sugar()
        if (caramel)
            drinkBuilder.caramel()
        if (honey)
            drinkBuilder.honey()
        if (cinnamon)
            drinkBuilder.cinnamon()
        return drinkBuilder.build()
    }
}