package org.xample.javarestservice;

import java.util.Iterator;

public interface IStuffIterator extends Iterator<StuffModel>, AutoCloseable { 

    @Override
    void close();
}
