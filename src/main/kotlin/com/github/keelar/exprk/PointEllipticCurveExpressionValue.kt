package com.github.keelar.exprk

import neilyich.polynomialfactorizationservice.ellipticcurve.Point

class PointEllipticCurveExpressionValue(
    val p: Point
) : EllipticCurveExpressionValue {
    override fun toString(): String {
        return p.toString()
    }
}