package it.windtre.tremobilitycms.ui.views.admin.serviceitem;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.polymertemplate.EventHandler;


import it.windtre.tremobilitycms.backend.data.InfoZoneType;
import it.windtre.tremobilitycms.backend.data.DurationType;
import it.windtre.tremobilitycms.backend.data.entity.Service;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import it.windtre.tremobilitycms.backend.repositories.ServiceRepository;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.ArrayList;



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

    //TODO
    //@Id("infozone_zones")

    @Id("link_service")
    private Button link_service;

    private ServiceRepository serviceRepository = null;
    private final Dialog dialog = new Dialog();
    private Grid<Service> grid = new Grid<>();


    @Autowired
    public ServiceitemForm(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void setBinder(BeanValidationBinder<Serviceitem> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.bind(name, "name");
        binder.bind(description, "description");
        binder.bind(currency, "currency");

        /*// WHEN SERVICE WAS A COMBO
        List<Service> allServices = serviceRepository.findAll();
        ListDataProvider<String> servProvider = DataProvider.ofItems(getAllComboServices(allServices));
        service.setItemLabelGenerator(s -> s != null ? s : "");
        service.setDataProvider(servProvider);
        binder.forField(service).withConverter(new StringServiceItemToLongConverter()).bind("service");
        */
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

    @EventHandler
    private void linkServicePressed() {

        //UI.getCurrent().navigate(PAGE_SERVICESLIST);

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

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public HasText getTitle() {
        return title;
    }

}
