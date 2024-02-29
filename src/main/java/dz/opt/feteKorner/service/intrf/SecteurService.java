package dz.opt.feteKorner.service.intrf;

import dz.opt.feteKorner.dto.SecteurDto;

import java.util.List;

public interface SecteurService {

    public SecteurDto createSecteur( SecteurDto secteurDto);
    public List<SecteurDto> getAllSecteur();
}
