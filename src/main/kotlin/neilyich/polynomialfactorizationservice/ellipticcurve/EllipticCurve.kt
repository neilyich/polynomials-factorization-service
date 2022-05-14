package neilyich.polynomialfactorizationservice.ellipticcurve

import neilyich.field.Field
import neilyich.field.element.PrimeFieldElement
import neilyich.polynomialfactorizationservice.utils.FieldCalculator

// y^2 = x^3 + ax + b
class EllipticCurve(
    val field: Field<PrimeFieldElement>,
    private val a: PrimeFieldElement,
    private val b: PrimeFieldElement
) {
    fun add(p: Point, q: Point): Point {
        checkOnCurve(p)
        checkOnCurve(q)
        if (p is ZeroPoint) {
            return q
        }
        if (q is ZeroPoint) {
            return p
        }
        p as PointImpl
        q as PointImpl
        val m: PrimeFieldElement = if (p == q) {
            val c = FieldCalculator(field, field.one(3))
            c.mult(p.x).mult(p.x).add(a)
                .div(
                    c.take(field.one(2)).mult(p.y).get()
                ).get()
        } else if (p.x == q.x) {
            return ZeroPoint
        } else {
            val c = FieldCalculator(field, p.y)
            c.sub(q.y)
                .div(
                    c.take(p.x).sub(q.x).get()
                ).get()
        }
        val xr = FieldCalculator(field, m).mult(m).sub(p.x).sub(q.x).get()
        val yr = FieldCalculator(field, p.x).sub(xr).mult(m).sub(p.y).get()
        return PointImpl(field, xr, yr).also { checkOnCurve(it) }
    }

    fun times(p: Point, n: Int): Point {
        if (n == 0) {
            return ZeroPoint
        }
        if (n < 0) {
            return times(-p, -n)
        }
        var result = p
        for (i in 1 until n) {
            result = add(result, p)
        }
        return result
    }

    fun sub(p: Point, q: Point): Point {
        return add(p, -q)
    }

    private fun checkOnCurve(p: Point) {
        if (p !is PointImpl) {
            return
        }
        val y2 = field.mult(p.y, p.y)
        val x3 = field.mult(p.x, field.mult(p.x, p.x))
        val ax = field.mult(a, p.x)
        if (y2 != field.add(x3, field.add(ax, b))) {
            throw IllegalArgumentException("$p is not on curve $this")
        }
    }

    override fun toString(): String {
        return "y^2 = x^3 + ${a}x + $b"
    }
}