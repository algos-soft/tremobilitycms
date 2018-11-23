package it.windtre.tremobilitycms.backend.service;

import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import it.windtre.tremobilitycms.backend.repositories.ZoneRepository;
import it.windtre.tremobilitycms.ui.utils.FormattingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

@org.springframework.stereotype.Service
public class ZoneService implements FilterableCrudService<Zoneitem> {

    private final ZoneRepository zoneRepository;

    @Autowired
    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Page<Zoneitem> findAnyMatching(Optional<String> filter, Pageable pageable) {
        System.out.println("ZoneService findAnyMatching is called...");
        if (filter.isPresent()) {
            if (FormattingUtils.containsOnlyNumber(filter.get())) {
                // filtered by serviceitemId
                Page<Zoneitem> page = zoneRepository.findByServiceitem(getIdByFilter(filter.get()), pageable);
                if (page != null) {
                    return page;
                } else {
                    return find(pageable);
                }
            } else {
                // filtered by name
                String repositoryFilter = "%" + filter.get() + "%";
                return zoneRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
            }
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        System.out.println("ZoneService countAnyMatching is called...");
        if (filter.isPresent()) {
            if (FormattingUtils.containsOnlyNumber(filter.get())) {
                // filtered by serviceitemId
                return zoneRepository.countByServiceitem(getIdByFilter(filter.get()));
            } else {
                // filtered by name
                String repositoryFilter = "%" + filter.get() + "%";
                return zoneRepository.countByNameLikeIgnoreCase(repositoryFilter);
            }
        } else {
            return count();
        }
    }

    public Page<Zoneitem> find(Pageable pageable) {
        return zoneRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Zoneitem, Long> getRepository() {
        return zoneRepository;
    }

    @Override
    public Zoneitem createNew(User currentUser) {
        return new Zoneitem();
    }

    @Override
    public Zoneitem save(User currentUser, Zoneitem entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a zone with that name. Please select a unique name for the service.");
        }

    }


    /** support method */

    private Long getIdByFilter(String filter) {
        String digits = FormattingUtils.extractOnlyNumbers(filter);
        return Long.valueOf(digits);
    }
}

