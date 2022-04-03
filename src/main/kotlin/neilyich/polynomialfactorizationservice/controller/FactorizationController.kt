package neilyich.polynomialfactorizationservice.controller

import neilyich.field.PrimeField
import neilyich.field.polynomial.AFieldPolynomial
import neilyich.polynomialfactorizationservice.service.FactorizationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FactorizationController(private val factorizationService: FactorizationService) {
    @RequestMapping("/factorization/result")
    fun makeFactorization(@RequestParam("polynomial") polynomialString: String, @RequestParam("mod") mod: Int): String {
        return factorizationService.makeFactorization(AFieldPolynomial.fromString(polynomialString.replace(Regex("\\s*\\+\\s*"), " + "), PrimeField(mod))).replace("\n", "<br>") +
                "<form action=\"/factorization/home\"><button>Home</button></form>"
    }

    @RequestMapping("/factorization/home")
    fun home(): String {
        return """
            <html lang="en">
              <style>". {padding=20}"</style>
            <body>
              <!-- your content here... -->
              <form action="/factorization/result">
                <label for="polynomial">polynomial</label><input name="polynomial"/>
                <br>
                <label for="mod">mod</label><input name="mod"/>
                <br>
                <button>Factorization</button>
              </form>
            </body>
            </html>
        """.trimIndent()
    }
}