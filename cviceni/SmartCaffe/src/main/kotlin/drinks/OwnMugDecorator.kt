package drinks

class OwnMugDecorator(customDrink: CustomDrink): CustomDrinkDecorator(customDrink) {
    override fun toString(): String {
        return "$customDrink[own mug]"
    }
}