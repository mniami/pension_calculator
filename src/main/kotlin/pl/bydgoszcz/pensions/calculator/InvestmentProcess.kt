package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal
import java.math.MathContext

class InvestmentProcess(private val parameters: InvestmentParameters) {
    fun process(): InvestmentResult {
        val mathContext = MathContext(4)
        val taxPercentNormalized = BigDecimal.ONE - parameters.taxForIncome.divide(Money(100), mathContext)

        val time = Time(0)
        val market = Market()

        val wallet = InvestmentWallet(parameters.initialCapital)
        val savings = SavingsInvestment(wallet, parameters.monthContribution)
        val financialRegulations = FinancialRegulations(taxPercentNormalized)
        val bankDeposit = BankDepositInvestment(parameters.bankDepositParameters)

        val steps: List<Investment> = listOf(savings, bankDeposit)
        val reports: MutableList<InvestmentReport> = mutableListOf()

        while (time.value < parameters.monthsCount) {
            steps.forEach { step ->
                reports.add(step.process(wallet, time, market, mathContext, financialRegulations))
            }
            time.value++
        }
        val bankDepositReport = reports.last { it is BankDepositReport } as BankDepositReport?
        val investGain = bankDepositReport?.investGain ?: Money(0)

        //TODO refactor investment result, to work with reports
        return InvestmentResult(wallet.contributedCapital, investGain, wallet.capital)
    }
}