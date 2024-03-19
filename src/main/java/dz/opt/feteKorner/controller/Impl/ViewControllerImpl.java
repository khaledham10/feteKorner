package dz.opt.feteKorner.controller.Impl;

import dz.opt.feteKorner.controller.api.ViewController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/views")
public class ViewControllerImpl implements ViewController {


    @Value("${front.url.login}")
    private String loginUrl;

    @Override
    public ModelAndView verification(String code) {
        var modelAndView = new ModelAndView("validation");
        modelAndView.addObject("loginUrl",loginUrl);
        return modelAndView;

    }
}
