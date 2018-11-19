package it.windtre.tremobilitycms.app;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.windtre.tremobilitycms.backend.data.*;
import it.windtre.tremobilitycms.backend.data.entity.*;
import it.windtre.tremobilitycms.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SpringComponent
public class DataGenerator implements HasLogger {

	private static final String[] FILLING = new String[] { "Strawberry", "Chocolate", "Blueberry", "Raspberry",
			"Vanilla" };
	private static final String[] TYPE = new String[] { "Cake", "Pastry", "Tart", "Muffin", "Biscuit", "Bread", "Bagel",
			"Bun", "Brownie", "Cookie", "Cracker", "Cheese Cake" };
	private static final String[] FIRST_NAME = new String[] { "Ori", "Amanda", "Octavia", "Laurel", "Lael", "Delilah",
			"Jason", "Skyler", "Arsenio", "Haley", "Lionel", "Sylvia", "Jessica", "Lester", "Ferdinand", "Elaine",
			"Griffin", "Kerry", "Dominique" };
	private static final String[] LAST_NAME = new String[] { "Carter", "Castro", "Rich", "Irwin", "Moore", "Hendricks",
			"Huber", "Patton", "Wilkinson", "Thornton", "Nunez", "Macias", "Gallegos", "Blevins", "Mejia", "Pickett",
			"Whitney", "Farmer", "Henry", "Chen", "Macias", "Rowland", "Pierce", "Cortez", "Noble", "Howard", "Nixon",
			"Mcbride", "Leblanc", "Russell", "Carver", "Benton", "Maldonado", "Lyons" };

	private final Random random = new Random(1L);

	private UserRepository userRepository;
	private ProductRepository productRepository;
	private ServiceRepository serviceRepository;
	private ServiceitemRepository serviceitemRepository;
	private ZoneRepository zoneRepository;
	private ContainerRepository containerRepository;
	private ElementRepository elementRepository;
	private CardRepository cardRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public DataGenerator(UserRepository userRepository, ProductRepository productRepository, ServiceRepository serviceRepository, ServiceitemRepository serviceitemRepository, ZoneRepository zoneRepository, ContainerRepository containerRepository, ElementRepository elementRepository, CardRepository cardRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.serviceRepository = serviceRepository;
		this.serviceitemRepository = serviceitemRepository;
		this.zoneRepository = zoneRepository;
		this.containerRepository = containerRepository;
		this.elementRepository = elementRepository;
		this.cardRepository = cardRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	public void loadData() {
		if (userRepository.count() != 0L) {
			getLogger().info("Using existing database");
			return;
		}

		getLogger().info("Generating demo data");

		getLogger().info("... generating users");
		User baker = createBaker(userRepository, passwordEncoder);
		User barista = createBarista(userRepository, passwordEncoder);
		createAdmin(userRepository, passwordEncoder);
		// A set of products without constrains that can be deleted
		createDeletableUsers(userRepository, passwordEncoder);

		getLogger().info("... generating products");
		// A set of products that will be used for creating orders.
		Supplier<Workspace> productSupplier = createProducts(productRepository, 8);
		// A set of products without relationships that can be deleted
		createProducts(productRepository, 4);

		getLogger().info("... generating services, serviceitems and zones");
		createServices(serviceRepository, 4);
		createServiceitems(serviceitemRepository, 4);
		createZones(zoneRepository, 4);
		createContainers(containerRepository, 1);
		createElements(elementRepository, 3);
		createCards(cardRepository, 6);
	}


	private String getRandomPhone() {
		return "+1-555-" + String.format("%04d", random.nextInt(10000));
	}


	private LocalTime getRandomDueTime() {
		int time = 8 + 4 * random.nextInt(3);

		return LocalTime.of(time, 0);
	}

	private OrderState getRandomState(LocalDate due) {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		LocalDate twoDays = today.plusDays(2);

		if (due.isBefore(today)) {
			if (random.nextDouble() < 0.9) {
				return OrderState.DELIVERED;
			} else {
				return OrderState.CANCELLED;
			}
		} else {
			if (due.isAfter(twoDays)) {
				return OrderState.NEW;
			} else if (due.isAfter(tomorrow)) {
				// in 1-2 days
				double resolution = random.nextDouble();
				if (resolution < 0.8) {
					return OrderState.NEW;
				} else if (resolution < 0.9) {
					return OrderState.PROBLEM;
				} else {
					return OrderState.CANCELLED;
				}
			} else {
				double resolution = random.nextDouble();
				if (resolution < 0.6) {
					return OrderState.READY;
				} else if (resolution < 0.8) {
					return OrderState.DELIVERED;
				} else if (resolution < 0.9) {
					return OrderState.PROBLEM;
				} else {
					return OrderState.CANCELLED;
				}
			}
		}
	}

	private <T> T getRandom(T[] array) {
		return array[random.nextInt(array.length)];
	}

	private Supplier<Workspace> createProducts(ProductRepository productsRepo, int numberOfItems) {
		List<Workspace> products  = new ArrayList<>();
		for (int i = 0; i < numberOfItems; i++) {
			Workspace product = new Workspace();
			product.setName(getRandomProductName());
			double doublePrice = 2.0 + random.nextDouble() * 100.0;
			//product.setPrice((int) (doublePrice * 100.0));
			products.add(productsRepo.save(product));
		}
		return () -> {
			double cutoff = 2.5;
			double g = random.nextGaussian();
			g = Math.min(cutoff, g);
			g = Math.max(-cutoff, g);
			g += cutoff;
			g /= (cutoff * 2.0);
			return products.get((int) (g * (products.size() - 1)));
		};
	}

	private String getRandomProductName() {
		String firstFilling = getRandom(FILLING);
		String name;
		if (random.nextBoolean()) {
			String secondFilling;
			do {
				secondFilling = getRandom(FILLING);
			} while (secondFilling.equals(firstFilling));

			name = firstFilling + " " + secondFilling;
		} else {
			name = firstFilling;
		}
		name += " " + getRandom(TYPE);

		return name;
	}

	private User createBaker(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("baker@vaadin.com", "Heidi", "Carter", passwordEncoder.encode("baker"),
				Role.BAKER, "https://randomuser.me/api/portraits/women/76.jpg", false));
	}

	private User createBarista(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository
				.save(createUser("barista@vaadin.com", "Malin", "Castro", passwordEncoder.encode("barista"),
						Role.BARISTA, "https://randomuser.me/api/portraits/women/89.jpg", true));
	}

	private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("admin@vaadin.com", "Göran", "Rich", passwordEncoder.encode("admin"),
				Role.ADMIN, "https://randomuser.me/api/portraits/men/34.jpg", true));
	}

	private void createDeletableUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		userRepository
				.save(createUser("peter@vaadin.com", "Peter", "Bush", passwordEncoder.encode("peter"), Role.BARISTA,
				"https://randomuser.me/api/portraits/men/10.jpg", false));
		userRepository.save(createUser("mary@vaadin.com", "Mary", "Ocon", passwordEncoder.encode("mary"), Role.BAKER,
				"https://randomuser.me/api/portraits/women/40.jpg", true));
	}

	private User createUser(String email, String firstName, String lastName, String passwordHash, String role,
			String photoUrl, boolean locked) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPasswordHash(passwordHash);
		user.setRole(role);
		user.setPhotoUrl(photoUrl);
		user.setLocked(locked);
		return user;
	}

	private void createServices(ServiceRepository servicesRepo, int numberOfItems) {
		List<Service> services  = new ArrayList<>();
		List<String> cities = new ArrayList<>( Arrays.asList("Milano", "Roma", "Bologna"));
		List<String> names = new ArrayList<>( Arrays.asList("ATM", "ATAC", "TPER"));
		List<String> types = new ArrayList<>( Arrays.asList(ServiceType.TICKETING, ServiceType.ZTL, ServiceType.PARKING));

		for (int i = 0; i < names.size(); i++) {
			Service service = new Service();
			service.setId(Long.valueOf(i));
			service.setCity(cities.get(i));
			service.setName(names.get(i));
			service.setType(types.get(i));
			services.add(servicesRepo.save(service));
		}
	}


	private void createServiceitems(ServiceitemRepository serviceitemsRepo, int numberOfItems) {
		List<Serviceitem> serviceitems  = new ArrayList<>();
		List<String> names = new ArrayList<>( Arrays.asList("Sosta oraria", "Sosta giornaliera"));
		List<String> durations = new ArrayList<>( Arrays.asList("1 ora", "8 ore"));

		for (int i = 0; i < names.size(); i++) {
			Serviceitem serviceit = new Serviceitem();
			serviceit.setId(Long.valueOf(i));
			serviceit.setName(names.get(i));
			serviceit.setDurationDescription(durations.get(i));
			serviceitems.add(serviceitemsRepo.save(serviceit));
		}
	}

	private void createZones(ZoneRepository zonesRepo, int numberOfItems) {
		List<Zoneitem> zoneitems = new ArrayList<>();
		List<Long> ids = new ArrayList<>( Arrays.asList(Long.valueOf(21),Long.valueOf(21),Long.valueOf(22)));
		List<String> names = new ArrayList<>( Arrays.asList("zona 1", "zona 2", "zona 3"));
		List<String> prices = new ArrayList<>( Arrays.asList("1", "1.5", "2.0"));

		for (int i = 0; i < names.size(); i++) {
			Zoneitem zoneitem = new Zoneitem();
			zoneitem.setId(Long.valueOf(i));
			zoneitem.setServiceitem(ids.get(i));
			zoneitem.setName(names.get(i));
			zoneitem.setPrice(Double.valueOf(prices.get(i)));
			zoneitems.add(zonesRepo.save(zoneitem));
		}
	}

	private void createContainers(ContainerRepository containerRepository, int numberOfItems) {
		Container cont = new Container();
		cont.setId(Long.valueOf(1));
		cont.setState(StateType.VISIBLE);
		containerRepository.save(cont);
	}

	private void createElements(ElementRepository elementRepository, int numberOfItems) {
		List<Element> elements = new ArrayList<>();
		List<String> modes = new ArrayList<>( Arrays.asList(ElementModeType.BANNER, ElementModeType.BUTTON, ElementModeType.BUTTON));
		List<String> cols = new ArrayList<>( Arrays.asList("1", "1", "2"));
		List<String> rows = new ArrayList<>( Arrays.asList("1", "1", "2"));
		List<String> spans = new ArrayList<>( Arrays.asList("2", "1", "1"));

		for (int i = 0; i < modes.size(); i++) {
			Element el = new Element();
			el.setId(Long.valueOf(i));
			el.setMode(modes.get(i));
			el.setPosColumn(Integer.valueOf(cols.get(i)));
			el.setPosRow(Integer.valueOf(rows.get(i)));
			el.setPosSpan(Integer.valueOf(spans.get(i)));
			el.setState(StateType.VISIBLE);
			elements.add(elementRepository.save(el));
		}
	}

	private void createCards(CardRepository cardRepository, int numberOfItems) {
		List<Card> cards = new ArrayList<>();
		List<String> modes = new ArrayList<>( Arrays.asList(CardActionModeType.INTERNAL, CardActionModeType.INTERNAL, CardActionModeType.EXTERNAL));
		List<String> actions = new ArrayList<>( Arrays.asList("openGoquickNews", "openTicket", "openZtl"));

		for (int i = 0; i < modes.size(); i++) {
			Card c = new Card();
			c.setId(Long.valueOf(i));
			c.setActionMode(modes.get(i));
			c.setActionLaunch(actions.get(i));
			cards.add(cardRepository.save(c));
		}
	}

}
