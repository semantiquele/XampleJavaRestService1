package org.xample.javarestservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SimpleStuffStore implements IStuffStore {
    private final Map<String, StuffModel> datastore = new HashMap<>();
    private final Logger logger;

    public SimpleStuffStore(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public long getCount() {
        return datastore.size();
    }

    @Override
    public IStuffIterator createStuffIterator() {
        return new SimpleStuffIterator(datastore);
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
        logger.info("trying to store stuff with key '"+stuff.getKey()+"'");
        if (datastore.containsKey(stuff.getKey())) {
            return false;
        }
        datastore.put(stuff.getKey(), stuff);
        return true;
    }

    @Override
    public boolean updateStuff(StuffModel stuff) {
        logger.info("trying to update stuff with key '"+stuff.getKey()+"'");
        if (!datastore.containsKey(stuff.getKey()))
            return false;
        datastore.put(stuff.getKey(), stuff);
        return true;
    }

    private static class SimpleStuffIterator implements IStuffIterator {
        private final Map<String, StuffModel> datastore;
        private final Iterator<String> datastoreKeysetIterator;
        private boolean isClosed = false;

        public SimpleStuffIterator(Map<String, StuffModel> datastore) {
            this.datastore = datastore;
            this.datastoreKeysetIterator = datastore.keySet().iterator();
        }

        @Override
        public void close() {
            isClosed = true;
        }

        @Override
        public boolean hasNext() {
            if (isClosed)
                throw new IllegalStateException("closed iterator");
            return datastoreKeysetIterator.hasNext();
        }
        @Override
        public StuffModel next() {
            if (isClosed)
                throw new IllegalStateException("closed iterator");
            var nextKey = datastoreKeysetIterator.next();
            return datastore.get(nextKey);
        }

    }
    
}
