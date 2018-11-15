package it.windtre.tremobilitycms.ui.views.admin.cards;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.Role;
import it.windtre.tremobilitycms.backend.data.entity.Card;
import it.windtre.tremobilitycms.backend.data.entity.util.EntityUtil;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.crud.CrudEntityPresenter;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.PAGE_CARDS;

@Tag("cards-view")
@HtmlImport("src/views/admin/cards/cards-view.html")
@Route(value = PAGE_CARDS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_CARDS)
@Secured(Role.ADMIN)
public class CardsView extends CrudView<Card, TemplateModel> {

    @Id("search")
    private SearchBar search;

    @Id("grid")
    private Grid<Card> grid;

    private final CrudEntityPresenter<Card> presenter;

    private final BeanValidationBinder<Card> binder = new BeanValidationBinder<>(Card.class);

    @Autowired
    public CardsView(CrudEntityPresenter<Card> presenter, CardForm form) {
        super(EntityUtil.getName(Card.class), form);
        this.presenter = presenter;
        form.setBinder(binder);

        setupEventListeners();
        setupGrid();
        presenter.setView(this);
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
}
