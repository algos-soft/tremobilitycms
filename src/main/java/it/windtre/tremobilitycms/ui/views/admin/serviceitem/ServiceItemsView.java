package it.windtre.tremobilitycms.ui.views.admin.serviceitem;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.backend.repositories.ServiceRepository;
import it.windtre.tremobilitycms.backend.repositories.ServiceitemRepository;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import it.windtre.tremobilitycms.ui.views.admin.service.ServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_SERVICE_ITEMS;

@Tag("serviceitems-view")
@HtmlImport("src/views/admin/serviceitems/serviceitems-view.html")
@Route(value = PAGE_SERVICE_ITEMS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_SERVICE_ITEMS)
@Secured(Role.ADMIN)
public class ServiceItemsView extends CrudView<Serviceitem, TemplateModel>
    implements PropertyChangeListener {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Serviceitem> grid;

    private final CrudEntityPresenter<Serviceitem> presenter;

    private final BeanValidationBinder<Serviceitem> binder = new BeanValidationBinder<>(Serviceitem.class);

    private ServiceitemForm serviceitemForm;
    private ServiceitemRepository serviceitemRepository;


    @Autowired
    public ServiceItemsView(CrudEntityPresenter<Serviceitem> presenter, ServiceitemForm form, ServiceitemRepository serviceitemRepository) {
        super(Serviceitem.getEntityName() /*EntityUtil.getName(Serviceitem.class)*/, form);
        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);

        super.addPropertyChangeListener(this);

        serviceitemForm = form;

        this.serviceitemRepository = serviceitemRepository;
    }

    private void setupGrid() {
        grid.addColumn(Serviceitem::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Serviceitem::getName).setWidth("180px").setHeader("Description").setFlexGrow(5);
        grid.addColumn(Serviceitem::getService).setWidth("180px").setHeader("ID Servizio").setFlexGrow(5);
        grid.addColumn(Serviceitem::getCity).setWidth("180px").setHeader("City").setFlexGrow(5);
        //grid.addColumn(Serviceitem::getServiceName).setWidth("180px").setHeader("Name").setFlexGrow(5);
    }

    @Override
    public Grid<Serviceitem> getGrid() {
        return grid;
    }

    @Override
    protected CrudEntityPresenter<Serviceitem> getPresenter() {
        return presenter;
    }

    @Override
    protected String getBasePage() {
        return PAGE_SERVICE_ITEMS;
    }

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Serviceitem> getBinder() {
        return binder;
    }


    /** property change */

    public void propertyChange(PropertyChangeEvent evt) {
        //to get newValue (String) evt.getNewValue();

        // generate new id and fill it
        Long id = Long.valueOf(serviceitemRepository.findAll().size() + 1);
        getPresenter().getEntity().setId(id);

        // update form UI
        serviceitemForm.getIdTF().setValue(String.valueOf(id));
    }
}
