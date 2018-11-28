package pl.bydgoszcz.pensions.calculator

class InvestmentWallet(initialCapital: Money = Money.ZERO) {
    companion object {
        val MAIN_WALLET_GROUP = WalletGroup("Main")
    }

    private var capitalMap: MutableMap<WalletGroup, Money> = hashMapOf()
    private val walletGroups: List<WalletGroup> = emptyList()

    init {
        capitalMap[MAIN_WALLET_GROUP] = initialCapital
    }

    fun getCapitals(): Map<WalletGroup, Money> {
        return capitalMap.toMap()
    }

    fun addCapital(amount: Money, walletGroup: WalletGroup): InvestmentAction {
        var oldValue = capitalMap[walletGroup]
        if (oldValue == null) {
            oldValue = Money(0)
        }
        val newValue = oldValue + amount
        capitalMap[walletGroup] = newValue
        return CapitalValueIncreased(oldValue, newValue)
    }

    fun getCapital(walletGroup: WalletGroup): Money {
        return capitalMap[walletGroup] ?: Money(0)
    }

    fun getTotalCapital(): Money {
        var sum = Money(0)
        for (value in capitalMap.values) {
            sum += value
        }
        return sum
    }

    data class CapitalValueIncreased(val oldValue: Money, val capitalValue: Money) : InvestmentAction
}

