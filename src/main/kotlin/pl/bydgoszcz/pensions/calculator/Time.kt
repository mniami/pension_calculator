package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal

data class Time(var value: Int) {
    companion object {
        val Months = BigDecimal(12)
    }
}