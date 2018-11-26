/**
 *
 */
package it.windtre.tremobilitycms.ui.crud;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.app.HasLogger;
import it.windtre.tremobilitycms.backend.data.entity.AbstractEntity;
import it.windtre.tremobilitycms.ui.components.ConfirmDialog;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.components.SearchBar;
import it.windtre.tremobilitycms.ui.utils.TemplateUtil;
import it.windtre.tremobilitycms.ui.views.EntityView;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;


public abstract class CrudView<E extends AbstractEntity, T extends TemplateModel> extends PolymerTemplate<T>
		implements HasLogger, EntityView<E>, HasUrlParameter<Long> {

	public interface CrudForm<E> {
		FormButtonsBar getButtons();

		HasText getTitle();

		void setBinder(BeanValidationBinder<E> binder);
	}

	private final Dialog dialog = new Dialog();

	private ConfirmDialog confirmation;

	private final CrudForm<E> form;

	private final String entityName;

	protected abstract CrudEntityPresenter<E> getPresenter();

	protected abstract String getBasePage();

	protected abstract BeanValidationBinder<E> getBinder();

	protected abstract SearchBar getSearchBar();

	protected abstract Grid<E> getGrid();

	// non serve
	//protected QueryParameters params = null;

	private PropertyChangeSupport support;


	public CrudView(String entityName, CrudForm<E> form) {
		this.entityName = entityName;
		this.form = form;

		dialog.add((Component) getForm());

		dialog.setHeight("100%");
		// Workaround for https://github.com/vaadin/vaadin-dialog-flow/issues/28
		dialog.getElement().addAttachListener(event -> UI.getCurrent().getPage().executeJavaScript(
				"$0.$.overlay.setAttribute('theme', 'right');", dialog.getElement()));

		support = new PropertyChangeSupport(this);

	}

    public CrudForm<E> getForm() {
		return form;
    }

	public void setupEventListeners() {
		getGrid().addSelectionListener(e -> {
			e.getFirstSelectedItem().ifPresent(entity -> {
				navigateToEntity(entity.getId().toString());
				getGrid().deselectAll();
			});
		});

		getForm().getButtons().addSaveListener(e -> getPresenter().save());
		getForm().getButtons().addCancelListener(e -> getPresenter().cancel());

		getDialog().getElement().addEventListener("opened-changed", e -> {
			if (!getDialog().isOpened()) {
				getPresenter().cancel();
			}
		});

		getForm().getButtons().addDeleteListener(e -> getPresenter().delete());

		getSearchBar().addActionClickListener(e -> {
			getPresenter().createNew();
			support.firePropertyChange ("NewEntity", true, true);
		});
		getSearchBar()
				.addFilterChangeListener(e -> getPresenter().filter(getSearchBar().getFilter()));

		getSearchBar().setActionText("New " + entityName);
		getBinder().addValueChangeListener(e -> getPresenter().onValueChange(isDirty()));
	}

	protected void navigateToEntity(String id) {
		getUI().ifPresent(ui -> ui.navigate(TemplateUtil.generateLocation(getBasePage(), id)));

		/* non serve
		if (params != null) {
			params = null;
			getUI().ifPresent(ui -> ui.navigate(TemplateUtil.generateLocation(getBasePage(), id), params));
		} else {
			getUI().ifPresent(ui -> ui.navigate(TemplateUtil.generateLocation(getBasePage(), id)));
		}*/
	}

	@Override
	public ConfirmDialog getConfirmDialog() {
		return confirmation;
	}

	@Override
	public void setConfirmDialog(ConfirmDialog confirmDialog) {
		this.confirmation = confirmDialog;
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
		if (id != null) {
			getPresenter().loadEntity(id);
		} else if (getDialog().isOpened()) {
		    getPresenter().closeSilently();
        }
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void closeDialog() {
		getDialog().setOpened(false);
		navigateToEntity(null);
	}

	public void openDialog() {
		getDialog().setOpened(true);
	}

	public void updateTitle(boolean newEntity) {
		getForm().getTitle().setText((newEntity ? "New" : "Edit") + " " + entityName);

	}

	@Override
	public void write(E entity) throws ValidationException {
		getBinder().writeBean(entity);
	}

	@Override
	public boolean isDirty() {
		return getBinder().hasChanges();
	}

	@Override
	public void clear() {
		getBinder().readBean(null);
	}

	@Override
	public String getEntityName() {
		return entityName;
	}


	/** propertyChange */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}
}
