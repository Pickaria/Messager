package fr.pickaria.messager;

import fr.pickaria.messager.configuration.MessageConfiguration;
import fr.pickaria.messager.configuration.MessagerConfiguration;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public class Messager {
    private final static MiniMessage miniMessage = MiniMessage.miniMessage();

    private final static MessagerConfiguration defaultConfiguration = new MessagerConfiguration(
            new MessageConfiguration(
                    miniMessage.deserialize("<dark_gray>[<bold><gold>!</bold><dark_gray>]"),
                    NamedTextColor.GRAY,
                    NamedTextColor.GOLD
            ),
            new MessageConfiguration(
                    miniMessage.deserialize("<dark_red>[<bold><red>!</bold><dark_red>]"),
                    NamedTextColor.RED,
                    NamedTextColor.GRAY
            )
    );

    private final MessagerConfiguration configuration;

    public Messager(MessagerConfiguration configuration) {
        this.configuration = configuration;
    }

    public Messager() {
        this.configuration = defaultConfiguration;
    }

    public void info(Audience audience, String key, MessageComponent... components) {
        Component formattedMessage = formatTranslatable(getConfiguration(MessageType.INFO), key, components);
        audience.sendMessage(formattedMessage);
    }

    public void error(Audience audience, String key, MessageComponent... components) {
        Component formattedMessage = formatTranslatable(getConfiguration(MessageType.ERROR), key, components);
        audience.sendMessage(formattedMessage);
    }

    public Component format(MessageType type, String key, MessageComponent... components) {
        return formatTranslatable(getConfiguration(type), key, components);
    }

    private MessageConfiguration getConfiguration(MessageType type) {
        return switch (type) {
            case INFO -> this.configuration.info();
            case ERROR -> this.configuration.error();
        };
    }

    private Component formatTranslatable(MessageConfiguration configuration, String translationKey, MessageComponent... components) {
        Component message = getComponent(configuration, translationKey, components)
                .color(configuration.color());
        return configuration.prefix()
                .appendSpace()
                .append(message);
    }

    private Component getComponent(MessageConfiguration configuration, String translationKey, MessageComponent... components) {
        if (components.length > 0) {
            List<Component> placeholders = new ArrayList<>();
            for (MessageComponent component : components) {
                placeholders.add(component.getComponent(configuration));
            }
            return Component.translatable(translationKey, translationKey, placeholders);
        } else {
            return Component.translatable(translationKey, translationKey);
        }
    }
}
