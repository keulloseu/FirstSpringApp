package pl.kurs.java.firstspringapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kurs.java.firstspringapp.model.MyForm;

@Controller
public class FirstController {

    @GetMapping("/welcome")
    public String test(ModelMap map) {
        map.addAttribute("myForm", new MyForm());
        return "dupa";
    }

    @PostMapping( "/welcome/execute")
    public String execute(ModelMap map, @ModelAttribute("myForm") MyForm form) {
        map.addAttribute("name", form.getName());
        return "dupa_execute";
    }



}
