package pl.bydgoszcz.pensions.calculator

data class InvestmentResult(val payedCapital: Money,
                            val investmentGain: Money,
                            val totalCapital: Money,
                            val wallet: InvestmentWallet)