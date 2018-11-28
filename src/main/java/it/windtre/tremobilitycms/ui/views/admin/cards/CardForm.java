package it.windtre.tremobilitycms.ui.views.admin.cards;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.StateType;
import it.windtre.tremobilitycms.backend.data.entity.Card;
import it.windtre.tremobilitycms.backend.data.entity.Element;
import it.windtre.tremobilitycms.backend.repositories.ElementRepository;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Tag("card-form")
@HtmlImport("src/views/admin/cards/card-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CardForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Card> {

    @Id("title")
    private H3 title;

    @Id("buttons")
    private FormButtonsBar buttons;

    @Id("id")
    private TextField id;

    @Id("element")
    private TextField element;

    @Id("action-launch")
    private TextField action_launch;
    @Id("action-mode")
    private TextField action_mode;

    @Id("card-background-color")
    private TextField card_background_color;
    @Id("upload_background_name")
    private TextField background_image_name;
    @Id("upload_background")
    private Upload background_image;

    @Id("upload_logo_name")
    private TextField logo_image_name;
    @Id("upload_logo")
    private Upload logo_image;

    @Id("description-align")
    private TextField description_align;
    @Id("card-description-color")
    private TextField card_description_color;
    @Id("description-font")
    private TextField description_font;
    @Id("description-font-size")
    private TextField description_font_size;
    @Id("description-font-style")
    private TextField description_font_style;
    @Id("description-text")
    private TextField description_text;

    @Id("title-align")
    private TextField title_align;
    @Id("card-title-color")
    private TextField card_title_color;
    @Id("title-font")
    private TextField title_font;
    @Id("title-font-size")
    private TextField title_font_size;
    @Id("title-font-style")
    private TextField title_font_style;
    @Id("title-text")
    private TextField title_text;

    @Id("state")
    private ComboBox<String> state;

    private ElementRepository elementRepository = null;
    private final Dialog dialog = new Dialog();
    private Grid<Element> grid = new Grid<>();

    @Autowired
    public CardForm(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    @Override
    public void setBinder(BeanValidationBinder<Card> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.forField(element).withConverter(new LongConverter()).bind("element");

        binder.bind(action_launch, "actionLaunch");
        binder.bind(action_mode,"actionMode");

        binder.bind(description_align, "descriptionAlign");
        binder.bind(card_description_color, "descriptionColor");
        binder.bind(description_font, "descriptionFont");
        binder.bind(description_font_size, "descriptionFontSize");
        binder.bind(description_font_style, "descriptionFontStyle");
        binder.bind(description_text, "descriptionText");

        binder.bind(title_align, "titleAlign");
        binder.bind(card_title_color, "titleColor");
        binder.bind(title_font, "titleFont");
        binder.bind(title_font_size, "titleFontSize");
        binder.bind(title_font_style, "titleFontStyle");
        binder.bind(title_text, "titleText");

        ListDataProvider<String> stateProvider = DataProvider.ofItems(StateType.getAllStateTypes());
        state.setItemLabelGenerator(s -> s != null ? s : "");
        state.setDataProvider(stateProvider);
        binder.bind(state, "state");

        binder.bind(card_background_color, "backgroundColor");

        binder.bind(background_image_name, "backgroundImage");
        background_image.addSucceededListener(event -> {
            background_image_name.setValue(event.getFileName());
        });
        background_image.addFailedListener(event -> {
            background_image_name.setValue(event.getFileName());
        });
        background_image.setReceiver(new Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                //String basepath = "/images/background/";
                //String basepath = Application.webinfFolder.getAbsolutePath() + "/background/";

                String basepath = "/opt/bitnami/apache-tomcat/webapps/tremobilitycms/images/background/";

                //LOGGER.log(Level.INFO, basepath);

                //title.setText(basepath);

                File file  = new File(basepath+filename);

                FileOutputStream fos = null;
                try{
                    fos = new FileOutputStream(file);
                }catch(Exception e){
                    //e.getMessage();
                    //LOGGER.log(Level.INFO, "COLORE " + picker_color.getColor());
                }
                return fos;
            }
        });

        binder.bind(logo_image_name, "logo");
        logo_image.addSucceededListener(event -> {
            logo_image_name.setValue(event.getFileName());
        });
        logo_image.addFailedListener(event -> {
            logo_image_name.setValue(event.getFileName());
        });
        logo_image.setReceiver(new Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                //String basepath = "/images/background/";
                //String basepath = Application.webinfFolder.getAbsolutePath() + "/background/";

                String basepath = "/opt/bitnami/apache-tomcat/webapps/tremobilitycms/images/background/";

                //LOGGER.log(Level.INFO, basepath);

                //title.setText(basepath);

                File file  = new File(basepath+filename);

                FileOutputStream fos = null;
                try{
                    fos = new FileOutputStream(file);
                }catch(Exception e){
                    //e.getMessage();
                    //LOGGER.log(Level.INFO, "COLORE " + picker_color.getColor());
                }
                return fos;
            }
        });

    }

    @EventHandler
    private void linkElementPressed() {

        grid.setItems(elementRepository.findAll());

        grid.addColumn(Element::getId).setHeader("ID");
        grid.addColumn(Element::getMode).setHeader("Mode");
        grid.addColumn(Element::getPosColumn).setHeader("Column");
        grid.addColumn(Element::getPosRow).setHeader("Row");
        grid.addColumn(Element::getPosSpan).setHeader("Span");

        grid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(entity -> {
                String el = entity.getId().toString();
                element.setValue(el);
                System.out.print("selected element with id = " + el);
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

    @ClientCallable
    public void changeCardBackgroundColor(String color){
        System.out.print("selected color = " + color);
        if(!color.equalsIgnoreCase(this.card_background_color.getValue())
                && !color.equalsIgnoreCase(""))
        {
            this.card_background_color.setValue(color);
        }
    }

    @ClientCallable
    public void changeCardDescriptionColor(String color){
        System.out.print("selected color = " + color);
        if(!color.equalsIgnoreCase(this.card_description_color.getValue())
                && !color.equalsIgnoreCase(""))
        {
            this.card_description_color.setValue(color);
        }
    }

    @ClientCallable
    public void changeCardTitleColor(String color){
        System.out.print("selected color = " + color);
        if(!color.equalsIgnoreCase(this.card_title_color.getValue())
                && !color.equalsIgnoreCase(""))
        {
            this.card_title_color.setValue(color);
        }
    }


    /** support method */

    public TextField getElementTF() {
        return element;
    }

    public TextField getIdTF() {
        return id;
    }

}
