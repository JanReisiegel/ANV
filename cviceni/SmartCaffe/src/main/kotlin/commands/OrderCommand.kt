package commands

import drinks.CustomDrink
import drinks.Drink
import observers.IObserver

class OrderCommand(
    val order: List<IObserver>,
    val drink: CustomDrink,
    val caffeName: String
) : ICommand {
    override fun execute() {
        order.forEach { it.update("${drink.createMessage()} in $caffeName") }
    }
}