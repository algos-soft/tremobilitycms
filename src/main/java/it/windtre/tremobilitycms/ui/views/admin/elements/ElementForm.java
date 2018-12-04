package it.windtre.tremobilitycms.ui.views.admin.elements;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.StateType;
import it.windtre.tremobilitycms.backend.data.entity.Element;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.FormattingUtils;
import it.windtre.tremobilitycms.ui.utils.TemplateUtil;
import it.windtre.tremobilitycms.ui.views.admin.products.IntegerConverter;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.text.Format;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.*;

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

    @Id("manage_card_button")
    private Button manage_card_button;

    @Id("description")
    private TextField description;


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

        binder.bind(description, "description");

        setTooltips();
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public HasText getTitle() {
        return title;
    }


    /** manage card */
    @EventHandler
    private void manageCardPressed() {

        UI.getCurrent().close();

        String[] keys = {QPKEY_elementId};
        String[] values = {id.getValue()};
        QueryParameters params = TemplateUtil.buildQueryParams(keys, values);
        UI.getCurrent().navigate (PAGE_CARDS, params);
    }


    /** support method */

    public TextField getIdTF() {
        return id;
    }

    private void setTooltips() {
        FormattingUtils.setTooltip(description.getElement(), "Descrizione del componente");
        FormattingUtils.setTooltip(mode.getElement(), "Tipologia del componente");
        FormattingUtils.setTooltip(pos_column.getElement(), "Colonna dove il componente è posizionato");
        FormattingUtils.setTooltip(pos_row.getElement(), "Riga dove il componente è posizionato");
        FormattingUtils.setTooltip(pos_span.getElement(), "Numero di colonne occupate del componente sulla riga");
    }
}

