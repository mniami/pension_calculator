package pl.bydgoszcz.pensions.calculator

data class BankDepositParameters(val rateOfReturnPercent: PercentValue,
                                 val capitalMarginPercent: PercentValue = PercentValue(0.1))
