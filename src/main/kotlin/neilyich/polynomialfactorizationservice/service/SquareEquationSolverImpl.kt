package neilyich.polynomialfactorizationservice.service

import neilyich.field.element.PrimeFieldElement
import neilyich.field.equations.SquareEquation
import neilyich.field.equations.SquareEquationUtils
import neilyich.polynomialfactorizationservice.config.WrapExceptions
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.lang.Exception
import java.nio.charset.StandardCharsets

@Service
@WrapExceptions
class SquareEquationSolverImpl : SquareEquationSolver {
    override fun solve(equation: SquareEquation<PrimeFieldElement>): String {
        val stream = ByteArrayOutputStream()
        val printStream = PrintStream(stream)
        try {
            val solution = SquareEquationUtils.solve(equation) {
                printStream.println(it)
            }
            if (solution == null) {
                printStream.println("no solution found :(")
                return stream.toString(StandardCharsets.UTF_8)
            }
            val pol = equation.polynomial
            val polX1 = pol.valueAt(solution.first)
            val polX2 = pol.valueAt(solution.second)
            printStream.println("f(x1)=f(${solution.first})=$polX1, f(x2)=f(${solution.second})=$polX2")
        } catch (e: Exception) {
            printStream.println("\n\nERROR:")
            e.printStackTrace(printStream)
        }
        return stream.toString(StandardCharsets.UTF_8)
    }
}