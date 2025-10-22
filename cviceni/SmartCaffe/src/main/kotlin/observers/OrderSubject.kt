package observers

abstract class OrderSubject {
    protected var observers : MutableList<IObserver> = mutableListOf()
    abstract fun addObserver(observer : IObserver)
    abstract fun removeObserver(observer : IObserver)
    abstract fun notifyAll(status: String)
}