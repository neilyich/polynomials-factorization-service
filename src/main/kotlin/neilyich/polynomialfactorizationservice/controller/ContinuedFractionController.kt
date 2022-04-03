package neilyich.polynomialfactorizationservice.controller

import neilyich.polynomialfactorizationservice.service.ContinuedFractionCalculator
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ContinuedFractionController(private val continuedFractionCalculator: ContinuedFractionCalculator) {
    @RequestMapping("fraction/result")
    fun calc(@RequestParam("n") n: Int, @RequestParam("m") m: Int): String {
        return continuedFractionCalculator.calculateContinuedFraction(n, m).replace("\n", "<br>") +
                "<form action=\"/fraction/home\"><button>Home</button></form>"
    }

    @RequestMapping("/fraction/home")
    fun home(): String {
        return """
            <html lang="en">
              <style>". {padding=20}"</style>
            <body>
              <!-- your content here... -->
              <form action="/fraction/result">
                <label for="n">n</label><input name="n"/>
                <br>
                <label for="m">m</label><input name="m"/>
                <br>
                <button>Calculate</button>
              </form>
            </body>
            </html>
        """.trimIndent()
    }
}