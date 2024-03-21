package dz.opt.feteKorner.controller.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface ViewController {



    @GetMapping("/auth/verification")
    ModelAndView verification(@RequestParam String code);}
