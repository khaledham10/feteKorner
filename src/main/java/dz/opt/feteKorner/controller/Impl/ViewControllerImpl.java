package dz.opt.feteKorner.controller.Impl;

import dz.opt.feteKorner.controller.api.ViewController;
import dz.opt.feteKorner.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/views")
@RequiredArgsConstructor
public class ViewControllerImpl implements ViewController {

    private final AuthServiceImpl authService;



    @Value("${front.url.login}")
    private String loginUrl;

    @Override
    public ModelAndView verification(String code) {
        this.authService.verification(code);
        var modelAndView = new ModelAndView("validation");
        modelAndView.addObject("loginUrl",this.loginUrl);
        return modelAndView;

    }
}
