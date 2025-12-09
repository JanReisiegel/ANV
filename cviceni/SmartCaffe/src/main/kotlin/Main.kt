package reisiegel.jan

import commands.ICommand
import commands.OrderCommand
import drinks.DrinkOrder
import observers.EmployeeObserver
import observers.IObserver
import payments.CashPayment
import payments.CreditPayment
import reisiegel.jan.Drinks.Caffe
import reisiegel.jan.Drinks.Tea

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    System.setOut(java.io.PrintStream(System.out, true, Charsets.UTF_8))

    val instance = CaffeConfig.getInstance("Testovací kavárna")
    println(instance.getCaffeName())
    instance.addObserver(EmployeeObserver("Barista"))
    instance.addObserver(EmployeeObserver("Waiter"))
    println(instance.serveDrink("tea", milk = true))
    println(instance.serveDrink("caffe", milk = true, sugar = true, toGo = true, ownMug = true))
    println(instance.serveDrink("beer", cinnamon = true))
    println(instance.serveDrink("whiskey"))
    instance.addCheckoutObserver(EmployeeObserver("Waiter"))

    instance.checkout(CreditPayment(), 159.55,1)
    instance.checkout(CashPayment(), 95.12,3)

    val mobileOrder: DrinkOrder = DrinkOrder()
    mobileOrder.add(Caffe("Jeans Caffe"))
    mobileOrder.add(Tea("Jeans Tea"))

    mobileOrder.prepare()
}