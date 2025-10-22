package observers

class EmployeeObserver(var name: String): IObserver {
    override fun update(status: String) {
        println("Employee $name notified: $status")
    }
}