package it.windtre.tremobilitycms.ui;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.app.security.SecurityUtils;
import it.windtre.tremobilitycms.ui.components.AppNavigation;
import it.windtre.tremobilitycms.ui.components.ConfirmDialog;
import it.windtre.tremobilitycms.ui.entities.PageInfo;
import it.windtre.tremobilitycms.ui.exceptions.AccessDeniedException;
import it.windtre.tremobilitycms.ui.views.HasConfirmation;
import it.windtre.tremobilitycms.ui.views.admin.products.ProductsView;
import it.windtre.tremobilitycms.ui.views.admin.users.UsersView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static it.windtre.tremobilitycms.ui.utils.BakeryConst.*;

@Tag("main-view")
@HtmlImport("src/main-view.html")

@PageTitle("Gertrud")
@Viewport(VIEWPORT)
public class MainView extends PolymerTemplate<TemplateModel>
		implements RouterLayout, BeforeEnterObserver {

	@Id("appNavigation")
	private AppNavigation appNavigation;

	private final ConfirmDialog confirmDialog;

	@Autowired
	public MainView(ConfirmDialog confirmDialog) {
		this.confirmDialog = confirmDialog;

		List<PageInfo> pages = new ArrayList<>();

		//pages.add(new PageInfo(PAGE_STOREFRONT, ICON_STOREFRONT, TITLE_STOREFRONT));
		//pages.add(new PageInfo(PAGE_DASHBOARD, ICON_DASHBOARD, TITLE_DASHBOARD));
		if (SecurityUtils.isAccessGranted(UsersView.class)) {
			pages.add(new PageInfo(PAGE_USERS, ICON_USERS, TITLE_USERS));
		}
		/*if (SecurityUtils.isAccessGranted(ProductsView.class)) {
			pages.add(new PageInfo(PAGE_PRODUCTS, ICON_PRODUCTS, TITLE_PRODUCTS));
		}*/
		pages.add(new PageInfo(PAGE_SERVICES, ICON_SERVICES, TITLE_SERVICES));
		pages.add(new PageInfo(PAGE_SERVICE_ITEMS, ICON_SERVICE_ITEMS, TITLE_SERVICE_ITEMS));
		//pages.add(new PageInfo(PAGE_ZONES, ICON_ZONES, TITLE_ZONES));
		pages.add(new PageInfo(PAGE_CONTAINERS, ICON_CONTAINERS, TITLE_CONTAINERS));
		pages.add(new PageInfo(PAGE_ELEMENTS, ICON_ELEMENTS, TITLE_ELEMENTS));
		//pages.add(new PageInfo(PAGE_CARDS, ICON_CARDS, TITLE_CARDS));
		pages.add(new PageInfo(PAGE_LOGOUT, ICON_LOGOUT, TITLE_LOGOUT));

		appNavigation.init(pages, PAGE_DEFAULT, PAGE_LOGOUT);

		getElement().appendChild(confirmDialog.getElement());
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
			event.rerouteToError(AccessDeniedException.class);
		}
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		if (content != null) {
			getElement().appendChild(content.getElement());
		}

		this.confirmDialog.setOpened(false);
		if (content instanceof HasConfirmation) {
			((HasConfirmation) content).setConfirmDialog(this.confirmDialog);
		}
	}
}
