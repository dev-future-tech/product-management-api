package com.flower.product.management;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"!test"})
public class AMQPConfiguration {
}
