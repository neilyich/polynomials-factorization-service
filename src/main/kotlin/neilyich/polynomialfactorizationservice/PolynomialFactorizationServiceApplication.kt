package neilyich.polynomialfactorizationservice

import com.github.keelar.exprk.EllipticCurveExpressions
import neilyich.field.PrimeField
import neilyich.polynomialfactorizationservice.ellipticcurve.EllipticCurve
import neilyich.polynomialfactorizationservice.ellipticcurve.PointImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PolynomialFactorizationServiceApplication

fun main(args: Array<String>) {
    val f = PrimeField(97)
    val curve = EllipticCurve(f, f(19), f(61))
    println(curve)
    val p = PointImpl(f, f(87), f(61))
    val q = PointImpl(f, f(41), f(55))
    val twoP = curve.times(p, 2)
    println(twoP)
    val twoPMinusQ = curve.sub(twoP, q)
    println(twoPMinusQ)
    println(EllipticCurveExpressions(curve).define("p", p).define("q", q).eval("2*p-q"))
    runApplication<PolynomialFactorizationServiceApplication>(*args)
}
