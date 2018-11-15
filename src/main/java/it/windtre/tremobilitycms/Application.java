package it.windtre.tremobilitycms;

import it.windtre.tremobilitycms.app.security.SecurityConfiguration;
import it.windtre.tremobilitycms.backend.data.entity.User;
import it.windtre.tremobilitycms.backend.repositories.UserRepository;
import it.windtre.tremobilitycms.backend.service.UserService;
import it.windtre.tremobilitycms.ui.MainView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring boot web application initializer.
 */
@SpringBootApplication(scanBasePackageClasses = { SecurityConfiguration.class, MainView.class, Application.class,
		UserService.class }, exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@EntityScan(basePackageClasses = { User.class })
public class Application extends SpringBootServletInitializer {
	public static ApplicationContext ctx;
	//public static File webinfFolder;

	public static void main(String[] args) {
		ctx = SpringApplication.run(Application.class, args);

		//WebApplicationContext context = (WebApplicationContext)ctx;
		//webinfFolder = new File ( ((WebApplicationContext) ctx).getServletContext().getRealPath("/images") );

		//ctxWeb = ((WebApplicationContext) ctx).getServletContext().getRealPath("/images");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}
