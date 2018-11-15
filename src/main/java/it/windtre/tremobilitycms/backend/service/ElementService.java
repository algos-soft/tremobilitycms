package it.windtre.tremobilitycms.backend.service;

import it.windtre.tremobilitycms.backend.data.entity.Element;
import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.repositories.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ElementService implements FilterableCrudService<Element> {

    private final ElementRepository elementRepository;

    @Autowired
    public ElementService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    @Override
    public Page<Element> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return find(pageable); //elementRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return count(); //elementRepository.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Element> find(Pageable pageable) {
        return elementRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Element, Long> getRepository() {
        return elementRepository;
    }

    @Override
    public Element createNew(User currentUser) {
        return new Element();
    }

    @Override
    public Element save(User currentUser, Element entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already an element with that name. Please select a unique name for the service.");
        }

    }

}
