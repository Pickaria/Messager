package fr.pickaria.messager;

import fr.pickaria.messager.configuration.MessageConfiguration;
import net.kyori.adventure.text.Component;

public interface MessageComponent {
    Component getComponent(MessageConfiguration config);
}
