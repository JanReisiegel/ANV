// 1. Abstract Expression
// Základní rozhraní pro všechny uzly gramatického stromu
interface Expression {
    fun interpret(): Int
}

// 2. Terminal Expression
// Reprezentuje nejmenší jednotku (číslo), která se už dál nerozkládá
class NumberExpression(private val number: Int) : Expression {
    override fun interpret(): Int = number
}

// 3. Non-terminal Expressions
// Reprezentují operace, které v sobě drží další výrazy (vlevo a vpravo)

class AddExpression(private val left: Expression, private val right: Expression) : Expression {
    override fun interpret(): Int = left.interpret() + right.interpret()
}

class SubtractExpression(private val left: Expression, private val right: Expression) : Expression {
    override fun interpret(): Int = left.interpret() - right.interpret()
}

// 4. Client
// Sestaví strom a spustí interpretaci
fun main() {
    // Chceme spočítat: (5 + 2) - 3

    // Ruční sestavení abstraktního syntaktického stromu (AST):
    //       Minus
    //      /     \
    //    Plus     3
    //   /    \
    //  5      2

    val expression: Expression = SubtractExpression(
        AddExpression(NumberExpression(5), NumberExpression(2)),
        NumberExpression(3)
    )

    val result = expression.interpret()
    println("Výsledek výrazu (5 + 2) - 3 je: $result")
}