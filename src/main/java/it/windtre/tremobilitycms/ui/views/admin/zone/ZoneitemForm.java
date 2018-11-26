package it.windtre.tremobilitycms.ui.views.admin.zone;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.entity.Serviceitem;
import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import it.windtre.tremobilitycms.backend.repositories.ServiceitemRepository;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.views.admin.products.IntegerConverter;
import it.windtre.tremobilitycms.ui.views.admin.service.DoubleConverter;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Tag("zoneitem-form")
@HtmlImport("src/views/admin/zones/zoneitem-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ZoneitemForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Zoneitem> {

    @Id("title")
    private H3 title;

    @Id("buttons")
    private FormButtonsBar buttons;

    @Id("id")
    private TextField id;

    @Id("name")
    private TextField name;

    @Id("position")
    private TextField position;

    @Id("smstext")
    private TextField smstext;

    @Id("price")
    private TextField price;

    @Id("serviceitem")
    private TextField serviceitem;

    @Id("shape")
    private TextField shape;

    @Id("value")
    private TextField value;

    private ServiceitemRepository serviceitemRepository = null;
    private final Dialog dialog = new Dialog();
    private Grid<Serviceitem> grid = new Grid<>();

    @Autowired
    public ZoneitemForm(ServiceitemRepository serviceitemRepository) {
        this.serviceitemRepository = serviceitemRepository;
    }

    @Override
    public void setBinder(BeanValidationBinder<Zoneitem> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.bind(name, "name");
        binder.forField(position).withConverter(new IntegerConverter()).bind("position");
        binder.bind(smstext, "smstext");
        binder.forField(price).withConverter(new DoubleConverter()).bind("price");
        binder.bind(shape, "shape");
        binder.bind(value, "value");
        binder.forField(serviceitem).withConverter(new LongConverter()).bind("serviceitem");
    }

    @EventHandler
    private void linkServiceitemPressed() {

        grid.setItems(serviceitemRepository.findAll());
        grid.addColumn(Serviceitem::getId).setHeader("ID");
        grid.addColumn(Serviceitem::getName).setHeader("Name");
        grid.addColumn(Serviceitem::getDescription).setHeader("Description");
        grid.addColumn(Serviceitem::getDurationDescription).setHeader("Duration");
        grid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(entity -> {
                String srv = entity.getId().toString();
                serviceitem.setValue(srv);
                System.out.print("selected serviceitem with id = " + srv);
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

    public TextField getServiceitemTF() {
        return serviceitem;
    }
}
