package pl.bydgoszcz.pensions.calculator


class App(val args: Array<String>) {
    fun start() {
        Thread { }.start()
    }
}

fun main(args: Array<String>) {
    App(args).start()
}