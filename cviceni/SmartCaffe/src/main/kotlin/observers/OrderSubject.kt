package observers

abstract class OrderSubject {
    protected var observers : MutableList<IObserver> = mutableListOf()
    protected var checkoutObservers : MutableList<IObserver> = mutableListOf()
    abstract fun addObserver(observer : IObserver)
    abstract fun addCheckoutObserver(observer: IObserver)
    abstract fun removeObserver(observer : IObserver)
    abstract fun removeCheckoutObserver(observer : IObserver)
}