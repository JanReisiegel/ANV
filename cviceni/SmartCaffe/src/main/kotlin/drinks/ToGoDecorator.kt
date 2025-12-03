package drinks

class ToGoDecorator(customDrink: CustomDrink): CustomDrinkDecorator(customDrink) {
    override fun toString(): String {
        return "$customDrink[to-go]"
    }
}