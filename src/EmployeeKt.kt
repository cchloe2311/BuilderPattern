class EmployeeKt (
    private val id: String,
    private val name: String,
    private val birthYear: Int? = null,
    private val marriedStatus: Boolean? = null
) {
    private constructor(builder: Builder) : this(builder.id, builder.name, builder.birthYear, builder.marriedStatus)

    companion object {
        fun build(id: String, name: String, block: Builder.() -> Unit) = Builder(id, name).apply(block).build()
        /**
         * 왜 companion object 사용?
         * - 클래스 내부에 정의된 Singleton value
         *   -> 클래스 이름으로 호출할 수 있음
         *   => 팩토리 메서드 구현 시 적합함
         *
         * +) Top-level로 구현하지 않고? -> 의미가 직관적이지 않음
         * */
    }

    class Builder (
        val id: String,
        val name: String
    ) {
        var birthYear: Int? = null
        var marriedStatus: Boolean? = null

        fun build() = EmployeeKt(this)
    }

    override fun toString(): String
        = "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthYear=" + (birthYear ?: "null") +
                ", marriedStatus=" + (marriedStatus ?: "null") +
                '}'
}

@Volatile var employeeKt: EmployeeKt = EmployeeKt.build("0", "init") {}
fun main() {
    val t1 = Thread(Runnable {
        employeeKt = EmployeeKt.build("1", "chloe") {birthYear = 1996}
    })

    val t2 = Thread(Runnable {
        employeeKt = EmployeeKt.build("2", "choi") {
            birthYear = 1997
            marriedStatus = false
        }
    })

    val t3 = Thread(Runnable {
        employeeKt = EmployeeKt(id = "3", name = "namedArg")
    })

    t1.start()
    t2.start()
    t3.start()

    for (i in 0..10) {
        println(employeeKt.toString())
    }
}