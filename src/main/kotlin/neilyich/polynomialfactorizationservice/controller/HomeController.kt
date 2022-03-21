package neilyich.polynomialfactorizationservice.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @RequestMapping("/home")
    fun home(): String {
        return """
            <html lang="en">
              <style>". {padding=20}"</style>
            <body>
              <!-- your content here... -->
              <form action="/factorization">
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