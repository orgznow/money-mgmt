package com.nwilson.finance.moneymgmt.controller

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@Slf4j
class GreetingController {

    @GetMapping(path="/greeting")
    String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        log.info("Entered greeting(${name}")
        model.addAttribute("name", name)
        "greeting"
    }
}
