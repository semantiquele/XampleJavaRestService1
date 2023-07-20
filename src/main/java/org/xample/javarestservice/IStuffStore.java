package org.xample.javarestservice;

import java.util.Optional;

public interface IStuffStore {

    /**
     * Checks whether storage is empty.
     * @return true iff storage is empty
     */
    boolean isEmpty();

    /**
     * Counts the amount of stuff currently stored.
     * @return number of stuff items stored
     */
    long getCount();

    /**
     * Creates an iterator to all stored stuff.
     * @return iterator to all stored stuff
     */
    IStuffIterator createStuffIterator();

    /**
     * Looks up stuff for a given key.
     * @param key stuff key
     * @return stuff with given key, or nothing
     */
    Optional<StuffModel> lookupStuff(String key);

    /**
     * Removes stuff from storage.
     * @param key stuff key
     * @return false if no stuff with given key is stored
     */
    boolean removeStuff(String key);

    /**
     * Stores new stuff in storage.
     * @param stuff stuff
     * @return false if some stuff with given key already stored
     */
    boolean storeStuff(StuffModel stuff);

    /**
     * Updates existing stuff with new stuff data for given stuff key.
     * @param stuff stuff
     * @return false iff no stuff with given key is stored
     */
    boolean updateStuff(StuffModel stuff);

}