package pl.bydgoszcz.pensions.calculator

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import pl.bydgoszcz.pensions.calculator.InvestmentWallet.Companion.MAIN_WALLET_GROUP
import java.math.BigDecimal

class InvestmentWalletTest {

    @Test
    fun testGetCapitals() {
        assertEquals(InvestmentWallet(Money(1000)).getTotalCapital().value, BigDecimal(1000))
    }

    @Test
    fun testAddCapital() {
        val wallet = InvestmentWallet(Money(1000))
        wallet.addCapital(Money.HOUNDRED, InvestmentWallet.MAIN_WALLET_GROUP)
        assertEquals(wallet.getTotalCapital().value, BigDecimal(1100))

        wallet.addCapital(Money(100.0), InvestmentWallet.MAIN_WALLET_GROUP)
        assertEquals(wallet.getTotalCapital().value, BigDecimal(1200))

        wallet.addCapital(Money(BigDecimal(100)), InvestmentWallet.MAIN_WALLET_GROUP)
        assertEquals(wallet.getTotalCapital().value, BigDecimal(1300))
    }

    @Test
    fun testGetCapital() {
        val wallet = InvestmentWallet(Money(1000))
        assertEquals(wallet.getCapital(MAIN_WALLET_GROUP).value, BigDecimal(1000))
    }
}