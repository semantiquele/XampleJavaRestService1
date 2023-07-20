package org.xample.javarestservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StuffStore {
    private final Map<String, StuffModel> datastore = new HashMap<>();

    /**
     * Checks whether storage is empty.
     * @return true iff storage is empty
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }

    /**
     * Counts the amount of stuff currently stored.
     * @return number of stuff items stored
     */
    public long getCount() {
        return datastore.size();
    }

    /**
     * Looks up stuff for a given key.
     * @param key stuff key
     * @return stuff with given key, or nothing
     */
    public Optional<StuffModel> lookupStuff(String key) {
        if (key == null)
            throw new IllegalArgumentException("Database key is null");
        if (!datastore.containsKey(key))
            return Optional.empty();
        return Optional.of(datastore.get(key));
    }

    /**
     * Removes stuff from storage.
     * @param key stuff key
     * @return false if no stuff with given key is stored
     */
    public boolean removeStuff(String key) {
        if (!datastore.containsKey(key))
            return false;
        datastore.remove(key);
        return true;
    }

    /**
     * Stores new stuff in storage.
     * @param stuff stuff
     * @return false if some stuff with given key already stored
     */
    public boolean storeStuff(StuffModel stuff) {
        if (datastore.containsKey(stuff.getKey())) {
            return false;
        }
        datastore.put(stuff.getKey(), stuff);
        return true;
    }

    /**
     * Updates existing stuff with new stuff data for given stuff key.
     * @param stuff stuff
     * @return false iff no stuff with given key is stored
     */
    public boolean updateStuff(StuffModel stuff) {
        if (!datastore.containsKey(stuff.getKey()))
            return false;
        datastore.put(stuff.getKey(), stuff);
        return true;
    }
}
