package it.windtre.tremobilitycms.ui.views;

import it.windtre.tremobilitycms.ui.components.ConfirmDialog;

public interface HasConfirmation {

	void setConfirmDialog(ConfirmDialog confirmDialog);

	ConfirmDialog getConfirmDialog();
}
