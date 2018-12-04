package it.windtre.tremobilitycms.ui.views.admin.serviceitem;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.router.*;

import it.windtre.tremobilitycms.backend.data.InfoZoneType;
import it.windtre.tremobilitycms.backend.data.DurationType;
import it.windtre.tremobilitycms.backend.data.entity.Service;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import it.windtre.tremobilitycms.backend.repositories.ServiceRepository;
import it.windtre.tremobilitycms.backend.repositories.ZoneRepository;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.FormattingUtils;
import it.windtre.tremobilitycms.ui.utils.TemplateUtil;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;


import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.*;


@Tag("serviceitem-form")
@HtmlImport("src/views/admin/serviceitems/serviceitem-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceitemForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Serviceitem> {

    @Id("title")
    private H3 title;

    @Id("buttons")
    private FormButtonsBar buttons;

    @Id("currency")
    private TextField currency;

    @Id("description")
    private TextField description;

    @Id("id")
    private TextField id;

    @Id("name")
    private TextField name;

    @Id("service")
    private TextField service;

    @Id("duration_description")
    private TextField duration_description;

    @Id("duration_interval")
    private TextField duration_interval;

    @Id("duration_name")
    private TextField duration_name;

    @Id("duration_type")
    private ComboBox<String> duration_type;

    @Id("infozone_name")
    private TextField infozone_name;

    @Id("infozone_typez")
    private ComboBox<String> infozone_typez;

    @Id("link_service")
    private Button link_service;

    @Id("manage_zone_button")
    private Button manage_zone_button;

    /** variables */

    private ServiceRepository serviceRepository = null;
    private final Dialog dialog = new Dialog();
    private Grid<Service> grid = new Grid<>();

    private ZoneRepository zoneRepository = null;
    private final Dialog zoneDialog = new Dialog();
    private Grid<Zoneitem> zoneGrid = new Grid<>();

    private final CrudEntityPresenter<Zoneitem> zonePresenter;


    @Autowired
    public ServiceitemForm(ServiceRepository serviceRepository, ZoneRepository zoneRepository, CrudEntityPresenter<Zoneitem> zonePresenter) {
        this.serviceRepository = serviceRepository;
        this.zoneRepository = zoneRepository;
        this.zonePresenter = zonePresenter;
    }

    @Override
    public void setBinder(BeanValidationBinder<Serviceitem> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.bind(name, "name");
        binder.bind(description, "description");
        binder.bind(currency, "currency");

        binder.forField(service).withConverter(new LongConverter()).bind("service");

        binder.bind(duration_description, "durationDescription");
        binder.forField(duration_interval).withConverter(new LongConverter()).bind("durationInterval");
        binder.bind(duration_name, "durationName");
        ListDataProvider<String> durationTypeProvider = DataProvider.ofItems(DurationType.getAllDurationTypes());
        duration_type.setItemLabelGenerator(s -> s != null ? s : "");
        duration_type.setDataProvider(durationTypeProvider);
        binder.bind(duration_type, "durationType");

        binder.bind(infozone_name, "infozoneName");
        ListDataProvider<String> zoneTypeProvider = DataProvider.ofItems(InfoZoneType.getAllZoneTypes());
        infozone_typez.setItemLabelGenerator(s -> s != null ? s : "");
        infozone_typez.setDataProvider(zoneTypeProvider);
        binder.bind(infozone_typez, "infozoneTypez");

        hideFields();
        setTooltips();
    }

    private String[] getAllComboServices(List<Service> services) {
        ArrayList<String> list = new ArrayList<>();
        list.add("");
        for (int i = 0; i < services.size(); ++i) {
            Service serv = services.get(i);
            String s = String.valueOf(serv.getId()); //+ "_" + serv.getCity() + "_" + serv.getName();
            list.add(s);
        }
        String[] arr = list.toArray(new String[list.size()]);
        return arr;
    }


    /** select service */

    @EventHandler
    private void linkServicePressed() {
        grid.setItems(serviceRepository.findAll());
        grid.addColumn(Service::getId).setHeader("ID");
        grid.addColumn(Service::getCity).setHeader("City");
        grid.addColumn(Service::getName).setHeader("Name");
        grid.addColumn(Service::getType).setHeader("Type");
        grid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(entity -> {
                String srv = entity.getId().toString();
                service.setValue(srv);
                System.out.print("selected service with id = " + srv);
                grid.deselectAll();
                dialog.close();
            });
        });

        dialog.add((Component) grid);
        dialog.setHeight("100%");
        dialog.getElement().addAttachListener(event -> UI.getCurrent().getPage().executeJavaScript(
                "$0.$.overlay.setAttribute('theme', 'left');", dialog.getElement()));
        dialog.open();
    }

    /** manage zone */

    @EventHandler
    private void manageZonePressed() {

        UI.getCurrent().close();

        /*Map<String,String> myMap = new HashMap<>();
        myMap.put("serviceitemId", id.getValue());
        QueryParameters params = QueryParameters.simple(myMap);
        System.out.println(params.getQueryString());
        */
        String[] keys = {QPKEY_serviceitemId};
        String[] values = {id.getValue()};
        QueryParameters params = TemplateUtil.buildQueryParams(keys, values);
        UI.getCurrent().navigate (PAGE_ZONES, params);
    }

    public void oldMethod() {
        /* // no more used
        Button addButton = new Button("Add Zone");
        addButton.getElement().setAttribute("theme", "primary");
        addButton.getElement().setAttribute("colspan", "2");
        addButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                System.out.print("addbutton clicked");

                //getUI().ifPresent(ui -> ui.navigate(PAGE_ZONES));
                //UI.getCurrent().navigate(PAGE_ZONES);
                UI.getCurrent().navigate(PAGE_ZONES);

                //zonePresenter.createNew();
                //openZoneitemForm("");
            }
        });

        Long serviceitemId = Long.valueOf(21); //Long.valueOf(this.getElement().getProperty("id"));
        zoneGrid.getElement().setAttribute("theme", "crud");
        zoneGrid.setItems(zoneRepository.findByServiceitem(serviceitemId));

        zoneGrid.addColumn(Zoneitem::getId).setHeader("ID");
        zoneGrid.addColumn(Zoneitem::getName).setHeader("Name");
        zoneGrid.addColumn(Zoneitem::getValue).setHeader("Value");
        zoneGrid.addColumn(Zoneitem::getPrice).setHeader("Price");

        zoneGrid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(entity -> {
                String z = entity.getId().toString();
                System.out.print("selected zoneitem with id = " + z);
                openZoneitemForm(z);
                zoneGrid.deselectAll();
                dialog.close();
            });
        });

        zoneDialog.add((Component) addButton);

        zoneDialog.add((Component) zoneGrid);
        zoneDialog.setHeight("100%");
        zoneDialog.getElement().addAttachListener(event -> UI.getCurrent().getPage().executeJavaScript(
                "$0.$.overlay.setAttribute('theme', 'left');", zoneDialog.getElement()));
        zoneDialog.open();
        */
    }

    public void openZoneitemForm(String zoneId) {
        getUI().ifPresent(ui -> ui.navigate(TemplateUtil.generateLocation(getBasePage(), zoneId)));
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public HasText getTitle() {
        return title;
    }

    protected String getBasePage() {
        return PAGE_ZONES;
    }


    /** support method */

    public TextField getIdTF() {
        return id;
    }

    private void hideFields() {
        duration_name.setVisible(false);
        infozone_name.setVisible(false);
        infozone_typez.setVisible(false);
    }

    private void setTooltips() {
        FormattingUtils.setTooltip(description.getElement(), "Descrizione del biglietto disponibile all’acquisto");
        FormattingUtils.setTooltip(name.getElement(), "Descrizione breve del servizio");
        FormattingUtils.setTooltip(duration_interval.getElement(), "Tempo di validità del biglietto espresso in minuti");
    }

}
