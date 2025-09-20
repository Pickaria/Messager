package fr.pickaria.messager;

import fr.pickaria.messager.configuration.MessageConfiguration;
import fr.pickaria.messager.configuration.MessagerConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Locale;

public class MessagerBuilder {

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

    private MessagerConfiguration configuration;
    private TranslatableComponentRenderer<Locale> renderer;

    protected MessagerBuilder() {}

    public MessagerBuilder withConfiguration(MessagerConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public MessagerBuilder withRenderer(TranslatableComponentRenderer<Locale> renderer) {
        this.renderer = renderer;
        return this;
    }

    public Messager build() {
        MessagerConfiguration configuration = this.configuration == null ? defaultConfiguration : this.configuration;
        TranslatableComponentRenderer<Locale> renderer = this.renderer == null ? GlobalTranslator.renderer() : this.renderer;
        return new Messager(configuration, renderer);
    }
}
