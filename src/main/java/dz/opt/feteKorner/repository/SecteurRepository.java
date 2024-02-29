package dz.opt.feteKorner.repository;

import dz.opt.feteKorner.model.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository  extends JpaRepository<Secteur,Integer> {
}
