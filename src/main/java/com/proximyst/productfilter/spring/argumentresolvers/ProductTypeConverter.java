package com.proximyst.productfilter.spring.argumentresolvers;

import com.proximyst.productfilter.model.product.ProductType;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;

public class ProductTypeConverter implements Converter<String, ProductType> {
  @Override
  public ProductType convert(String s) {
    ProductType type = ProductType.getByTypeName(s);
    if (type == null) {
      return EnumUtils.getEnum(ProductType.class, s);
    }
    return type;
  }
}
