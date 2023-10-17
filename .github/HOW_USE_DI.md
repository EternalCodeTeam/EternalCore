## EternalCore DI
Dependency Injection (DI) is an object-oriented programming design pattern that enhances flexibility, maintainability, and modularity by managing component dependencies. It allows an external source to inject dependencies into a class, reducing tight coupling and improving testability.

Key DI concepts include:
- **Beans** (instances of classes managed by the bean container)
- **Bean container** (stores beans)
- **Dependent class** (those needing dependencies from the bean container)

Used DI type
**Constructor Injection:** Dependencies are injected through the class's constructor (annotated as `@Inject`)

### How to use DI
#### 1. Repository
```java
@Repository // <- means a class that will be injected into other classes. (dependency)
public class UserRepositoryImpl implements UserRepository {
    // Implementation
}
```

#### 2. Config
```java
@ConfigurationFile // <- marks a class that will be registered and loaded as a config.
public class UserConfiguration implements ReloadableConfig {
    String example = "example";
    int exampleInt = 1;
    boolean exampleBoolean = true;

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "user-configuration.yml");
    }
}
```

#### 3. Service / Manager etc.
```java
@Service // <- means a class that will be injected into other classes. (dependency)
public class UserService {
    
    private final UserRepository exampleRepository;
    
    @Inject // <- marks a class as dependent on other classes. (dependent class)
    public UserService(UserRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }
    
}
```

#### 4. Bukkit task
```java
@Task(delay = 20L, unit = TimeUnit.SECONDS) // <- marks a class that will be registered as a task.
public class UserTask implements Runnable {
    
    private final UserService exampleService;
    
    @Inject // <- marks a class as dependent on other classes. (dependent class)
    public UserTask(UserService exampleService) {
        this.exampleService = exampleService;
    }
    
    @Override
    public void run() {
        // do something
    }
    
}
```
#### 5. Bukkit listener
```java
@Controller // <- marks a class that will be registered as a bukkit listener.
public class UserListener implements Listener {
    
    private final UserService exampleService;
    
    @Inject // <- marks a class as dependent on other classes. (dependent class)
    public UserListener(UserService exampleService) {
        this.exampleService = exampleService;
    }
    
    @EventHandler
    void onJoin(AsyncPlayerJoinEvent event) {
        // do something
    }
    
}
```
#### 6. Command
```java
@Command(name = "example") // <- marks a class that will be registered as a command.
public class UserCommand {
    
    private final UserService exampleService;
    
    @Inject // <- marks a class as dependent on other classes. (dependent class)
    public UserCommand(UserService exampleService) {
        this.exampleService = exampleService;
    }
    
    @Execute
    void onUserCommand(CommandSender sender, @Arg String arg) {
        // do something
    }
    
}
```

#### 7. Subscriber
```java
@Controller // <- marks a class that will be registered as an event subscriber.
public class UserSubscriber implements Subscriber {
    
    private final UserService exampleService;
    
    @Inject // <- marks a class as dependent on other classes. (dependent class)
    public UserListener(UserService exampleService) {
        this.exampleService = exampleService;
    }
    
    @Subscribe
    void onInitialize(EternalInitializeEvent event) {
        // do something on initialize
    }
    
}
```

#### 8. BeanSetup
BeanSetup is a class that allows you to register beans in the bean container.
It is used to register dependencies that are cannot be registered in the bean container using annotations. e.g. MiniMessage, AdventureProvider, HikariDataSource, etc.
```java

@BeanSetup // <- marks a class that will be registered as a bean holder.
public class UserBeanSetup {

    @Bean // <- marks a method that will be registered as a bean. (dependency)
    public UserProvider exampleProvider() {
        UserProviderImpl exampleProvider = new UserProviderImpl();
        exampleProvider.doSomething();
        
        return new UserProviderImpl();
    }
    
    @Bean // <- marks a method that will be registered as a bean. (dependency)
    public OtherProvider otherProvider(Server server) {
        return new OtherProvider(server);
    }
}
```
