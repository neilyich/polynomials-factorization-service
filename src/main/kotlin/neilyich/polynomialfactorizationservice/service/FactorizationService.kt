package neilyich.polynomialfactorizationservice.service

import neilyich.field.element.FieldElement
import neilyich.field.polynomial.AFieldPolynomial

interface FactorizationService {
    fun <CoefsFieldElement: FieldElement> makeFactorization(polynomial: AFieldPolynomial<CoefsFieldElement>): String
}