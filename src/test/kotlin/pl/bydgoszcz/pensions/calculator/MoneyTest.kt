package pl.bydgoszcz.pensions.calculator

import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class MoneyTest {

    @Test
    fun testPlus() {
        assertEquals(Money(4), Money(2) * Money(2))
    }

    @Test
    fun testMinus() {
        assertEquals(Money(12), Money(20) - Money(8))
    }

    @Test
    fun testDiv() {
        assertEquals(Money(4), Money(12) / Money(3))
    }

    @Test
    fun testTimes() {
        assertEquals(Money(12), Money(4) * Money(3))
    }

    @Test
    fun testToPercent() {
        assertEquals(Money(0.04), Money(4).toPercent())
    }
}