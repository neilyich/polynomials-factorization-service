package neilyich.polynomialfactorizationservice.service

import neilyich.field.element.FieldElement
import neilyich.field.polynomial.AFieldPolynomial
import neilyich.field.polynomial.OnePolynomial
import neilyich.polynomialfactorizationservice.config.WrapExceptions
import neilyich.util.FieldPolynomialUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

@Service
@WrapExceptions
class FactorizationServiceImpl : FactorizationService {
    private val log = LoggerFactory.getLogger(FactorizationServiceImpl::class.java)

    override fun <CoefsFieldElement : FieldElement> makeFactorization(polynomial: AFieldPolynomial<CoefsFieldElement>): String {
        log.info("making factorization of f(x) = $polynomial")
        val stream = ByteArrayOutputStream()
        val printStream = PrintStream(stream)
        val factorization = FieldPolynomialUtils.polynomialFactorization(polynomial) {
            printStream.println(it)
        }
        if (checkFactorization(polynomial, factorization)) {
            printStream.println("factorization check was successful")
        } else {
            printStream.println("factorization check failed")
        }
        return stream.toString(StandardCharsets.UTF_8)
    }

    private fun <CoefsFieldElement : FieldElement> checkFactorization(polynomial: AFieldPolynomial<CoefsFieldElement>, factorization: Map<AFieldPolynomial<CoefsFieldElement>, Int>): Boolean {
        log.info("making factorization check ($polynomial and ${FieldPolynomialUtils.factorizationDescription(factorization)}")
        var currentPolynomial: AFieldPolynomial<CoefsFieldElement> = OnePolynomial(polynomial.field, polynomial.literal)
        for ((d, pow) in factorization) {
            currentPolynomial *= d.pow(pow)
        }
        if (polynomial == currentPolynomial) {
            log.info("factorization check was successful")
            return true
        }
        log.error("factorization check failed ($polynomial != $currentPolynomial")
        return false
    }
}