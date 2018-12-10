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

		/*getLogger().info("... generating users");
		User baker = createOperator(userRepository, passwordEncoder);
		User barista = createReadonlyUser(userRepository, passwordEncoder);
		createAdmin(userRepository, passwordEncoder);
		// A set of products without constrains that can be deleted
		createDeletableUsers(userRepository, passwordEncoder);

		getLogger().info("... generating products");
		// A set of products that will be used for creating orders.
		Supplier<Workspace> productSupplier = createProducts(productRepository, 8);
		// A set of products without relationships that can be deleted
		createProducts(productRepository, 4);
		*/

		getLogger().info("... generating users");
		User vanni = createUser("vanni.casari@windtre.it", "Vanni", "Casari", passwordEncoder.encode("admin"),
				Role.ADMIN, "https://randomuser.me/api/portraits/men/34.jpg", true);
		User daniele = createUser("daniele.marabese@windtre.it", "Daniele", "Marabese", passwordEncoder.encode("admin"),
				Role.ADMIN, "https://randomuser.me/api/portraits/men/35.jpg", true);
		User corrado = createUser("corrado.tonini@windtre.it", "Corrado", "Tonini", passwordEncoder.encode("operator"),
				Role.OPERATOR, "https://randomuser.me/api/portraits/men/21.jpg", false);
		User filomena = createUser("filomena.fortino@windtre.it", "Filomena", "Fortino", passwordEncoder.encode("operator"),
				Role.OPERATOR, "https://randomuser.me/api/portraits/woman/20.jpg", false);
		User marialaura = createUser("marialaura.mele@windtre.it", "Marialaura", "Mele", passwordEncoder.encode("admin"),
				Role.ADMIN, "https://randomuser.me/api/portraits/woman/24.jpg", false);
		User develop = createUser("admin@admin.it", "Admin", "Sviluppo", passwordEncoder.encode("admin"),
				Role.ADMIN, "https://randomuser.me/api/portraits/woman/24.jpg", false);
		ArrayList<User> users = new ArrayList<>();
		users.add(vanni);
		users.add(daniele);
		users.add(corrado);
		users.add(filomena);
		users.add(marialaura);
		users.add(develop);
		saveUsers(userRepository, passwordEncoder, users);

		getLogger().info("... generating services, serviceitems, zones, containers, elements and cards");
		createServices(serviceRepository, 4);
		createServiceitems(serviceitemRepository, 4);
		createZones(zoneRepository, 4);
		createContainers(containerRepository, 1);
		createElements(elementRepository, 3);
		createCards(cardRepository, 6);
	}


	/** support method */

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


	/** products */

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


	/** Users */

	private User createOperator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("corrado@vaadin.com", "Corrado", "Carter", passwordEncoder.encode("baker"),
				Role.READONLY, "https://randomuser.me/api/portraits/women/76.jpg", false));
	}

	private User createReadonlyUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository
				.save(createUser("barista@vaadin.com", "Malin", "Castro", passwordEncoder.encode("barista"),
						Role.OPERATOR, "https://randomuser.me/api/portraits/women/89.jpg", true));
	}

	private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("admin@vaadin.com", "Göran", "Rich", passwordEncoder.encode("admin"),
				Role.ADMIN, "https://randomuser.me/api/portraits/men/34.jpg", true));
	}

	private void createDeletableUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		userRepository
				.save(createUser("peter@vaadin.com", "Peter", "Bush", passwordEncoder.encode("peter"), Role.OPERATOR,
				"https://randomuser.me/api/portraits/men/10.jpg", false));
		userRepository.save(createUser("mary@vaadin.com", "Mary", "Ocon", passwordEncoder.encode("mary"), Role.READONLY,
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

	private User saveUser(UserRepository userRepository, PasswordEncoder passwordEncoder, User user) {
		return userRepository.save(user);
	}

	private ArrayList<User> saveUsers(UserRepository userRepository, PasswordEncoder passwordEncoder, ArrayList<User> users) {
		ArrayList<User> saved = new ArrayList<>();
		for (User u : users) {
			saved.add(userRepository.save(u));
		}
		return saved;
	}

	/** services */

	private void createServices(ServiceRepository servicesRepo, int numberOfItems) {
		List<Service> services  = new ArrayList<>();
		List<String> cities = new ArrayList<>( Arrays.asList("Milano", "Alessandria"));
		List<String> names = new ArrayList<>( Arrays.asList("ATM", "AMAG"));
		List<String> types = new ArrayList<>( Arrays.asList(ServiceType.TICKETING, ServiceType.PARKING));
		List<String> icons = new ArrayList<>( Arrays.asList("GoQuick/logo/atm.png", "GoQuick/logo/amag.png"));
		List<String> infos = new ArrayList<>( Arrays.asList("Ticketing/PopUp/popup17.html", ""));
		List<String> senders = new ArrayList<>( Arrays.asList("ATM Milano", "AMAG Mobilità"));
		List<String> msgs = new ArrayList<>( Arrays.asList("48444", "4880807"));
		List<String> telephones = new ArrayList<>( Arrays.asList("0248607607", "0131323811"));
		List<String> emails = new ArrayList<>( Arrays.asList("", "", ""));
		List<String> webs = new ArrayList<>( Arrays.asList("http://www.atm.it", "http://www.amagmobilita.it/trasporti/it/home_page.aspx"));
		List<String> ids = new ArrayList<>( Arrays.asList("1", "1", "1"));

		for (int i = 0; i < names.size(); i++) {
			Service service = new Service();
			service.setId(Long.valueOf(ids.get(i)));
			service.setCity(cities.get(i));
			service.setName(names.get(i));
			service.setType(types.get(i));
			service.setIcon(icons.get(i));
			service.setInfo(infos.get(i));
			service.setSender(senders.get(i));
			service.setSms(msgs.get(i));
			service.setTelephone(telephones.get(i));
			service.setEmail(emails.get(i));
			service.setWeb(webs.get(i));
			services.add(servicesRepo.save(service));
		}
	}

	/** serviceitems */

	private void createServiceitems(ServiceitemRepository serviceitemsRepo, int numberOfItems) {
		List<Serviceitem> serviceitems  = new ArrayList<>();
		List<String> names = new ArrayList<>( Arrays.asList("Urbano", "Extraurbano (Rho Fiera)", "Parking 60 minuti", "Parking 30 minuti"));
		List<String> durations = new ArrayList<>( Arrays.asList("90 minuti", "105 minuti", "60 minuti", "30 minuti"));
		List<String> cities = new ArrayList<>( Arrays.asList("ATM - Milano", "ATM - Milano", "AMAG - Alessandria", "AMAG - Alessandria"));
		List<String> types = new ArrayList<>( Arrays.asList("fromPurchaseTime", "fromPurchaseTime", "fromPurchaseTime", "fromPurchaseTime"));
		List<String> serviceIds = new ArrayList<>( Arrays.asList("7", "7", "8", "8"));
		List<String> ids = new ArrayList<>( Arrays.asList("10", "11", "12", "13"));

		for (int i = 0; i < names.size(); i++) {
			Serviceitem serviceit = new Serviceitem();
			serviceit.setId(Long.valueOf(ids.get(i)));
			serviceit.setName(names.get(i));
			serviceit.setDurationType(types.get(i));
			serviceit.setDurationDescription(durations.get(i));
			serviceit.setCurrency("€");
			serviceit.setService(Long.valueOf(serviceIds.get(i)));
			serviceit.setCity(cities.get(i));
			serviceit.setDescription(names.get(i));
			serviceitems.add(serviceitemsRepo.save(serviceit));
		}
	}


	/** zones */

	private void createZones(ZoneRepository zonesRepo, int numberOfItems) {
		List<Zoneitem> zoneitems = new ArrayList<>();
		//List<Long> ids = new ArrayList<>( Arrays.asList(Long.valueOf(21),Long.valueOf(21),Long.valueOf(22)));
		List<String> names = new ArrayList<>( Arrays.asList("Biglietto Urbano di Milano", "Zona 1", "Zona 2"));
		List<String> values = new ArrayList<>( Arrays.asList("", "1", "2"));
		List<String> smstexts = new ArrayList<>(Arrays.asList("ATM.", "<targa> Z1", "<targa> Z2 M"));
		List<String> prices = new ArrayList<>( Arrays.asList("1.5", "1.0", "1.0"));
		List<String> ids = new ArrayList<>(Arrays.asList("10", "11", "11"));
		List<String> orders = new ArrayList<>(Arrays.asList("1", "1", "2"));
		List<String> serviceitemnames = new ArrayList<>(Arrays.asList("Biglietto Urbano", "Parking 60 minuti", "Parking 60 minuti"));
		List<String> servicenames = new ArrayList<>(Arrays.asList("ATM - Milano", "AMAG - Alessandria", "AMAG - Alessandria"));
		List<String> cities = new ArrayList<>(Arrays.asList("Milano", "Alessandria", "Alessandria"));

		for (int i = 0; i < names.size(); i++) {
			Zoneitem zoneitem = new Zoneitem();
			zoneitem.setId(Long.valueOf(i));
			zoneitem.setServiceitem(Long.valueOf(ids.get(i)));
			zoneitem.setName(names.get(i));
			zoneitem.setPrice(Double.valueOf(prices.get(i)));
			zoneitem.setCity(cities.get(i));
			zoneitem.setSmstext(smstexts.get(i));
			zoneitem.setValue(values.get(i));
			zoneitem.setPosition(Integer.valueOf(orders.get(i)));
			zoneitem.setServiceName(servicenames.get(i));
			zoneitem.setServiceitemName(serviceitemnames.get(i));
			zoneitems.add(zonesRepo.save(zoneitem));
		}
	}


	/** containers */

	private void createContainers(ContainerRepository containerRepository, int numberOfItems) {
		Container cont = new Container();
		cont.setId(Long.valueOf(1));
		cont.setDescription("Home page di produzione");
		cont.setState(StateType.VISIBLE);
		containerRepository.save(cont);
	}


	/** elements */

	private void createElements(ElementRepository elementRepository, int numberOfItems) {
		List<Element> elements = new ArrayList<>();
		List<String> modes = new ArrayList<>( Arrays.asList(ElementModeType.BANNER, ElementModeType.BUTTON, ElementModeType.BUTTON, ElementModeType.BUTTON, ElementModeType.BUTTON));
		List<String> cols = new ArrayList<>( Arrays.asList("1", "1", "1", "1", "2"));
		List<String> spans = new ArrayList<>( Arrays.asList("2", "2", "2", "1", "1"));
		List<String> rows = new ArrayList<>( Arrays.asList("1", "2", "3", "4", "4"));
		List<String> descriptions = new ArrayList<>(Arrays.asList("Banner", "Trasporti urbani", "Parcheggio", "Zona traffico limitato", "Sosta con ricarica"));

		for (int i = 0; i < modes.size(); i++) {
			Element el = new Element();
			el.setId(Long.valueOf(i));
			el.setMode(modes.get(i));
			el.setPosColumn(Integer.valueOf(cols.get(i)));
			el.setPosRow(Integer.valueOf(rows.get(i)));
			el.setPosSpan(Integer.valueOf(spans.get(i)));
			el.setState(StateType.VISIBLE);
			el.setDescription(descriptions.get(i));
			elements.add(elementRepository.save(el));
		}
	}


	/** cards */

	private void createCards(CardRepository cardRepository, int numberOfItems) {
		List<Card> cards = new ArrayList<>();
		List<String> modes = new ArrayList<>( Arrays.asList(CardActionModeType.INTERNAL, CardActionModeType.EXTERNAL, CardActionModeType.INTERNAL));
		List<String> actions = new ArrayList<>( Arrays.asList("openGoquickNews", "http://www2.comune.prato.it/comefareper/trasporti/in-citta/archivio7_51_630_64_8.html", "openTicket"));
		List<String> idsComponent = new ArrayList<>(Arrays.asList("17", "17", "18"));

		for (int i = 0; i < modes.size(); i++) {
			Card c = new Card();
			c.setId(Long.valueOf(i));
			c.setActionMode(modes.get(i));
			c.setActionLaunch(actions.get(i));
			c.setElement(Long.valueOf(idsComponent.get(i)));
			cards.add(cardRepository.save(c));
		}
	}

}
