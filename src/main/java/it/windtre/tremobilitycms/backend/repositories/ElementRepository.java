package it.windtre.tremobilitycms.backend.repositories;

import it.windtre.tremobilitycms.backend.data.entity.Element;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ElementRepository extends JpaRepository<Element, Long> {

    Page<Element> findBy(Pageable page);

    //Page<Element> findByNameLikeIgnoreCase(String name, Pageable page);

    //int countByNameLikeIgnoreCase(String name);

    List<Element> findAll();

    Optional<Element> findById(Long id);

}