package org.xample.javarestservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        List<StuffModel> result = new ArrayList<>();
        try (var iter = stuffstore.createStuffIterator()) {
            while (iter.hasNext()) {
                result.add(iter.next());
            }
        }
        return ResponseEntity.of(Optional.of(result));
    }

    @GetMapping(path="/{key}")
    public ResponseEntity<StuffModel> getStuff(@PathVariable String key) {
        if (key == null || key.trim().length() == 0) {
            LOG.error("missing stuff key");
            return ResponseEntity.badRequest().build();
        }
        key = key.trim();

        var stuff = stuffstore.lookupStuff(key);
        if (!stuff.isPresent()) {
            LOG.error("no such stuff for key '"+key+"'");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(stuff);
    }

    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStuff(@RequestBody StuffModel stuff) {
        var isStored = stuffstore.storeStuff(stuff);
        if (!isStored) {
            LOG.error("already existing stuff with key '"+stuff.getKey()+"'");
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path="/{key}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStuff(@RequestBody StuffModel stuff, @PathVariable String key) {
        if (key == null || key.trim().length() == 0) {
            LOG.error("missing stuff key");
            return ResponseEntity.badRequest().build();
        }
        key = key.trim();

        if (!key.equals(stuff.getKey())) {
            LOG.error("path key '"+key+"' doesn't match stuff key '"+stuff.getKey()+"'");
            return ResponseEntity.badRequest().build();
        }

        var isUpdated = stuffstore.updateStuff(stuff);
        if (!isUpdated) {
            LOG.error("no such stuff for key '"+stuff.getKey()+"'");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path="/{key}")
    public ResponseEntity<String> deleteStuff(@PathVariable String key) {
        if (key == null || key.trim().length() == 0) {
            LOG.error("missing stuff key");
            return ResponseEntity.badRequest().build();
        }
        key = key.trim();

        var isDeleted = stuffstore.removeStuff(key);
        if (!isDeleted) {
            LOG.error("no such stuff for key '"+key+"'");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
