package it.windtre.tremobilitycms.backend.service;

import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.repositories.ServiceitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceitemService implements FilterableCrudService<Serviceitem> {

    private final ServiceitemRepository serviceitemRepo;

    @Autowired
    public ServiceitemService(ServiceitemRepository serviceItemRepository) {
        this.serviceitemRepo = serviceItemRepository;
    }

    @Override
    public Page<Serviceitem> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return serviceitemRepo.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return serviceitemRepo.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Serviceitem> find(Pageable pageable) {
        return serviceitemRepo.findBy(pageable);
    }

    @Override
    public JpaRepository<Serviceitem, Long> getRepository() {
        return serviceitemRepo;
    }

    @Override
    public Serviceitem createNew(User currentUser) {
        return new Serviceitem();
    }

    @Override
    public Serviceitem save(User currentUser, Serviceitem entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a serviceItem with that name. Please select a unique name for the service.");
        }

    }

}

