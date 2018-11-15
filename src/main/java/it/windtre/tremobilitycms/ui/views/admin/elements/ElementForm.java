package it.windtre.tremobilitycms.ui.views.admin.elements;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.StateType;
import it.windtre.tremobilitycms.backend.data.entity.Element;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.views.admin.products.IntegerConverter;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Tag("element-form")
@HtmlImport("src/views/admin/elements/element-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ElementForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Element> {

    @Id("title")
    private H3 title;

    @Id("buttons")
    private FormButtonsBar buttons;

    @Id("id")
    private TextField id;
    @Id("mode")
    private TextField mode;
    @Id("pos-column")
    private TextField pos_column;
    @Id("pos-row")
    private TextField pos_row;
    @Id("pos-span")
    private TextField pos_span;
    @Id("state")
    private ComboBox<String> state;


    @Autowired
    public ElementForm() {
    }

    @Override
    public void setBinder(BeanValidationBinder<Element> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.bind(mode, "mode");
        binder.forField(pos_column).withConverter(new IntegerConverter()).bind("posColumn");
        binder.forField(pos_row).withConverter(new IntegerConverter()).bind("posRow");
        binder.forField(pos_span).withConverter(new IntegerConverter()).bind("posSpan");

        ListDataProvider<String> stateProvider = DataProvider.ofItems(StateType.getAllStateTypes());
        state.setItemLabelGenerator(s -> s != null ? s : "");
        state.setDataProvider(stateProvider);
        binder.bind(state, "state");
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

