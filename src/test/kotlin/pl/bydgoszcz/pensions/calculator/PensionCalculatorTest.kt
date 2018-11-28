package pl.bydgoszcz.pensions.calculator

import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import java.text.NumberFormat

class PensionCalculatorTest {
    private val formatter = NumberFormat.getCurrencyInstance()
    @Test
    fun pensionCalculation() {
        val calculator = PensionCalculator()
        val result = calculator.calculate(
                contributionPerMonth = Money(3000),
                investmentTime = InvestmentTime().applyFromYears(25),
                rateOfReturn = Money(2))
        // getting result
        val yearsOfPension = 20
        val pensionOnMonth = result.totalCapital / Money(yearsOfPension * 12)

        println(String.format("Input: \t\t\t\t%s\n" +
                "Investment gain: \t%s\n" +
                "Total capital: \t\t%s\n" +
                "Pension on month: \t%s",
                formatter.format(result.payedCapital),
                formatter.format(result.investmentGain),
                formatter.format(result.totalCapital),
                formatter.format(pensionOnMonth)))

        result.wallet.getCapitals()
                .forEach { println(String.format("%s = %s", it.key.name, it.value)) }

        assertEquals(result.payedCapital, Money(900000))
        assertTrue(result.totalCapital < Money(4000000))
    }

    @Test
    fun rentFlat() {
        RentRoomsInvestment(RentRoomsParameters(0.0, 11000.0, 220000.0, 2500.0), 25, 25).calculate {
            //            if (it.result.year % 4 == 0 && random.nextBoolean()){
//                it.parameters.tfiGainPerc = -2.0
//            }
//            if (it.result.year % 7 == 0 && random.nextBoolean()){
//                it.parameters.tfiGainPerc = 2.0
//            }

        }
    }

    @Test
    fun testRentingFlatCalculation() {
        val investment = RentRoomsInvestment(RentRoomsParameters(0.0, 10000.0, 100000.0, 1000.0), 20, 30)
        investment.calculate {
            when (it.result.year) {
                in 1..8 -> {
                    assertEquals(it.result.capital, it.result.year * 12000.0, "capital")
                    assertEquals(it.result.tfiGainTotal, 0.0, "tfiGainTotal")
                    assertEquals(it.result.flats, 0, "flats")
                }
                9 -> {
                    assertEquals(it.result.capital, 8000.0, "capital")
                    assertEquals(it.result.tfiGainTotal, 0.0, "tfiGainTotal")
                    assertEquals(it.result.flats, 1, "flats")
                    assertEquals(it.result.flatGainForYear, 0.0, "flatRentGainForYear")
                }
                14 -> {
                    assertEquals(it.result.capital, 18000.0, "capital")
                    assertEquals(it.result.tfiGainTotal, 0.0, "tfiGainTotal")
                    assertEquals(it.result.flats, 2, "flats")
                    assertEquals(it.result.flatGainForYear, 10000.0, "flatRentGainForYear")
                }
                20 -> {
                    assertEquals(it.result.capital, 40000.0, "capital")
                    assertEquals(it.result.tfiGainTotal, 0.0, "tfiGainTotal")
                    assertEquals(it.result.flats, 4, "flats")
                    assertEquals(it.result.flatGainForYear, 30000.0, "flatRentGainForYear")
                    assertEquals(it.result.monthPensionFromFlatsRenting, 4.0 * 10000.0 / 12.0 + 40000.0 / 12.0 / 30.0, "")
                }
            }
        }
    }

    @Test
    fun testRentingFlatCalculationWithTfi() {
        val investment = RentRoomsInvestment(RentRoomsParameters(2.0, 10000.0, 100000.0, 1000.0), 20, 30)
        investment.calculate {
            when (it.result.year) {
                1 -> {
                    assertEquals(it.result.capital, 12000.0, "capital")
                    assertEquals(it.result.tfiGainTotal, 0.0, "tfiGainTotal")
                    assertEquals(it.result.flats, 0, "flats")
                }
                2 -> {
                    assertEquals(it.result.capital, 24240.0, "capital")
                    assertEquals(it.result.tfiGainTotal, 240.0, "tfiGainTotal")
                    assertEquals(it.result.flats, 0, "flats")
                }
                3 -> {
                    assertEquals(it.result.capital, 36724.8, "capital")
                    assertEquals(it.result.tfiGainTotal, 724.8, "tfiGainTotal")
                    assertEquals(it.result.flats, 0, "flats")
                }
            }
        }
    }

}

