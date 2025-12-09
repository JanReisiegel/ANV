package drinks

open class DrinkTemplate(var drinkName: String) : IPreparableDrink {
    override fun prepare() {
        println("[APP] Starting preparation: $drinkName");
        boilWater();
        brew();
        pour();
        println("[APP] Done: $drinkName");
    }

    fun boilWater() {
        println("[APP] Water preparation: $drinkName");
    }
    fun pour() {
        println("[APP] Pour preparation: $drinkName");
    }
    open fun brew(){
        println("[APP] Brewing preparation: $drinkName");
    }
}