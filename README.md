# Pickaria Messager API

> An API making use of the [Adventure](https://docs.advntr.dev/) to consistently format messages being sent to players.

## Usage

### Add dependency

```groovy
repositories {
    mavenLocal()
}

dependencies {
    implementation("fr.pickaria:messager:1.0-SNAPSHOT")
}
```

### Register translations

Since this API requires messages to be defined in a Bundle.properties file, you must first register the Bundle in the TranslationRegistry.

This is well explain on the [Internationalization](https://docs.papermc.io/paper/dev/component-api/i18n) documentation of Paper.

```properties
# src/main/resources/your/plugin/Bundle_en_US.properties
hello.player=Hello {0}!
```

### Usage with Spigot

```java
import fr.pickaria.messager.Messager;

public class ExamplePlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        // Register translations
        TranslationRegistry registry = TranslationRegistry.create(Key.key("namespace:value"));

        ResourceBundle bundle = ResourceBundle.getBundle("your.plugin.Bundle", Locale.US, UTF8ResourceBundleControl.get());
        registry.registerAll(Locale.US, bundle, true);
        GlobalTranslator.translator().addSource(registry);
        
        // Register events
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Messager messager = Messager();
        messager.info(event.getPlayer(), "hello.player", Text(event.getPlayer().getName()));
    }
}
```
