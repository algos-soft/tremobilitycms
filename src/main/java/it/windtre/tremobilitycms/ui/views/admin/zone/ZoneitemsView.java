package it.windtre.tremobilitycms.ui.views.admin.zone;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.router.*;

import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.backend.repositories.ServiceitemRepository;
import it.windtre.tremobilitycms.backend.repositories.ZoneRepository;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import it.windtre.tremobilitycms.ui.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.awt.*;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Optional;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_ZONES;
import static it.windtre.tremobilitycms.ui.utils.BakeryConst.QPKEY_serviceitemId;

@Tag("zoneitems-view")
@HtmlImport("src/views/admin/zones/zoneitems-view.html")
@Route(value = PAGE_ZONES, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_ZONES)
@Secured(Role.ADMIN)
public class ZoneitemsView extends CrudView<Zoneitem, TemplateModel>
        implements AfterNavigationObserver, BeforeEnterObserver, PropertyChangeListener {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Zoneitem> grid;

    @Id("header-layout")
    private FormLayout headerLayout;

    @Id("header-tf")
    private TextField headerTF;


    /** variables */

    private final CrudEntityPresenter<Zoneitem> presenter;

    private final BeanValidationBinder<Zoneitem> binder = new BeanValidationBinder<>(Zoneitem.class);

    private String serviceitemIdStr = null;
    private Long currentServiceitemId = null;

    ZoneitemForm zoneForm = null;
    private ZoneRepository zoneRepository = null;
    private ServiceitemRepository serviceitemRepository = null;

    @Autowired
    public ZoneitemsView(CrudEntityPresenter<Zoneitem> presenter, ZoneitemForm form, ZoneRepository zoneRepository, ServiceitemRepository serviceitemRepository) {
        super(Zoneitem.getEntityName() /*EntityUtil.getName(Zoneitem.class)*/, form);
        super.setAddItemButtonLabel("Nuovo Dettaglio");

        this.presenter = presenter;
        form.setBinder(binder);

        this.setupEventListeners();
        setupGrid();
        presenter.setView(this);

        super.addPropertyChangeListener(this);

        zoneForm = form;

        this.zoneRepository = zoneRepository;
        this.serviceitemRepository = serviceitemRepository;
    }

    private void setupGrid() {
        grid.addColumn(Zoneitem::getId).setWidth("70px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Zoneitem::getName).setWidth("200px").setHeader("Name").setFlexGrow(4);
        grid.addColumn(Zoneitem::getPrice).setWidth("70px").setHeader("Price").setFlexGrow(4);
        grid.addColumn(Zoneitem::getCity).setWidth("100px").setHeader("City").setFlexGrow(4);
        grid.addColumn(Zoneitem::getServiceName).setWidth("180px").setHeader("Service").setFlexGrow(4);
        //grid.addColumn(Zoneitem::getServiceitemName).setWidth("180px").setHeader("Srvitm").setFlexGrow(4);
        grid.addColumn(Zoneitem::getServiceitem).setWidth("90px").setHeader("Id Biglietto").setFlexGrow(2);
    }


    @Override
    public Grid<Zoneitem> getGrid() {
        return grid;
    }

    @Override
    protected CrudEntityPresenter<Zoneitem> getPresenter() {
        return presenter;
    }

    @Override
    protected String getBasePage() {
        return PAGE_ZONES;
    }

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Zoneitem> getBinder() {
        return binder;
    }


    /** view life cycle */

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String params = event.getLocation().getQueryParameters().getQueryString();
        System.out.println("ZoneitemsView BeforeEnter params = " + params);
        serviceitemIdStr = null;
        String[] arr = params.split("=");
        if (arr.length == 2) {
            if (arr[0].equalsIgnoreCase(QPKEY_serviceitemId)) {
                serviceitemIdStr = arr[1];
                currentServiceitemId = Long.valueOf(serviceitemIdStr);
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("ZoneitemsView AfterNavigationEvent fired");
        if (serviceitemIdStr != null && !serviceitemIdStr.isEmpty()) {
            reloadDataSourceById(serviceitemIdStr);
            fillHeaderLabel();
        }
    }


    /** support method */

    private void reloadDataSourceById(String id) {
        System.out.println("reloadDataSource filter by id = " + id);
        getPresenter().filter(id);
    }

    private void fillHeaderLabel() {
        String headerText = "";
        Optional<Serviceitem> optSrv = serviceitemRepository.findById(currentServiceitemId);
        if (optSrv != null) {
            Serviceitem srv = optSrv.get();
            headerText = "ID Biglietto: " + String.valueOf(srv.getId()) + " - Descrizione: " + srv.getDescription() + " - City: " + srv.getCity();
        }
        headerTF.setValue(headerText);
    }


    /** property change */

    public void propertyChange(PropertyChangeEvent evt) {
        //to get newValue (String) evt.getNewValue();

        // auto fill serviceitem
        getPresenter().getEntity().setServiceitem(currentServiceitemId);

        // generate new id and fill it
        Long id = Long.valueOf(zoneRepository.findAll().size() + 1);
        getPresenter().getEntity().setId(id);

        // update form UI
        zoneForm.getServiceitemTF().setValue(String.valueOf(currentServiceitemId));
        zoneForm.getIdTF().setValue(String.valueOf(id));
    }

}