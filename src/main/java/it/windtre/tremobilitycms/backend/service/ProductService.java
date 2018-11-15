package it.windtre.tremobilitycms.backend.service;

import it.windtre.tremobilitycms.backend.data.entity.Workspace;
import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements FilterableCrudService<Workspace> {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Page<Workspace> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return productRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return productRepository.countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return count();
		}
	}

	public Page<Workspace> find(Pageable pageable) {
		return productRepository.findBy(pageable);
	}

	@Override
	public JpaRepository<Workspace, Long> getRepository() {
		return productRepository;
	}

	@Override
	public Workspace createNew(User currentUser) {
		return new Workspace();
	}

	@Override
	public Workspace save(User currentUser, Workspace entity) {
		try {
			return FilterableCrudService.super.save(currentUser, entity);
		} catch (DataIntegrityViolationException e) {
			throw new UserFriendlyDataException(
					"There is already a product with that name. Please select a unique name for the product.");
		}

	}

}
