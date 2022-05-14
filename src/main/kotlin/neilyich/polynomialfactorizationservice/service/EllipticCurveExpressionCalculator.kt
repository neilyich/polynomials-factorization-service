package neilyich.polynomialfactorizationservice.service

interface EllipticCurveExpressionCalculator {
    fun calculate(mod: Int, a: Int, b: Int, expression: String, points: String): String
}