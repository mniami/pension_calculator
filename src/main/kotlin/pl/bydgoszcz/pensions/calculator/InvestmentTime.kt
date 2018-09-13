package pl.bydgoszcz.pensions.calculator

class InvestmentTime(var months: Int = 0) {
    fun getYears(): Int = months / 12
    fun applyFromYears(years: Int): InvestmentTime {
        months = years * 12
        return this
    }
}