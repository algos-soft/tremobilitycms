package it.windtre.tremobilitycms.ui.views.admin.zone;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.router.*;

import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import it.windtre.tremobilitycms.ui.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

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

    private final CrudEntityPresenter<Zoneitem> presenter;

    private final BeanValidationBinder<Zoneitem> binder = new BeanValidationBinder<>(Zoneitem.class);

    private String serviceitemIdStr = null;


    @Autowired
    public ZoneitemsView(CrudEntityPresenter<Zoneitem> presenter, ZoneitemForm form) {
        super(EntityUtil.getName(Zoneitem.class), form);
        this.presenter = presenter;
        form.setBinder(binder);

        this.setupEventListeners();
        setupGrid();
        presenter.setView(this);
    }

    private void setupGrid() {
        grid.addColumn(Zoneitem::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Zoneitem::getName).setWidth("180px").setHeader("Name").setFlexGrow(5);
        grid.addColumn(Zoneitem::getPrice).setWidth("180px").setHeader("Price").setFlexGrow(5);
        //grid.addColumn(Zoneitem::getCity).setWidth("180px").setHeader("City").setFlexGrow(5);
        //grid.addColumn(Zoneitem::getServiceName).setWidth("180px").setHeader("Service").setFlexGrow(5);
        //grid.addColumn(Zoneitem::getServiceitemName).setWidth("180px").setHeader("Serviceitem").setFlexGrow(5);
        grid.addColumn(Zoneitem::getServiceitem).setWidth("90px").setHeader("sitemId").setFlexGrow(2);
    }

    // non serve
    /*@Override
    public void setupEventListeners() {
        String[] keys = {QPKEY_serviceitemId};
        String[] values = {serviceitemIdStr};
        QueryParameters params = TemplateUtil.buildQueryParams(keys, values);
        super.params = params;
        super.setupEventListeners();
        //super.params = null;
    }*/

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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String params = event.getLocation().getQueryParameters().getQueryString();
        System.out.println("ZoneitemsView BeforeEnter params = " + params);
        serviceitemIdStr = null;
        String[] arr = params.split("=");
        if (arr.length == 2) {
            if (arr[0].equalsIgnoreCase(QPKEY_serviceitemId)) {
                serviceitemIdStr = arr[1];
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("ZoneitemsView AfterNavigationEvent fired");
        if (serviceitemIdStr != null && !serviceitemIdStr.isEmpty()) {
            reloadDataSourceById(serviceitemIdStr);
        }
    }


    /** support method */

    private void reloadDataSourceById(String id) {
        System.out.println("reloadDataSource filter by id = " + id);
        getPresenter().filter(id);
    }


    /** property change */

    public void propertyChange(PropertyChangeEvent evt) {
        //this.setNews((String) evt.getNewValue());
        getPresenter().getEntity().setServiceitem(Long.valueOf(serviceitemIdStr));
        getPresenter().save();
        
    }

}