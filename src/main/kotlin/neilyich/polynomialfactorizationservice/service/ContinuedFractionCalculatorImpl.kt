package neilyich.polynomialfactorizationservice.service

import neilyich.polynomialfactorizationservice.config.WrapExceptions
import org.springframework.stereotype.Service

@Service
@WrapExceptions
class ContinuedFractionCalculatorImpl : ContinuedFractionCalculator {
    override fun calculateContinuedFraction(n: Int, m: Int): String {
        var ni = n
        var mi = m
        var qi = ni / mi
        var ri = ni % mi
        val qiList = mutableListOf(qi)
        while (ri != 0) {
            ni = mi
            mi = ri
            qi = ni / mi
            ri = ni % mi
            qiList.add(qi)
        }
        val approximationFractionsList = mutableListOf(1 to 0, qiList[0] to 1)
        for (i in 1 until qiList.size) {
            val pn = qiList[i] * approximationFractionsList[i].first + approximationFractionsList[i - 1].first
            val qn = qiList[i] * approximationFractionsList[i].second + approximationFractionsList[i - 1].second
            approximationFractionsList.add(pn to qn)
        }
        return qiList.joinToString(separator = "\n") + "\n\nApproximations:\n" +
                approximationFractionsList.subList(1, approximationFractionsList.size).joinToString(separator = "\n") { "${it.first} / ${it.second }" }
    }
}