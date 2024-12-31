package fr.pickaria.messager;

public class MessagerException extends RuntimeException {
    private final String translationKey;
    private final MessageComponent[] arguments;

    MessagerException(String translationKey) {
        this.translationKey = translationKey;
        arguments = new MessageComponent[0];
    }

    MessagerException(String translationKey, MessageComponent... arguments) {
        this.translationKey = translationKey;
        this.arguments = arguments;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public MessageComponent[] getArguments() {
        return arguments;
    }
}
