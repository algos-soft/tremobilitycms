package it.windtre.tremobilitycms.ui.views.admin.service;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H3;
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
import it.windtre.tremobilitycms.backend.data.ServiceType;
import it.windtre.tremobilitycms.backend.data.entity.Service;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Tag("service-form")
@HtmlImport("src/views/admin/services/service-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Service> {

    @Id("title")
    private H3 title;

    @Id("buttons")
    private FormButtonsBar buttons;

    @Id("city")
    private TextField city;

    @Id("lat")
    private TextField latitude;

    @Id("lon")
    private TextField longitude;

    @Id("code")
    private TextField code;

    @Id("email")
    private TextField email;

    @Id("id")
    private TextField id;

    @Id("info")
    private TextField info;

    @Id("name")
    private TextField name;

    @Id("sender")
    private TextField sender;

    @Id("sms")
    private TextField sms;

    @Id("telephone")
    private TextField telephone;

    @Id("web")
    private TextField web;

    @Id("type")
    private ComboBox<String> type;

    @Id("upload_icon_name")
    private TextField icon_image_name;
    @Id("upload_icon")
    private Upload icon_image;


    @Autowired
    public ServiceForm() {
    }

    @Override
    public void setBinder(BeanValidationBinder<Service> binder) {
        binder.forField(id).withConverter(new LongConverter()).bind("id");
        binder.bind(city, "city");
        binder.forField(latitude).withConverter(new DoubleConverter()).bind("lat");
        binder.forField(longitude).withConverter(new DoubleConverter()).bind("lon");
        binder.bind(name, "name");
        binder.bind(code, "code");
        binder.bind(email, "email");
        binder.bind(info, "info");
        binder.bind(sender, "sender");
        binder.bind(sms, "sms");
        binder.bind(telephone, "telephone");
        binder.bind(web, "web");

        ListDataProvider<String> serviceTypeProvider = DataProvider.ofItems(ServiceType.getAllServiceTypes());
        type.setItemLabelGenerator(s -> s != null ? s : "");
        type.setDataProvider(serviceTypeProvider);
        binder.bind(type, "type");

        binder.bind(icon_image_name, "icon");
        icon_image.addSucceededListener(event -> {
            icon_image_name.setValue(event.getFileName());
        });
        icon_image.addFailedListener(event -> {
            icon_image_name.setValue(event.getFileName());
        });
        icon_image.setReceiver(new Receiver() {
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

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public HasText getTitle() {
        return title;
    }

}
