package org.xample.javarestservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StuffStore implements IStuffStore {
    private final Map<String, StuffModel> datastore = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public long getCount() {
        return datastore.size();
    }

    @Override
    public Optional<StuffModel> lookupStuff(String key) {
        if (key == null)
            throw new IllegalArgumentException("Database key is null");
        if (!datastore.containsKey(key))
            return Optional.empty();
        return Optional.of(datastore.get(key));
    }

    @Override
    public boolean removeStuff(String key) {
        if (!datastore.containsKey(key))
            return false;
        datastore.remove(key);
        return true;
    }

    @Override
    public boolean storeStuff(StuffModel stuff) {
        if (datastore.containsKey(stuff.getKey())) {
            return false;
        }
        datastore.put(stuff.getKey(), stuff);
        return true;
    }

    @Override
    public boolean updateStuff(StuffModel stuff) {
        if (!datastore.containsKey(stuff.getKey()))
            return false;
        datastore.put(stuff.getKey(), stuff);
        return true;
    }
}
