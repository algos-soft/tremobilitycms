package it.windtre.tremobilitycms.ui.views.admin.products;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H1;
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
import it.windtre.tremobilitycms.backend.data.Camera;
import it.windtre.tremobilitycms.backend.data.Enable;
import it.windtre.tremobilitycms.backend.data.Visible;
import it.windtre.tremobilitycms.backend.data.entity.Workspace;
import it.windtre.tremobilitycms.ui.components.FormButtonsBar;
import it.windtre.tremobilitycms.ui.crud.CrudView;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

@Tag("product-form")
@HtmlImport("src/views/admin/products/product-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProductForm extends PolymerTemplate<TemplateModel> implements CrudView.CrudForm<Workspace> {
	@Id("title")
	private H1 title;

	@Id("buttons")
	private FormButtonsBar buttons;

	@Id("name")
	private TextField name;

	@Id("enabled")
	private ComboBox<String> enabled;

	@Id("title_label")
	private TextField title_label;

	@Id("company")
	private TextField company;

	@Id("subtitle_label")
	private TextField subtitle_label;

	@Id("footer_label")
	private TextField footer_label;

	@Id("title_visible")
	private ComboBox<String> title_visible;

	@Id("subtitle_visible")
	private ComboBox<String> subtitle_visible;

	@Id("footer_visible")
	private ComboBox<String> footer_visible;

	@Id("camera")
	private ComboBox<String> camera;

	@Id("flashing_enabled")
	private ComboBox<String> flashing_enabled;

	@Id("flashing_interval")
	private TextField flashing_interval;

	@Id("facebook_key")
	private TextField facebook_key;

	@Id("facebook_page")
	private TextField facebook_page;

	@Id("facebook_enabled")
	private ComboBox<String> facebook_enabled;

	@Id("upload_background_name")
	private TextField background_image_name;

	@Id("upload_background")
	private Upload background_image;

	@Id("upload_watermark_name")
	private TextField watermark_image_name;

	@Id("upload_watermark")
	private Upload watermark_image;

	@Id("subtitle_user_label")
	private TextField subtitle_user_label;

	@Id("title_user_label")
	private TextField title_user_label;

	@Id("title_user_visible")
	private ComboBox<String> title_user_visible;

	@Id("subtitle_user_visible")
	private ComboBox<String> subtitle_user_visible;

	@Id("title_color")
	private TextField title_color;

	@Id("subtitle_color")
	private TextField subtitle_color;

	@Id("title_user_color")
	private TextField title_user_color;

	@Id("subtitle_user_color")
	private TextField subtitle_user_color;

	private static final Logger LOGGER = Logger.getLogger( TypeData.ClassName.class.getName() );

	@Override
	public void setBinder(BeanValidationBinder<Workspace> binder) {
		binder.bind(name, "name");

		/*binder.forField(price).withConverter(new PriceConverter()).bind("price");
		price.setPattern("\\d+(\\.\\d?\\d?)?$");
		price.setPreventInvalidInput(true);

		String currencySymbol = Currency.getInstance(BakeryConst.APP_LOCALE).getSymbol();
		price.setPrefixComponent(new Span(currencySymbol));*/

		binder.bind(title_label, "titleLabel");
		binder.bind(title_user_label, "titleUserLabel");
		binder.bind(company, "company");
		binder.bind(subtitle_label, "subtitleLabel");
		binder.bind(subtitle_user_label, "subtitleUserLabel");
		binder.bind(footer_label, "footerLabel");
		binder.bind(title_visible, "titleVisible");
		binder.bind(title_user_visible, "titleUserVisible");
		binder.bind(subtitle_visible, "subtitleVisible");
		binder.bind(subtitle_user_visible, "subtitleUserVisible");
		binder.bind(footer_visible, "footerVisible");
		binder.bind(enabled, "enabled");
		binder.bind(camera, "camera");
		binder.bind(flashing_enabled, "flashingEnabled");
		binder.bind(background_image_name, "backgroundImageName");
		binder.bind(watermark_image_name, "watermarkImageName");
		binder.bind(facebook_key, "facebookKey");
		binder.bind(facebook_enabled, "facebookEnabled");
		binder.bind(facebook_page, "facebookPage");
		binder.bind(title_color, "titleColor");
		binder.bind(subtitle_color, "subtitleColor");
		binder.bind(title_user_color, "titleUserColor");
		binder.bind(subtitle_user_color, "subtitleUserColor");

		/*title_color.addValueChangeListener(event ->
				// Do something with the value
				Notification.show("Value is: " + event.getValue()));*/

		binder.forField(flashing_interval).withConverter(new IntegerConverter()).bind("flashingInterval");

		flashing_interval.setPattern("\\d+(\\.\\d?\\d?)?$");
		flashing_interval.setPreventInvalidInput(true);

		ListDataProvider<String> visibleProvider = DataProvider.ofItems(Visible.getAllVisibles());
		title_visible.setItemLabelGenerator(s -> s != null ? s : "VISIBLE");
		title_visible.setDataProvider(visibleProvider);

		title_user_visible.setItemLabelGenerator(s -> s != null ? s : "VISIBLE");
		title_user_visible.setDataProvider(visibleProvider);

		subtitle_visible.setItemLabelGenerator(s -> s != null ? s : "VISIBLE");
		subtitle_visible.setDataProvider(visibleProvider);

		subtitle_user_visible.setItemLabelGenerator(s -> s != null ? s : "VISIBLE");
		subtitle_user_visible.setDataProvider(visibleProvider);

		footer_visible.setItemLabelGenerator(s -> s != null ? s : "VISIBLE");
		footer_visible.setDataProvider(visibleProvider);

		ListDataProvider<String> enabledProvider = DataProvider.ofItems(Enable.getAllEnables());
		enabled.setItemLabelGenerator(s -> s != null ? s : "ENABLED");
		enabled.setDataProvider(enabledProvider);

		ListDataProvider<String> cameraProvider = DataProvider.ofItems(Camera.getAllCameras());
		camera.setItemLabelGenerator(s -> s != null ? s : "REAR");
		camera.setDataProvider(cameraProvider);

		flashing_enabled.setItemLabelGenerator(s -> s != null ? s : "ENABLED");
		flashing_enabled.setDataProvider(enabledProvider);

		facebook_enabled.setItemLabelGenerator(s -> s != null ? s : "ENABLED");
		facebook_enabled.setDataProvider(enabledProvider);

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
				//return null;
			}
		});


		watermark_image.addSucceededListener(event -> {
			watermark_image_name.setValue(event.getFileName());
		});
		watermark_image.addFailedListener(event -> {
			watermark_image_name.setValue(event.getFileName());
		});

		watermark_image.setReceiver(new Receiver() {
			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				//String basepath = "src/main/webapp/images/watermark/";

				String basepath = "/opt/bitnami/apache-tomcat/webapps/tremobilitycms/images/watermark/";

				File file  = new File(basepath+filename);

				FileOutputStream fos = null;
				try{
					fos = new FileOutputStream(file);
				}catch(Exception e){
					e.getMessage();
					//LOGGER.log(Level.INFO, picker_color.getColor());
				}
				return fos;
			}
		});

		//color1.setValue("#ef5350");
	}

	@Override
	public FormButtonsBar getButtons() {
		return buttons;
	}

	@Override
	public HasText getTitle() {
		return title;
	}

	/*@Override
	protected void onAttach(AttachEvent attachEvent) {
		getElement().addEventListener("colorTitleChanged", e -> {
			LOGGER.log(Level.INFO, "VAFFANCULO STRONZO");
		});
		super.onAttach(attachEvent);
	}*/

	/*@Override
	protected void onAttach(AttachEvent attachEvent) {
		getElement().addEventListener("colorTitleChanged", e -> {
			LOGGER.log(Level.INFO, "VAFFANCULO STRONZO");
		});
		super.onAttach(attachEvent);
	}

	@EventHandler
	private void colorTitleChanged() {
		System.out.println("Session info");
	}*/

	@ClientCallable
	public void changeTitleColor(String color){
		if(!color.equalsIgnoreCase(title_color.getValue()) && !color.equalsIgnoreCase("") &&
				!title_color.getValue().equalsIgnoreCase(""))
			title_color.setValue(color);
	}

	@ClientCallable
	public void changeSubtitleColor(String color){
		if(!color.equalsIgnoreCase(subtitle_color.getValue()) && !color.equalsIgnoreCase("") &&
				!subtitle_color.getValue().equalsIgnoreCase(""))
			subtitle_color.setValue(color);
	}

	@ClientCallable
	public void changeTitleUserColor(String color){
		if(!color.equalsIgnoreCase(title_user_color.getValue()) && !color.equalsIgnoreCase("") &&
				!title_user_color.getValue().equalsIgnoreCase(""))
			title_user_color.setValue(color);
	}

	@ClientCallable
	public void changeSubtitleUserColor(String color){
		if(!color.equalsIgnoreCase(subtitle_user_color.getValue()) && !color.equalsIgnoreCase("") &&
				!subtitle_user_color.getValue().equalsIgnoreCase(""))
			subtitle_user_color.setValue(color);
	}
}
