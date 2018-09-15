package pl.bydgoszcz.pensions.calculator

data class BankDepositReport(val investGain: Money, val netGain: Money, val tax: Money, val bankMargin: Money)
    : InvestmentReport
