package fr.pickaria.messager.components;

import fr.pickaria.messager.MessageComponent;
import fr.pickaria.messager.configuration.MessageConfiguration;
import net.kyori.adventure.text.Component;

public class Text implements MessageComponent {
    private final Component component;
    private final Boolean changeColor;

    public Text(Component component, Boolean changeColor) {
        this.component = component;
        this.changeColor = changeColor;
    }

    public Text(Component component) {
        this.component = component;
        this.changeColor = true;
    }

    @Override
    public Component getComponent(MessageConfiguration config) {
        if (changeColor) {
            return component.color(config.accent());
        } else {
            return component;
        }
    }
}
