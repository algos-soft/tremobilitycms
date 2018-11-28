package it.windtre.tremobilitycms.backend.service;

import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.data.entity.Card;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import it.windtre.tremobilitycms.backend.repositories.CardRepository;
import it.windtre.tremobilitycms.ui.utils.FormattingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.Format;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CardService implements FilterableCrudService<Card> {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Page<Card> findAnyMatching(Optional<String> filter, Pageable pageable) {
        System.out.println("CardService findAnyMatching is called...");
        if (filter.isPresent()) {
            if (FormattingUtils.containsOnlyNumber(filter.get())) {
                // filtered by elementId
                Page<Card> page = cardRepository.findByElement(FormattingUtils.getIdByFilter(filter.get()), pageable);
                if (page != null) {
                    return page;
                } else {
                    return find(pageable);
                }
            } else {
                return find(pageable);
            }
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        System.out.println("CardService countAnyMatching is called...");
        if (filter.isPresent()) {
            if (FormattingUtils.containsOnlyNumber(filter.get())) {
                // filtered by elementId
                return cardRepository.countByElement(FormattingUtils.getIdByFilter(filter.get()));
            } else {
                return count();
            }
        } else {
            return count();
        }
    }

    public Page<Card> find(Pageable pageable) {
        return cardRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Card, Long> getRepository() {
        return cardRepository;
    }

    @Override
    public Card createNew(User currentUser) {
        return new Card();
    }

    @Override
    public Card save(User currentUser, Card entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a card with that name. Please select a unique name for the service.");
        }

    }


    /** support method */
    /*
    private Long getIdByFilter(String filter) {
        String digits = FormattingUtils.extractOnlyNumbers(filter);
        return Long.valueOf(digits);
    }*/
}