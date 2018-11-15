package it.windtre.tremobilitycms.ui.dataproviders;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.windtre.tremobilitycms.backend.data.entity.Workspace;
import it.windtre.tremobilitycms.backend.service.ProductService;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Stream;

@SpringComponent
public class ProductDataProvider extends AbstractBackEndDataProvider<Workspace, String> {

	private final ProductService productService;

	public ProductDataProvider(ProductService productService) {
		this.productService = productService;
	}

	@Override
	protected int sizeInBackEnd(Query<Workspace, String> query) {
		return (int) productService.countAnyMatching(query.getFilter());
	}

	@Override
	public Stream<Workspace> fetchFromBackEnd(Query<Workspace, String> query) {
		return productService.findAnyMatching(query.getFilter(), PageRequest.of(query.getOffset(), query.getLimit()))
				.stream();
	}

}
