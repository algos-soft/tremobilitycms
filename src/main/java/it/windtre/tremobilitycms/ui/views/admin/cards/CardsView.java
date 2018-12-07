package it.windtre.tremobilitycms.ui.views.admin.cards;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Card;
import it.windtre.tremobilitycms.backend.repositories.CardRepository;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_CARDS;
import static it.windtre.tremobilitycms.ui.utils.BakeryConst.QPKEY_elementId;

@Tag("cards-view")
@HtmlImport("src/views/admin/cards/cards-view.html")
@Route(value = PAGE_CARDS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_CARDS)
@Secured(Role.ADMIN)
public class CardsView extends CrudView<Card, TemplateModel>
    implements AfterNavigationObserver, BeforeEnterObserver, PropertyChangeListener {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Card> grid;

    private final CrudEntityPresenter<Card> presenter;

    private final BeanValidationBinder<Card> binder = new BeanValidationBinder<>(Card.class);

    private String elementIdStr = null;
    private Long currentElementId = null;

    private CardForm cardForm = null;
    private CardRepository cardRepository = null;


    @Autowired
    public CardsView(CrudEntityPresenter<Card> presenter, CardForm form, CardRepository cardRepository) {
        super(Card.getEntityName() /*EntityUtil.getName(Card.class)*/, form);
        super.setAddItemButtonLabel("Nuova Card");
        super.setEntityFemale(true);

        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);

        super.addPropertyChangeListener(this);

        cardForm = form;

        this.cardRepository = cardRepository;
    }

    private void setupGrid() {
        grid.addColumn(Card::getId).setWidth("90px").setHeader("ID").setFlexGrow(2);
        grid.addColumn(Card::getElement).setWidth("270px").setHeader("Element").setFlexGrow(5);
        grid.addColumn(Card::getActionMode).setWidth("200px").setHeader("Action Mode").setFlexGrow(5);
        grid.addColumn(Card::getActionLaunch).setWidth("150px").setHeader("Action Launch").setFlexGrow(5);
    }

    @Override
    public Grid<Card> getGrid() {
        return grid;
    }

    @Override
    protected CrudEntityPresenter<Card> getPresenter() {
        return presenter;
    }

    @Override
    protected String getBasePage() {
        return PAGE_CARDS;
    }

    @Override
    public SearchBar getSearchBar() {
        return search;
    }

    @Override
    protected BeanValidationBinder<Card> getBinder() {
        return binder;
    }


    /** view life cycle */

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String params = event.getLocation().getQueryParameters().getQueryString();
        System.out.println("CardsView BeforeEnter params = " + params);
        elementIdStr = null;
        String[] arr = params.split("=");
        if (arr.length == 2) {
            if (arr[0].equalsIgnoreCase(QPKEY_elementId)) {
                elementIdStr = arr[1];
                currentElementId = Long.valueOf(elementIdStr);
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("CardsView AfterNavigationEvent fired");
        if (elementIdStr != null && !elementIdStr.isEmpty()) {
            reloadDataSourceById(elementIdStr);
        }
    }


    /** support method */

    private void reloadDataSourceById(String id) {
        System.out.println("reloadDataSource filter by id = " + id);
        getPresenter().filter(id);
    }


    /** property change */

    public void propertyChange(PropertyChangeEvent evt) {
        //to get newValue (String) evt.getNewValue();

        // auto fill serviceitem
        getPresenter().getEntity().setElement(currentElementId);

        // generate new id and fill it
        Long id = Long.valueOf(cardRepository.findAll().size() + 1);
        getPresenter().getEntity().setId(id);

        // update form UI
        cardForm.getElementTF().setValue(String.valueOf(currentElementId));
        cardForm.getIdTF().setValue(String.valueOf(id));
    }

}
