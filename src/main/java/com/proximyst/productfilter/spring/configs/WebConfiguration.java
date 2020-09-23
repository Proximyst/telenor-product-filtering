package com.proximyst.productfilter.spring.configs;

import com.proximyst.productfilter.spring.argumentresolvers.ProductColorConverter;
import com.proximyst.productfilter.spring.argumentresolvers.ProductTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new ProductColorConverter());
    registry.addConverter(new ProductTypeConverter());
  }
}
