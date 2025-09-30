/**
 * SmokeTest.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 26/05/25
 */


package com.cdl.escrow.sample;

import com.cdl.escrow.controller.ApplicationConfigurationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ApplicationConfigurationController applicationConfigurationController;

    @Test
    public void contextLoads() throws Exception
    {
    assertThat(applicationConfigurationController).isNotNull();
    }
}
