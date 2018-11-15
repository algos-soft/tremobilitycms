package it.windtre.tremobilitycms.ui.views.errors;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.ui.MainView;
import it.windtre.tremobilitycms.ui.exceptions.AccessDeniedException;
import it.windtre.tremobilitycms.ui.utils.BakeryConst;

import javax.servlet.http.HttpServletResponse;

@Tag("access-denied-view")
@HtmlImport("src/views/errors/access-denied-view.html")
@ParentLayout(MainView.class)
@PageTitle(BakeryConst.TITLE_ACCESS_DENIED)
public class AccessDeniedView extends PolymerTemplate<TemplateModel> implements HasErrorParameter<AccessDeniedException> {

	@Override
	public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<AccessDeniedException> errorParameter) {
		return HttpServletResponse.SC_FORBIDDEN;
	}
}
