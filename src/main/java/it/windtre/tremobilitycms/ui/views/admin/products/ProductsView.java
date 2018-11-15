package it.windtre.tremobilitycms.ui.views.admin.products;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Workspace;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_PRODUCTS;

@Tag("products-view")
@HtmlImport("src/views/admin/products/products-view.html")
@Route(value = PAGE_PRODUCTS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_PRODUCTS)
@RouteAlias(value = BakeryConst.PAGE_ROOT, layout = MainView.class)
@Secured(Role.ADMIN)
public class ProductsView extends CrudView<Workspace, TemplateModel> {

	@Id("search")
	private SearchBar search;

	@Id("grid")
	private Grid<Workspace> grid;

	private CrudEntityPresenter<Workspace> presenter;

	private final BeanValidationBinder<Workspace> binder = new BeanValidationBinder<>(Workspace.class);

	@Autowired
	public ProductsView(CrudEntityPresenter<Workspace> presenter, ProductForm form) {
		super(EntityUtil.getName(Workspace.class), form);
		this.presenter = presenter;
		form.setBinder(binder);

		setupEventListeners();
		setupGrid();
		presenter.setView(this);
	}

	private void setupGrid() {
		grid.addColumn(Workspace::getName).setHeader("Workspace Name").setFlexGrow(10);
		grid.addColumn(Workspace::getEnabled).setHeader("Enabled").setFlexGrow(5);
	}

	@Override
	public Grid<Workspace> getGrid() {
		return grid;
	}

	@Override
	protected CrudEntityPresenter<Workspace> getPresenter() {
		return presenter;
	}

	@Override
	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

	@Override
	protected BeanValidationBinder<Workspace> getBinder() {
		return binder;
	}

	@Override
	protected SearchBar getSearchBar() {
		return search;
	}
}
