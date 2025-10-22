package drinks

import reisiegel.jan.Drinks.Beer
import reisiegel.jan.Drinks.Caffe
import reisiegel.jan.Drinks.EmptyBottle
import reisiegel.jan.Drinks.Tea


class CustomDrink private constructor(builder: Builder){
    private var drinkType: String
    private var milk: Boolean
    private var sugar: Boolean
    private var caramel: Boolean
    private var honey: Boolean
    private var cinnamon: Boolean

    init {
        drinkType = builder.drinkType
        milk = builder.milk
        sugar = builder.sugar
        caramel = builder.caramel
        honey = builder.honey
        cinnamon = builder.cinnamon
    }

    open class Builder constructor(val drinkType: String){
        var milk: Boolean = false
        var sugar: Boolean = false
        var caramel: Boolean = false
        var honey: Boolean = false
        var cinnamon: Boolean = false

        fun milk(): Builder{
            milk = true
            return this
        }

        fun sugar(): Builder{
            sugar = true
            return this
        }
        fun caramel(): Builder{
            caramel = true
            return this
        }
        fun honey(): Builder{
            honey = true
            return this
        }
        fun cinnamon(): Builder{
            cinnamon = true
            return this
        }

        fun build(): CustomDrink{
            return CustomDrink(this)
        }
    }

    fun createMessage(): String{
        val drink: Drink = when(drinkType){
            "tea" -> Tea()
            "caffe" -> Caffe()
            "beer" -> Beer()
            else -> EmptyBottle()
        }
        return (drink.create()
                + (if (milk) " with milk" else "")
                + (if (sugar) " with sugar" else "")
                + (if (caramel) " with caramel" else "")
                + (if (honey) " with honey" else "")
                + (if (cinnamon) " with cinnamon" else ""))
    }

    override fun toString(): String{
        val drink: Drink = when(drinkType){
            "tea" -> Tea()
            "caffe" -> Caffe()
            "beer" -> Beer()
            else -> EmptyBottle()
        }
        return (drink.serve()
                + (if (milk) " with milk" else "")
                + (if (sugar) " with sugar" else "")
                + (if (caramel) " with caramel" else "")
                + (if (honey) " with honey" else "")
                + (if (cinnamon) " with cinnamon" else ""))
    }
}