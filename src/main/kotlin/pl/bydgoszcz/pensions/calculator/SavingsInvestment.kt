package pl.bydgoszcz.pensions.calculator

/**
 * Monthly contribute your savings to the wallet. Like putting your savings money on the account.
 */
class SavingsInvestment(private val contributionPerMonth: Money) : Investment {
    companion object {
        val SAVINGS_WALLET_GROUP = WalletGroup("Savings wallet group")
    }

    override fun process(wallet: InvestmentWallet, time: Time, market: Market, financialRegulations: FinancialRegulations): InvestmentReport {
        val increasedCapital = wallet.addCapital(contributionPerMonth, SAVINGS_WALLET_GROUP)
        return SavingsInvestmentReport(increasedCapital, time)
    }
}

data class SavingsInvestmentReport(val action: InvestmentAction, val time: Time) : InvestmentReport