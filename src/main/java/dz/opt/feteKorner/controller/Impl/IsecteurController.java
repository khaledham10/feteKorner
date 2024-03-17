package dz.opt.feteKorner.controller.Impl;

import dz.opt.feteKorner.controller.api.SecteurController;
import dz.opt.feteKorner.dto.SecteurDto;
import dz.opt.feteKorner.service.intrf.SecteurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value = "/secteur")
@RequiredArgsConstructor
public class IsecteurController implements SecteurController {

    private final SecteurService secteurService;

    @Override
    public SecteurDto createSecteur(@Valid SecteurDto secteurDto){
        return this.secteurService.createSecteur(secteurDto);
    }

    @Override
    public List<SecteurDto> getAllSecteur() {
        return this.secteurService.getAllSecteur();
    }


}
