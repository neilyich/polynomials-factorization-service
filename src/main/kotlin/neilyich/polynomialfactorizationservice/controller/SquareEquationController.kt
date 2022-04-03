package neilyich.polynomialfactorizationservice.controller

import neilyich.field.PrimeField
import neilyich.field.element.PrimeFieldElement
import neilyich.field.equations.SquareEquation
import neilyich.field.polynomial.AFieldPolynomial
import neilyich.polynomialfactorizationservice.service.SquareEquationSolver
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SquareEquationController(private val squareEquationSolver: SquareEquationSolver) {
    @RequestMapping("/square-equation/result")
    fun solveEquation(@RequestParam("xSquare") xSquare: Int,
                      @RequestParam("x") x: Int,
                      @RequestParam("free") free: Int,
                      @RequestParam("mod") mod: Int): String {
        println("here")
        val field = PrimeField(mod)
        return squareEquationSolver.solve(SquareEquation(field, PrimeFieldElement(field, xSquare), PrimeFieldElement(field, x), PrimeFieldElement(field, free))).replace("\n", "<br>") +
                "<form action=\"/square-equation/home\"><button>Home</button></form>"
    }

    @RequestMapping("/square-equation/home")
    fun home(): String {
        return """
            <html lang="en">
              <style>". {padding=20}"</style>
            <body>
              <!-- your content here... -->
              <form action="/square-equation/result">
                <label for="xSquare">Equation: </label><input name="xSquare"/>
                <label for="x">x^2 + </label><input name="x"/>
                <label for="free">x + </label><input name="free"/>
                <br>
                <label for="mod">mod</label><input name="mod"/>
                <br>
                <button>Solve</button>
              </form>
            </body>
            </html>
        """.trimIndent()
    }
}