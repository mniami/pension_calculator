package pl.bydgoszcz.pensions.calculator

interface Investment {
    fun process(wallet: InvestmentWallet, time: Time, market: Market, financialRegulations: FinancialRegulations): InvestmentReport
}