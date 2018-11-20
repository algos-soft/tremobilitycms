package it.windtre.tremobilitycms.ui.views.admin.zone;

import com.vaadin.flow.component.Tag;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_ZONES;

@Tag("zoneitems-view")
@HtmlImport("src/views/admin/zones/zoneitems-view.html")
@Route(value = PAGE_ZONES, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_ZONES)
@Secured(Role.ADMIN)
public class ZoneitemsView extends CrudView<Zoneitem, TemplateModel>
        implements AfterNavigationObserver, BeforeEnterObserver {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Zoneitem> grid;

    private final CrudEntityPresenter<Zoneitem> presenter;

    private final BeanValidationBinder<Zoneitem> binder = new BeanValidationBinder<>(Zoneitem.class);

    @Autowired
    public ZoneitemsView(CrudEntityPresenter<Zoneitem> presenter, ZoneitemForm form) {
        super(EntityUtil.getName(Zoneitem.class), form);
        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);
    }

    private void setupGrid() {
        grid.addColumn(Zoneitem::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Zoneitem::getName).setWidth("180px").setHeader("Name").setFlexGrow(5);
        grid.addColumn(Zoneitem::getPrice).setWidth("180px").setHeader("Price").setFlexGrow(5);
        grid.addColumn(Zoneitem::getCity).setWidth("180px").setHeader("City").setFlexGrow(5);
        grid.addColumn(Zoneitem::getServiceName).setWidth("180px").setHeader("Service").setFlexGrow(5);
        grid.addColumn(Zoneitem::getServiceitemName).setWidth("180px").setHeader("Serviceitem").setFlexGrow(5);
        grid.addColumn(Zoneitem::getServiceitem).setWidth("90px").setHeader("sitemId").setFlexGrow(2);
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String params = event.getLocation().getQueryParameters().getQueryString();
        System.out.println("ZoneitemsView BeforeEnter params = " + params);
        String[] arr = params.split("=");
        if (arr.length == 2) {
            if (arr[0].equalsIgnoreCase("serviceitemId")) {
                reloadDataSourceById(arr[1]);
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("ZoneitemsView AfterNavigationEvent fired");
    }


    private void reloadDataSourceById(String id) {
        System.out.println("filter by id = " + id);
        getPresenter().filter(id);
    }
}