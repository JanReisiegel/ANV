package drinks

open class CustomDrinkDecorator(val customDrink: CustomDrink): CustomDrink(customDrink.builder) {
}