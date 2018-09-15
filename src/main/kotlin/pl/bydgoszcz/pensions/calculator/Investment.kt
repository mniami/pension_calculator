package pl.bydgoszcz.pensions.calculator

import java.math.MathContext

interface Investment {
    fun process(wallet: InvestmentWallet, time: Time, market: Market, mathContext: MathContext, financialRegulations: FinancialRegulations): InvestmentReport
}