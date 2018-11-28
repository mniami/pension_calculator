package pl.bydgoszcz.pensions.calculator

import org.testng.Assert.assertEquals
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test
import java.math.MathContext

class BankDepositInvestmentTest {
    lateinit var victim: BankDepositInvestment
    lateinit var wallet: InvestmentWallet
    lateinit var time: Time
    lateinit var market: Market
    lateinit var mathContext: MathContext
    lateinit var financialRegulations: FinancialRegulations

    @BeforeTest
    fun beforeTest() {
        mathContext = MathContext.DECIMAL32
        financialRegulations = FinancialRegulations(taxPercent = Money(0.2))
        market = Market()
        time = Time(1)
        wallet = InvestmentWallet(initialCapital = Money(1000))
        val params = BankDepositParameters(
                rateOfReturnPercent = PercentValue(0.02),
                capitalMarginPercent = PercentValue(0.02))
        victim = BankDepositInvestment(params)
    }

    @Test
    fun testProcess() {
        val report = victim.process(wallet, time, market, financialRegulations) as BankDepositReport
        assertEquals(report.investGain, Money(19.6), "Invest gain")
        assertEquals(report.tax, Money(3.92), "Invest gain")
        assertEquals(wallet.getTotalCapital(), Money(1015.68), "Total capital")
    }
}