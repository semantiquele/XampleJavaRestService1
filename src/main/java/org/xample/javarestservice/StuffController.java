package org.xample.javarestservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stuff")
public class StuffController {
    private static final Logger LOG = LoggerFactory.getLogger(StuffController.class);

    private final IStuffStore stuffstore;

    public StuffController(IStuffStore stuffstore) {
        this.stuffstore = stuffstore;
    }

    @GetMapping(path="")
    public ResponseEntity<List<StuffModel>> listStuff() {
        LOG.info("in GET /stuff");
        var result = List.of(new StuffModel("key1", "data1"));
        return ResponseEntity.of(Optional.of(result));
    }
}
