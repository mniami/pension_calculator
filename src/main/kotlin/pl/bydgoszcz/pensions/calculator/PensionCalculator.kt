package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal

typealias Money = BigDecimal
typealias PercentValue = BigDecimal

class PensionCalculator {
    fun calculate(contributionPerMonth: Money,
                  investmentTime: InvestmentTime,
                  rateOfReturn: Money,
                  taxPercent: Money = Money(19),
                  capitalMarginPercent: Money = Money(0.1)): InvestmentResult {
        val bankDepositParameters = BankDepositParameters(rateOfReturn, capitalMarginPercent)
        val parameters = InvestmentParameters(
                contributionPerMonth,
                Money(0),
                investmentTime.months,
                taxPercent,
                bankDepositParameters)
        val process = InvestmentProcess(parameters)

        return process.process()
    }
}