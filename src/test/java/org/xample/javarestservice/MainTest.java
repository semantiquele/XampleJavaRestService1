package org.xample.javarestservice;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MainTest {
    
    @Test
    public void getMessage() {
        assertThat(Main.getMessage())
        .isNotNull()
        .isNotEmpty()
        .isEqualTo("Michael was here!")
        ;
    }

}
