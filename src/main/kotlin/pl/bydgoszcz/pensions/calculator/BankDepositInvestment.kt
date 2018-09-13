package pl.bydgoszcz.pensions.calculator

import java.math.BigDecimal
import java.math.MathContext

class BankDepositInvestment(var bankDepositParameters: BankDepositParameters) : Investment {
    override fun process(wallet: InvestmentWallet, time: Time, market: Market, mathContext: MathContext, financialRegulations: FinancialRegulations) {
        val capitalMargin = bankDepositParameters.capitalMarginPercent.divide(Money(100), mathContext)
        val rateOfReturnNormalized = bankDepositParameters.rateOfReturnPercent.divide(Money(100), mathContext)
        var investGain = wallet.capital.multiply(rateOfReturnNormalized, mathContext)
        var margin = investGain.multiply(capitalMargin, mathContext)
        val minMargin = Money(60)

        if (margin < minMargin) {
            margin = minMargin
        }
        investGain -= margin

        var gainAfterTax = investGain.multiply(financialRegulations.taxPercent, mathContext)
        if (gainAfterTax < BigDecimal.ZERO) {
            gainAfterTax = BigDecimal.ZERO
        }
        val netGain = gainAfterTax

        wallet.add(netGain)
    }
}