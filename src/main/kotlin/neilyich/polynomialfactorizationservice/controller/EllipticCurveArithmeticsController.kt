package neilyich.polynomialfactorizationservice.controller

import neilyich.polynomialfactorizationservice.service.EllipticCurveExpressionCalculator
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EllipticCurveArithmeticsController(private val ellipticCurveExpressionCalculator: EllipticCurveExpressionCalculator) {
    @RequestMapping("/elliptic-curve/result")
    fun makeFactorization(@RequestParam("mod") mod: Int,
                          @RequestParam("a") a: Int,
                          @RequestParam("b") b: Int,
                          @RequestParam("expr") expr: String,
                          @RequestParam("points") points: String): String {

        return ellipticCurveExpressionCalculator.calculate(mod,a, b, expr, points).replace("\n", "<br>") +
                "<form action=\"/elliptic-curve/home\"><button>Home</button></form>"
    }

    @RequestMapping("/elliptic-curve/home")
    fun home(): String {
        return """
            <html lang="en">
              <style>". {padding=20}"</style>
            <body>
              <!-- your content here... -->
              <form action="/elliptic-curve/result">
                <label for="a">Curve: y^2 = x^3 + </label><input name="a"/>
                <label for="b">x + </label><input name="b"/>
                <br>
                <label for="mod">mod</label><input name="mod"/>
                <br>
                <label for="expr">expression (use lowercase letters): </label><input name="expr", placeholder="2*p-q"/>
                <br>
                <label for="points">points (use lowercase letters): </label><input name="points", placeholder="p=(1,2); q=(3,4)"/>
                <br>
                <button>Solve</button>
              </form>
            </body>
            </html>
        """.trimIndent()
    }

}