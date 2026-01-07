open class Animal{
    fun eat(){
        println("This animal eats food.")
    }
}

class Dog : Animal() {
    fun bark(){
        println("The dog barks.")
    }
}

fun main(){
    val myDog: Dog = Dog()
    myDog.eat()
    myDog.bark()
}