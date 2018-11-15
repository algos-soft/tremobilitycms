package it.windtre.tremobilitycms.ui.views.admin.containers;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Container;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_CONTAINERS;

@Tag("containers-view")
@HtmlImport("src/views/admin/containers/containers-view.html")
@Route(value = PAGE_CONTAINERS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_CONTAINERS)
@Secured(Role.ADMIN)
public class ContainersView extends CrudView<Container, TemplateModel> {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Container> grid;

    private final CrudEntityPresenter<Container> presenter;

    private final BeanValidationBinder<Container> binder = new BeanValidationBinder<>(Container.class);

    @Autowired
    public ContainersView(CrudEntityPresenter<Container> presenter, ContainerForm form) {
        super(EntityUtil.getName(Container.class), form);
        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);
    }

    private void setupGrid() {
        grid.addColumn(Container::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Container::getState).setWidth("270px").setHeader("State").setFlexGrow(5);
    }

    @Override
    public Grid<Container> getGrid() {
        return grid;
    }

    @Override
    protected CrudEntityPresenter<Container> getPresenter() {
        return presenter;
    }

    @Override
    protected String getBasePage() {
        return PAGE_CONTAINERS;
    }

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Container> getBinder() {
        return binder;
    }
}
