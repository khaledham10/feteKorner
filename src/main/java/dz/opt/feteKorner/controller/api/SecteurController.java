package dz.opt.feteKorner.controller.api;

import dz.opt.feteKorner.dto.SecteurDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SecteurController {

    @PostMapping("")
    SecteurDto createSecteur(@Valid @RequestBody SecteurDto secteurDto);

    @GetMapping("")
    List<SecteurDto> getAllSecteur();
}
