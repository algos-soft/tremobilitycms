package it.windtre.tremobilitycms.ui.views.admin.service;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.entity.Service;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.LinkEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.LinkEntityView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_SERVICESLIST;

@Tag("serviceslist-view")
@HtmlImport("src/views/admin/services/serviceslist-view.html")
@SpringComponent
@Route(value = PAGE_SERVICESLIST)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceslistView extends LinkEntityView<Service, TemplateModel> {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Service> grid;

    private final LinkEntityPresenter<Service> presenter;

    private final BeanValidationBinder<Service> binder = new BeanValidationBinder<>(Service.class);

    @Autowired
    public ServiceslistView(LinkEntityPresenter<Service> presenter) {
        super(EntityUtil.getName(Service.class));
        this.presenter = presenter;
        //form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);
    }

    private void setupGrid() {
        grid.addColumn(Service::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Service::getCity).setWidth("270px").setHeader("City").setFlexGrow(5);
        grid.addColumn(Service::getName).setWidth("200px").setHeader("Name").setFlexGrow(5);
        grid.addColumn(Service::getType).setWidth("150px").setHeader("Type").setFlexGrow(5);
    }

    @Override
    public Grid<Service> getGrid() {
        return grid;
    }

    @Override
    protected LinkEntityPresenter<Service> getPresenter() {
        return presenter;
    }

    /*@Override
    protected String getBasePage() {
        return PAGE_SERVICES;
    }*/

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Service> getBinder() {
        return binder;
    }

    @Override
    protected String getBasePage() {
        return "";
    }
}

