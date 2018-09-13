package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.NumberFormat

class PensionCalculator {
    fun calculate(monthContribution: BigDecimal, investmentTime: InvestmentTime, rateOfReturn: BigDecimal): InvestmentResult {

        val minMargin = BigDecimal(60)
        var capital = BigDecimal.ZERO
        val mathContext = MathContext(4, RoundingMode.HALF_EVEN)
        val contribution = monthContribution.multiply(BigDecimal(12), mathContext)
        val taxPercantage = BigDecimal.ONE - BigDecimal(19).divide(BigDecimal(100), mathContext)
        val capitalMargin = BigDecimal(1).divide(BigDecimal(1000), mathContext)
        val rateOfReturnNormalized = rateOfReturn.divide(BigDecimal(100), mathContext)
        var payedCapital = BigDecimal.ZERO
        val formatter = NumberFormat.getCurrencyInstance()

        println("YEAR\tCAPITAL\n" +
                "---------------")
        for (year in 0..investmentTime.getYears()) {
            payedCapital += contribution
            capital += contribution

            var investGain = capital.multiply(rateOfReturnNormalized, mathContext)
            var margin = investGain.multiply(capitalMargin, mathContext)
            if (margin < minMargin) {
                margin = minMargin
            }
            investGain -= margin
            var gainAfterTax = investGain.multiply(taxPercantage, mathContext)
            if (gainAfterTax < BigDecimal.ZERO) {
                gainAfterTax = BigDecimal.ZERO
            }
            var netGain = gainAfterTax

            capital += netGain
            println(String.format("%s\t\t%s", year, formatter.format(capital)))
        }

        return InvestmentResult(payedCapital, capital - payedCapital, capital)
    }
}

class InvestmentTime(var months: Int = 0) {
    fun getYears(): Int = months / 12
    fun applyFromYears(years: Int): InvestmentTime {
        months = years * 12
        return this
    }
}

data class InvestmentResult(val payedCapital: BigDecimal, val investmentGain: BigDecimal, val totalCapital: BigDecimal)