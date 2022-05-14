package com.github.keelar.exprk

class IntEllipticCurveExpressionValue(
    val value: Int
) : EllipticCurveExpressionValue {
    override fun toString(): String {
        return value.toString()
    }
}