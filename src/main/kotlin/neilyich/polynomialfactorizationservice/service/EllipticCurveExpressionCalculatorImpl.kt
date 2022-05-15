package neilyich.polynomialfactorizationservice.service

import com.github.keelar.exprk.EllipticCurveExpressions
import neilyich.polynomialfactorizationservice.config.WrapExceptions
import neilyich.field.Field
import neilyich.field.PrimeField
import neilyich.field.element.PrimeFieldElement
import neilyich.polynomialfactorizationservice.ellipticcurve.EllipticCurve
import neilyich.polynomialfactorizationservice.ellipticcurve.Point
import neilyich.polynomialfactorizationservice.ellipticcurve.PointImpl
import neilyich.polynomialfactorizationservice.ellipticcurve.ZeroPoint
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@WrapExceptions
class EllipticCurveExpressionCalculatorImpl : EllipticCurveExpressionCalculator {
    private val log = LoggerFactory.getLogger(EllipticCurveExpressionCalculatorImpl::class.java)

    override fun calculate(mod: Int, a: Int, b: Int, expression: String, points: String): String {
        val f = PrimeField(mod)
        val curve = EllipticCurve(f, f(a), f(b))
        val pointsMap = parsePoints(f, points)
        val prefix = "Calculating value for: \n$curve, \n$f, \npoints: $pointsMap, \nexpression: $expression"
        log.info(prefix)
        val expr = EllipticCurveExpressions(curve)
        for ((name, point) in pointsMap) {
            expr.define(name, point)
        }
        return prefix + "\nresult: " + expr.eval(expression).toString()
    }

    private fun parsePoints(field: Field<PrimeFieldElement>, str: String): Map<String, Point> {
        return str.split(";").associate { declaration ->
            val s = declaration.trim().split("=")
            if (s.size != 2) {
                throw IllegalArgumentException("wrong variable format: ${declaration.trim()}")
            }
            val name = s[0].trim()
            val point = s[1].trim()
            name to parsePoint(field, point)
        }
    }

    private fun parsePoint(field: Field<PrimeFieldElement>, str: String): Point {
        if (str == "0") {
            return ZeroPoint
        }
        val xy = str.removePrefix("(").removeSuffix(")").trim().split(",")
        if (xy.size != 2) {
            throw IllegalArgumentException("wrong point format: $str")
        }
        val x = xy[0].trim()
        val y = xy[1].trim()
        return PointImpl(field, field.fromString(x), field.fromString(y))
    }
}