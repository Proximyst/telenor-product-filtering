package com.proximyst.productfilter.model.product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
  Iterable<Product> findAllByType(ProductType type);
}
