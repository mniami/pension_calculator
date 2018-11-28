package pl.bydgoszcz.pensions.calculator

class BankDepositInvestment(var bankDepositParameters: BankDepositParameters) : Investment {
    companion object {
        val DEPOSIT_WALLET_GROUP = WalletGroup("Deposit wallet group")
    }

    override fun process(wallet: InvestmentWallet, time: Time, market: Market, financialRegulations: FinancialRegulations): InvestmentReport {
        val capitalMargin = bankDepositParameters.capitalMarginPercent.toPercent()
        val rateOfReturnNormalized = bankDepositParameters.rateOfReturnPercent.toPercent()
        var investGain = wallet.getTotalCapital() * rateOfReturnNormalized
        // Fee or costs of the
        var margin = investGain / capitalMargin
        val minMargin = Money(0)

        if (margin < minMargin) {
            margin = minMargin
        }
        investGain -= margin

        var gainAfterTax = investGain * financialRegulations.taxPercent
        if (gainAfterTax < Money.ZERO) {
            gainAfterTax = Money.ZERO
        }
        val netGain = gainAfterTax
        val paidTax = investGain.minus(gainAfterTax)
        val actionResult = wallet.addCapital(netGain, DEPOSIT_WALLET_GROUP)

        return BankDepositReport(investGain, netGain, paidTax, margin, time, actionResult)
    }
}
