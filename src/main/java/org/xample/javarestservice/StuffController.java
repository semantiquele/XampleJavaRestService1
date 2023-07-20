package org.xample.javarestservice;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stuffs")
public class StuffController {
    
    private final Logger logger;
    private final IStuffStore stuffstore;

    public StuffController(Logger logger, IStuffStore stuffstore) {
        this.logger = logger;
        this.stuffstore = stuffstore;
    }

}
