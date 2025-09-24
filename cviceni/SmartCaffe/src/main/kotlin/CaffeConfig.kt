package reisiegel.jan

class CaffeConfig private constructor(name: String) {
    private var caffeName: String

    companion object{
        @Volatile
        private var instance: CaffeConfig? = null

        fun getInstance(newName: String) = instance ?: synchronized(this){
            instance?: CaffeConfig(newName).also { instance = it }
        }

        fun deleteInstace(){
            instance = null
        }
    }

    init {
        caffeName = name
    }

    fun getCaffeName(): String{
        return caffeName
    }
}