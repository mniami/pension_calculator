package pl.bydgoszcz.pensions.calculator

import java.math.MathContext

/**
 * Monthly contribute your savings to the wallet. Like putting your savings money on the account.
 */
class SavingsInvestment(private val wallet: InvestmentWallet, private val contributionPerMonth: Money) : Investment {
    override fun process(wallet: InvestmentWallet, time: Time, market: Market, mathContext: MathContext, financialRegulations: FinancialRegulations) {
        wallet.add(contributionPerMonth)
    }
}