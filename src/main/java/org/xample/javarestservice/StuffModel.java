package org.xample.javarestservice;

public class StuffModel {
    private final String key;
    private final String data;
    public StuffModel(String key, String data) {
        if (key == null)
            throw new IllegalArgumentException("Key must not be null.");
        key = key.trim();
        if (key.length() == 0)
            throw new IllegalArgumentException("Key must not be empty string.");
        this.key = key;
        if (data == null)
            throw new IllegalArgumentException("Value to key '" + key + "' is null.");
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }
}
