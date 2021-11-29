package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model){

     //   System.out.println("Hello, " + name + " " + surname);

        model.addAttribute("message", "Hello, " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage(){
        return "first/goodBye";
    }

    @GetMapping("/calculator")
    public String goodByePage(@RequestParam(value = "a",required = false) int a,
                              @RequestParam(value = "b", required = false) int b,
                              @RequestParam(value = "action", required = false) String action,
                              Model model){

        double result;

        switch (action.toLowerCase()){
            case ("multiplication"):
                result = a * b;
                break;
            case ("addition"):
                result = a + b;
                break;
            case ("subtraction"):
                result = a - b;
                break;
            case ("division"):
                result = a / (double)b;
                break;
            default:
                result = 0;
                break;
        }

        model.addAttribute("result", result);
        return "first/calculator";
    }
}
