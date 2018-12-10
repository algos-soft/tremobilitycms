package it.windtre.tremobilitycms.backend.repositories;

import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceitemRepository extends JpaRepository<Serviceitem, Long> {

    Page<Serviceitem> findBy(Pageable page);

    Page<Serviceitem> findByNameLikeIgnoreCase(String name, Pageable page);

    int countByNameLikeIgnoreCase(String name);

    List<Serviceitem> findAll();

    List<Serviceitem> findByService(Long service);

    Optional<Serviceitem> findById(Long id);
}
