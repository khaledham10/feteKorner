package dz.opt.feteKorner.service.impl;

import dz.opt.feteKorner.dto.SecteurDto;
import dz.opt.feteKorner.model.Secteur;
import dz.opt.feteKorner.repository.SecteurRepository;
import dz.opt.feteKorner.service.intrf.SecteurService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SecteurServiceImpl implements SecteurService {

    private final SecteurRepository secteurRepo;
    private final ModelMapper modelMapper;

    @Override
    public SecteurDto createSecteur( SecteurDto secteurDto){
        Secteur createdSecteur = this.secteurRepo.save(this.modelMapper.map(secteurDto,Secteur.class));
        secteurDto.setId(createdSecteur.getId());
        return secteurDto;

    }

    @Override
    public List<SecteurDto> getAllSecteur() {
        return this.secteurRepo.findAll().stream().map(secteur->modelMapper.map(secteur,SecteurDto.class)).toList();
    }




}
