package com.proximyst.productfilter.model.hibernate;

import com.proximyst.productfilter.model.product.ProductType;
import javax.persistence.AttributeConverter;

public class ProductTypeConverter implements AttributeConverter<ProductType, Integer> {
  @Override
  public Integer convertToDatabaseColumn(ProductType productType) {
    return productType == null ? null : productType.getSqlId();
  }

  @Override
  public ProductType convertToEntityAttribute(Integer integer) {
    if (integer == null) {
      return null;
    }

    return ProductType.getBySqlId(integer);
  }
}
