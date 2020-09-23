package com.proximyst.productfilter.model.processing;

import com.opencsv.bean.CsvBindByName;
import com.proximyst.productfilter.model.product.Product;
import com.proximyst.productfilter.model.product.ProductProperty;
import com.proximyst.productfilter.model.product.ProductType;

public class CsvProduct {
  @CsvBindByName(column = "Product type")
  public String productType;

  @CsvBindByName(column = "Product properties")
  public String rawProperty;

  @CsvBindByName(column = "Price")
  public float price;

  @CsvBindByName(column = "Store address")
  public String address;

  public Product createProduct() {
    ProductType type = ProductType.getByTypeName(productType);
    if (type == null) {
      throw new IllegalStateException("Invalid product type found: " + productType);
    }

    String propertyName = rawProperty.substring(0, rawProperty.indexOf(':'));
    String propertyValue = rawProperty.substring(propertyName.length() + 1);

    return new Product(type, price, address);
  }

  public ProductProperty createProperty(long productId) {
    String key = rawProperty.substring(0, rawProperty.indexOf(':'));
    return new ProductProperty(productId, key, rawProperty.substring(key.length() + 1));
  }
}
