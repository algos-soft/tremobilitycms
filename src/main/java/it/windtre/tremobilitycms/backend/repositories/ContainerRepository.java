package it.windtre.tremobilitycms.backend.repositories;

import it.windtre.tremobilitycms.backend.data.entity.Container;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContainerRepository extends JpaRepository<Container, Long> {

    Page<Container> findBy(Pageable page);

    //Page<Container> findByNameLikeIgnoreCase(String name, Pageable page);

    //int countByNameLikeIgnoreCase(String name);

    List<Container> findAll();
}

