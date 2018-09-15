package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal

class InvestmentWallet(val initialCapital: Money = BigDecimal.ZERO) {
    private val capitalValue: Money = Money(initialCapital.longValueExact())

    fun addCapital(amount: Money): InvestmentAction {
        val oldValue = capitalValue
        capitalValue.add(amount)
        return CapitalValueIncreased(oldValue, capitalValue)
    }

    val contributedCapital: Money
        get() {
            return capitalValue.minus(initialCapital)
        }

    val capital: Money
        get() {
            return capitalValue
        }


    class CapitalValueIncreased(val oldValue: Money, val capitalValue: Money) : InvestmentAction
}