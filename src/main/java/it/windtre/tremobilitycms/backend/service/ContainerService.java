package it.windtre.tremobilitycms.backend.service;

import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.data.entity.Container;
import it.windtre.tremobilitycms.backend.repositories.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ContainerService implements FilterableCrudService<Container> {

    private final ContainerRepository containerRepository;

    @Autowired
    public ContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    @Override
    public Page<Container> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return find(pageable); //containerRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return count(); //containerRepository.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Container> find(Pageable pageable) {
        return containerRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Container, Long> getRepository() {
        return containerRepository;
    }

    @Override
    public Container createNew(User currentUser) {
        return new Container();
    }

    @Override
    public Container save(User currentUser, Container entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a container with that name. Please select a unique name for the service.");
        }

    }

}