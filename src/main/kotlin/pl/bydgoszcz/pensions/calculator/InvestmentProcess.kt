package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal
import java.math.MathContext

class InvestmentProcess(private val parameters: InvestmentParameters) {
    fun process() {
        val mathContext = MathContext(4)
        val taxPercentNormalized = BigDecimal.ONE - parameters.taxForIncome.divide(Money(100), mathContext)

        val time = Time(0)
        val market = Market()

        val wallet = InvestmentWallet(parameters.initialCapital)
        val savingsInvestment = SavingsInvestment(wallet, parameters.monthContribution)
        val financialRegulations = FinancialRegulations(taxPercentNormalized)
        val tfiInvestment = BankDepositInvestment(parameters.bankDepositParameters)

        val steps: List<Investment> = listOf(savingsInvestment, tfiInvestment)

        while (time.value < parameters.monthsCount) {
            steps.forEach { step ->
                step.process(wallet, time, market, mathContext, financialRegulations)
            }
            time.value++
        }
    }
}