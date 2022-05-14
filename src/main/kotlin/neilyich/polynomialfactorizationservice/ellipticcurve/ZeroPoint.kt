package neilyich.polynomialfactorizationservice.ellipticcurve

object ZeroPoint: Point {
    override fun unaryMinus(): Point = this

    override fun toString(): String {
        return "0"
    }
}