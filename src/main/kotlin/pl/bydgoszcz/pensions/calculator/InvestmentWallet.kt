package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal

class InvestmentWallet(private val capitalValue: Money = BigDecimal.ZERO) {
    fun add(amount: Money) {
        capitalValue.add(amount)
    }

    val capital: Money
        get() {
            return capitalValue
        }
}