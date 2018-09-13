package pl.bydgoszcz.pensions.calculator

data class InvestmentParameters(val monthContribution: Money,
                                val initialCapital: Money,
                                val monthsCount: Int,
                                val taxForIncome: PercentValue,
                                val bankDepositParameters: BankDepositParameters)