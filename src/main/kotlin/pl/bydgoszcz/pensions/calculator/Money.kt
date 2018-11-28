package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal
import java.math.MathContext

typealias PercentValue = Money

val MATH_CONTEXT = MathContext.DECIMAL64

class Money {
    companion object {
        val HOUNDRED = Money(100)
        val ZERO: Money = Money(0)
        val ONE: Money = Money(1)
    }

    val value: BigDecimal

    constructor(value: BigDecimal) {
        this.value = value
    }

    constructor(value: Double) {
        this.value = BigDecimal(value, MATH_CONTEXT)
    }

    constructor(value: Int) {
        this.value = BigDecimal(value, MATH_CONTEXT)
    }

    operator fun plus(money: Money): Money {
        return Money(this.value + money.value)
    }

    operator fun minus(money: Money): Money {
        return Money(this.value - money.value)
    }

    operator fun div(money: Money): Money {
        return Money(this.value.divide(money.value, MATH_CONTEXT))
    }

    operator fun times(money: Money): Money {
        return Money(this.value.multiply(money.value))
    }

    fun toPercent(): Money {
        return this / HOUNDRED
    }

    operator fun compareTo(money: Money): Int {
        return this.value.compareTo(money.value)
    }

    operator fun compareTo(bigDecimal: BigDecimal): Int {
        return this.value.compareTo(bigDecimal)
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (value.compareTo(other.value) != 0) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}