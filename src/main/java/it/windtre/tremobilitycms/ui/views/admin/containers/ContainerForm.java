package it.windtre.tremobilitycms.ui.views.admin.containers;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.windtre.tremobilitycms.backend.data.StateType;
import it.windtre.tremobilitycms.backend.data.entity.Container;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import it.windtre.tremobilitycms.ui.views.admin.service.LongConverter;
import it.windtre.tremobilitycms.ui.views.admin.products.IntegerConverter;

//import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Tag("container-form")
@HtmlImport("src/views/admin/containers/container-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContainerForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Container> {

    @Id("title")
    private H3 title;

    @Id("buttons")
    private FormButtonsBar buttons;

    @Id("id")
    private TextField id;
    @Id("color")
    private TextField colorTF;
    @Id("columns")
    private TextField columns;
    @Id("state")
    private ComboBox<String> state;

    @Id("upload_background_name")
    private TextField background_image_name;
    @Id("upload_background")
    private Upload background_image;



    @Autowired
    public ContainerForm() {
    }

    @Override
    public void setBinder(BeanValidationBinder<Container> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.bind(colorTF, "color");
        binder.forField(columns).withConverter(new IntegerConverter()).bind("columns");

        ListDataProvider<String> stateProvider = DataProvider.ofItems(StateType.getAllStateTypes());
        state.setItemLabelGenerator(s -> s != null ? s : "");
        state.setDataProvider(stateProvider);
        binder.bind(state, "state");

        binder.bind(background_image_name, "image");
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

                String basepath = "/opt/bitnami/apache-tomcat/webapps/funtablet/images/background/";

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
                //return null;
            }
        });
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
    public void changeColor(String color){
        System.out.print("selected color = " + color);
        if(!color.equalsIgnoreCase(this.colorTF.getValue())
                && !color.equalsIgnoreCase("")
                /*&& !this.colorTF.getValue().equalsIgnoreCase("")*/) {
            this.colorTF.setValue(color);
        }
    }

}
