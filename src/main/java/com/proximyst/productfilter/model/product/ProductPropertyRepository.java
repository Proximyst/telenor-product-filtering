package com.proximyst.productfilter.model.product;

import com.proximyst.productfilter.model.product.ProductProperty.PropertyKey;
import org.springframework.data.repository.CrudRepository;

public interface ProductPropertyRepository extends CrudRepository<ProductProperty, PropertyKey> {
}
