package com.proximyst.productfilter.model.hibernate;

import com.proximyst.productfilter.model.product.ProductColor;
import javax.persistence.AttributeConverter;

public class ProductColorConverter implements AttributeConverter<ProductColor, Integer> {
  @Override
  public Integer convertToDatabaseColumn(ProductColor productColor) {
    return productColor == null ? null : productColor.getSqlId();
  }

  @Override
  public ProductColor convertToEntityAttribute(Integer integer) {
    if (integer == null) {
      return null;
    }

    return ProductColor.getBySqlId(integer);
  }
}
