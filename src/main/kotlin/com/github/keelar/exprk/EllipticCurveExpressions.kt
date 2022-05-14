package com.github.keelar.exprk

import com.github.keelar.exprk.internal.*
import neilyich.polynomialfactorizationservice.ellipticcurve.EllipticCurve
import neilyich.polynomialfactorizationservice.ellipticcurve.Point
import java.math.MathContext

class EllipticCurveExpressions(
    curve: EllipticCurve
) {
    private val evaluator = EllipticCurveEvaluator(curve)

    init {
    }

    fun define(name: String, value: Int): EllipticCurveExpressions {
        define(name, value.toString())

        return this
    }

    fun define(name: String, p: Point): EllipticCurveExpressions {
        evaluator.define(name, PointEllipticCurveExpressionValue(p))

        return this
    }

    fun define(name: String, expression: String): EllipticCurveExpressions {
        val expr = parse(expression)
        evaluator.define(name, expr)

        return this
    }

    private fun parse(expression: String): Expr {
        return parse(scan(expression))
    }

    private fun parse(tokens: List<Token>): Expr {
        return Parser(tokens).parse()
    }

    private fun scan(expression: String): List<Token> {
        return Scanner(expression, MathContext.DECIMAL64).scanTokens()
    }

    fun eval(expression: String): EllipticCurveExpressionValue {
        return evaluator.eval(parse(expression))
    }

}