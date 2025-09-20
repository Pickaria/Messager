package fr.pickaria.messager;

import fr.pickaria.messager.configuration.MessageConfiguration;
import fr.pickaria.messager.configuration.MessagerConfiguration;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Messager {

    private final MessagerConfiguration configuration;
    private final TranslatableComponentRenderer<Locale> renderer;

    protected Messager(MessagerConfiguration configuration, TranslatableComponentRenderer<Locale> renderer) {
        this.configuration = configuration;
        this.renderer = renderer;
    }

    public static MessagerBuilder builder() {
        return new MessagerBuilder();
    }

    public void info(Audience audience, String key, MessageComponent... components) {
        Component formattedMessage = formatTranslatable(getConfiguration(MessageType.INFO), key, components);
        sendMessage(audience, formattedMessage);
    }

    public void error(Audience audience, String key, MessageComponent... components) {
        Component formattedMessage = formatTranslatable(getConfiguration(MessageType.ERROR), key, components);
        sendMessage(audience, formattedMessage);
    }

    public void exception(Audience audience, MessagerException exception) {
        this.error(audience, exception.getTranslationKey(), exception.getArguments());
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

    private void sendMessage(Audience audience, Component message) {
        Locale locale = audience.get(Identity.LOCALE).orElse(Locale.ROOT);
        audience.sendMessage(this.renderer.render(message, locale));
    }
}
