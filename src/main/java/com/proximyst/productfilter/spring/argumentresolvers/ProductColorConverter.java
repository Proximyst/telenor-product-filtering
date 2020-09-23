package com.proximyst.productfilter.spring.argumentresolvers;

import com.proximyst.productfilter.model.product.ProductColor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;

public class ProductColorConverter implements Converter<String, ProductColor> {
  @Override
  public ProductColor convert(String s) {
    ProductColor color = ProductColor.getByTypeName(s);
    if (color == null) {
      return EnumUtils.getEnum(ProductColor.class, s);
    }
    return color;
  }
}
