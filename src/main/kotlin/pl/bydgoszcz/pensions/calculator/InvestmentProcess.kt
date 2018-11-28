package pl.bydgoszcz.pensions.calculator

class InvestmentProcess(private val parameters: InvestmentParameters) {
    fun process(): InvestmentResult {
        val taxPercentNormalized = Money.ONE - parameters.taxForIncome.toPercent()

        var time = Time(0)
        val market = Market()

        val wallet = InvestmentWallet(parameters.initialCapital)
        val savings = SavingsInvestment(parameters.monthContribution)
        val financialRegulations = FinancialRegulations(taxPercentNormalized)
        val bankDeposit = BankDepositInvestment(parameters.bankDepositParameters)

        val investmentSteps: List<Investment> = listOf(savings, bankDeposit)
        val reports: MutableList<InvestmentReport> = mutableListOf()

        while (time.value < parameters.monthsCount) {
            investmentSteps.forEach { step ->
                reports.add(step.process(wallet, time, market, financialRegulations))
            }
            time = Time(time.value + 1)
        }
        val bankDepositReport = reports.last { it is BankDepositReport } as BankDepositReport?
        val investGain = bankDepositReport?.investGain ?: Money(0)

        //TODO refactor investment result, to work with reports
        return InvestmentResult(wallet.getCapital(SavingsInvestment.SAVINGS_WALLET_GROUP), investGain, wallet.getTotalCapital(), wallet)
    }
}