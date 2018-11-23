package it.windtre.tremobilitycms.backend.repositories;

import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zoneitem, Long> {

    Page<Zoneitem> findBy(Pageable page);

    Page<Zoneitem> findByNameLikeIgnoreCase(String name, Pageable page);

    int countByNameLikeIgnoreCase(String name);

    List<Zoneitem> findAll();

    Page<Zoneitem> findByServiceitem(Long serviceitem, Pageable page);

    int countByServiceitem(Long serviceitem);
    
}
