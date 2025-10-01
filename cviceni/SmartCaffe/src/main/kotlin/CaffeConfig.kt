package reisiegel.jan

import reisiegel.jan.Drinks.Beer
import reisiegel.jan.Drinks.Caffe
import reisiegel.jan.Drinks.Drink
import reisiegel.jan.Drinks.EmptyBottle
import reisiegel.jan.Drinks.Tea

class CaffeConfig private constructor(name: String): DrinkFactory() {
    private var caffeName: String

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

    init {
        caffeName = name
    }

    fun getCaffeName(): String{
        return caffeName
    }

    override fun serveDrink(type: String): String{
        return "${createDrink(type).Serve()} in $caffeName"
    }

    override fun createDrink(type: String): Drink {
        return when(type){
            "tea" -> Tea()
            "caffe" -> Caffe()
            "beer" -> Beer()
            else -> EmptyBottle()
        }
    }
}