package com.linebeck.gitter.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class TextHandler {

    public static TextComponent setText(String text, String hexCode) {
        TextComponent component = Component.text(text).color(TextColor.fromCSSHexString(hexCode)).decoration(TextDecoration.ITALIC, false);
        return component;
    }

    public static TextComponent setText(String text) {
        TextComponent component = Component.text(text);
        return component;
    }
}
