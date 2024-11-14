package fr.pickaria.messager.configuration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public record MessageConfiguration(Component prefix, TextColor color, TextColor accent) {
}
