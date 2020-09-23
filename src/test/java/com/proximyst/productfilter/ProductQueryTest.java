package com.proximyst.productfilter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.proximyst.productfilter.model.product.Product;
import com.proximyst.productfilter.model.product.ProductRepository;
import com.proximyst.productfilter.model.product.ProductType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductQueryTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testTemplate;

  @Autowired
  private ProductRepository productRepository;

  @Test
  void allProducts() throws Exception {
    ResponseEntity<Wrapper> ent = testTemplate.getForEntity("http://localhost:" + port + "/product", Wrapper.class);
    Product randomProduct = productRepository.findAll().iterator().next();
    Assertions.assertThat(ent.getBody().data).anyMatch(p -> isSimilar(p, randomProduct));
  }

  @Test
  void allProductsForTypes() throws Exception {
    ResponseEntity<Wrapper> ent = testTemplate.getForEntity("http://localhost:" + port + "/product?type=PHONE", Wrapper.class);
    Product randomProduct = productRepository.findAllByType(ProductType.PHONE).iterator().next();
    Assertions.assertThat(ent.getBody().data).anyMatch(p -> isSimilar(p, randomProduct));
  }

  // Continue with more query params here.
  // Omitting due to absolute minimum code requirement.

  private boolean isSimilar(Product product, Product randomProduct) {
    // The ID is not the same, and is stored in both the Product and  the ProductProperty,
    // so we need to check that they're still equal without IDs taken into account.
    return product.getPrice() == randomProduct.getPrice()
        && product.getAddress().equals(randomProduct.getAddress())
        && product.getType() == randomProduct.getType()
        && product.getProperties().size() == randomProduct.getProperties().size()
        && product.getProperties().stream().allMatch(prop ->
        randomProduct.getProperties().stream()
            .anyMatch(prop2 -> prop.getKey().equals(prop2.getKey())
                && prop.getValue().equals(prop2.getValue()))
    );
  }

  @JsonSerialize
  static class Wrapper {
    @JsonSerialize
    public List<Product> data;
  }
}
