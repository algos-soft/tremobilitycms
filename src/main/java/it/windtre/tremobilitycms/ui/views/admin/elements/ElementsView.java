package it.windtre.tremobilitycms.ui.views.admin.elements;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Element;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.backend.repositories.ElementRepository;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_ELEMENTS;

@Tag("elements-view")
@HtmlImport("src/views/admin/elements/elements-view.html")
@Route(value = PAGE_ELEMENTS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_ELEMENTS)
@Secured(Role.ADMIN)
public class ElementsView extends CrudView<Element, TemplateModel>
    implements PropertyChangeListener {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Element> grid;

    private final CrudEntityPresenter<Element> presenter;

    private final BeanValidationBinder<Element> binder = new BeanValidationBinder<>(Element.class);

    private ElementForm elementForm;
    private ElementRepository elementRepository;


    @Autowired
    public ElementsView(CrudEntityPresenter<Element> presenter, ElementForm form, ElementRepository elementRepository) {
        super(Element.getEntityName() /*EntityUtil.getName(Element.class)*/, form);
        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);

        super.addPropertyChangeListener(this);

        elementForm = form;

        this.elementRepository = elementRepository;
    }

    private void setupGrid() {
        grid.addColumn(Element::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Element::getMode).setWidth("270px").setHeader("Mode").setFlexGrow(5);
        grid.addColumn(Element::getPosColumn).setWidth("270px").setHeader("Column").setFlexGrow(5);
        grid.addColumn(Element::getPosRow).setWidth("270px").setHeader("Row").setFlexGrow(5);
        grid.addColumn(Element::getPosSpan).setWidth("270px").setHeader("Span").setFlexGrow(5);
    }

    @Override
    public Grid<Element> getGrid() {
        return grid;
    }

    @Override
    protected CrudEntityPresenter<Element> getPresenter() {
        return presenter;
    }

    @Override
    protected String getBasePage() {
        return PAGE_ELEMENTS;
    }

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Element> getBinder() {
        return binder;
    }


    /** property change */

    public void propertyChange(PropertyChangeEvent evt) {
        //to get newValue (String) evt.getNewValue();

        // generate new id and fill it
        Long id = Long.valueOf(elementRepository.findAll().size() + 1);
        getPresenter().getEntity().setId(id);

        // update form UI
        elementForm.getIdTF().setValue(String.valueOf(id));
    }
}

