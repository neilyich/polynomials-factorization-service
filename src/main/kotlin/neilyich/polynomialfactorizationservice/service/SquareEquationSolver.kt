package neilyich.polynomialfactorizationservice.service

import neilyich.field.element.PrimeFieldElement
import neilyich.field.equations.SquareEquation

interface SquareEquationSolver {
    fun solve(equation: SquareEquation<PrimeFieldElement>): String
}