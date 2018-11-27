package it.windtre.tremobilitycms.ui.views.admin.service;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Service;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.backend.repositories.ServiceRepository;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_SERVICES;

@Tag("services-view")
@HtmlImport("src/views/admin/services/services-view.html")
@Route(value = PAGE_SERVICES, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_SERVICES)
@Secured(Role.ADMIN)
public class ServicesView extends CrudView<Service, TemplateModel>
    implements PropertyChangeListener {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Service> grid;

    private final CrudEntityPresenter<Service> presenter;

    private final BeanValidationBinder<Service> binder = new BeanValidationBinder<>(Service.class);

    private ServiceForm serviceForm;
    private ServiceRepository serviceRepository;


    @Autowired
    public ServicesView(CrudEntityPresenter<Service> presenter, ServiceForm form, ServiceRepository serviceRepository) {
        super(EntityUtil.getName(Service.class), form);
        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);

        super.addPropertyChangeListener(this);

        serviceForm = form;

        this.serviceRepository = serviceRepository;
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
    protected CrudEntityPresenter<Service> getPresenter() {
        return presenter;
    }

    @Override
    protected String getBasePage() {
        return PAGE_SERVICES;
    }

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Service> getBinder() {
        return binder;
    }


    /** property change */

    public void propertyChange(PropertyChangeEvent evt) {
        //to get newValue (String) evt.getNewValue();

        // generate new id and fill it
        Long id = Long.valueOf(serviceRepository.findAll().size() + 1);
        getPresenter().getEntity().setId(id);

        // update form UI
        serviceForm.getIdTF().setValue(String.valueOf(id));
    }


}
