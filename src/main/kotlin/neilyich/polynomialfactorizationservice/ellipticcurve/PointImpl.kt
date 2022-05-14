package neilyich.polynomialfactorizationservice.ellipticcurve

import neilyich.field.Field
import neilyich.field.element.PrimeFieldElement

data class PointImpl(
    val field: Field<PrimeFieldElement>,
    val x: PrimeFieldElement,
    val y: PrimeFieldElement
): Point {
    override fun unaryMinus(): PointImpl = PointImpl(field, x, field.inverseAdd(y))

    override fun toString(): String {
        return "($x, $y)"
    }
}