package neilyich.polynomialfactorizationservice.controller

import neilyich.field.PrimeField
import neilyich.field.polynomial.AFieldPolynomial
import neilyich.polynomialfactorizationservice.service.FactorizationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FactorizationController(private val factorizationService: FactorizationService) {
    @RequestMapping("/factorization")
    fun makeFactorization(@RequestParam("polynomial") polynomialString: String, @RequestParam("mod") mod: Int): String {
        return factorizationService.makeFactorization(AFieldPolynomial.fromString(polynomialString.replace(Regex("\\s*\\+\\s*"), " + "), PrimeField(mod))).replace("\n", "<br>") +
                "<form action=\"/home\"><button>Home</button></form>"
    }
}