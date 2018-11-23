package it.windtre.tremobilitycms.backend.repositories;

import it.windtre.tremobilitycms.backend.data.entity.Card;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findBy(Pageable page);

    //Page<Card> findByNameLikeIgnoreCase(String name, Pageable page);

    //int countByNameLikeIgnoreCase(String name);

    List<Card> findAll();

    Page<Card> findByElement(Long element, Pageable page);

    int countByElement(Long element);
}
