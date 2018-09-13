package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.NumberFormat

typealias Money = BigDecimal
typealias PercentValue = BigDecimal

class PensionCalculator {
    private val monthsInYear = 12

    fun calculate(contributionPerMonth: Money,
                  investmentTime: InvestmentTime,
                  rateOfReturn: Money,
                  taxPercent: Money = Money(19),
                  capitalMarginPercent: Money = Money(0.1)): InvestmentResult {


        val minMargin = Money(60)
        var capital = Money.ZERO
        val mathContext = MathContext(4, RoundingMode.HALF_EVEN)
        val contribution = contributionPerMonth
        val taxPercentNormalized = Money.ONE - taxPercent.divide(Money(100), mathContext)
        val capitalMargin = capitalMarginPercent.divide(Money(100), mathContext)
        val rateOfReturnNormalized = rateOfReturn.divide(Money(100), mathContext)
        var payedCapital = Money.ZERO
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

            var gainAfterTax = investGain.multiply(taxPercentNormalized, mathContext)
            if (gainAfterTax < Money.ZERO) {
                gainAfterTax = Money.ZERO
            }
            val netGain = gainAfterTax

            capital += netGain
            println(String.format("%s\t\t%s", year, formatter.format(capital)))
        }

        return InvestmentResult(payedCapital, capital - payedCapital, capital)
    }

    fun play(contributionPerMonth: Money,
             investmentTime: InvestmentTime,
             rateOfReturn: Money,
             taxPercent: Money = Money(19),
             capitalMarginPercent: Money = Money(0.1)) {
        val bankDepositParameters = BankDepositParameters(rateOfReturn, capitalMarginPercent)
        val parameters = InvestmentParameters(
                contributionPerMonth,
                Money(0),
                investmentTime.months,
                taxPercent,
                bankDepositParameters)
        val process = InvestmentProcess(parameters)

        process.process()
    }
}