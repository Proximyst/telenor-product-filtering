package com.proximyst.productfilter.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.proximyst.productfilter.model.product.Product;
import com.proximyst.productfilter.model.product.ProductColor;
import com.proximyst.productfilter.model.product.ProductRepository;
import com.proximyst.productfilter.model.product.ProductType;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProductController {
  private final ProductRepository productRepository;

  @Autowired
  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ProductWrapper getProduct(
      // TODO(Mariell Hoversholm): Replace with property request mappings
      @RequestParam(required = false) ProductType type,
      @RequestParam(required = false) String city,
      @RequestParam(required = false, name = "min_price") Float minPrice,
      @RequestParam(required = false, name = "max_price") Float maxPrice,
      @RequestParam(required = false, name = "property.color") ProductColor color,
      @RequestParam(required = false, name = "property.gb_limit_min") Float gbLimitMin,
      @RequestParam(required = false, name = "property.gb_limit_max") Float gbLimitMax
  ) {
    return new ProductWrapper(
        StreamSupport.stream(
            (type == null ? productRepository.findAll() : productRepository.findAllByType(type)).spliterator(),
            false
        )
            .filter(prod -> {
              if (city != null &&
                  !prod.getAddress().substring(prod.getAddress().lastIndexOf(',') + 1).trim().equalsIgnoreCase(city)
              ) {
                return false;
              }

              if (minPrice != null && prod.getPrice() < minPrice
                  || maxPrice != null && prod.getPrice() > maxPrice) {
                return false;
              }

              if (color != null &&
                  prod.getPropertyByName("color")
                      .map(p -> !p.getValue().equalsIgnoreCase(color.getTypeName()))
                      .orElse(true)) {
                return false;
              }

              if ((gbLimitMin != null || gbLimitMax != null) &&
                  prod.getPropertyByName("gb_limit")
                      .map(p -> {
                        float f = Float.parseFloat(p.getValue());
                        return (gbLimitMin != null && f < gbLimitMin)
                            || (gbLimitMax != null && f > gbLimitMax);
                      })
                      .orElse(true)) {
                return false;
              }

              return true;
            })
            .collect(Collectors.toList())
    );
  }

  @SuppressWarnings("unused") // Required to be converted to JSON.
  @JsonSerialize
  private static class ProductWrapper implements Serializable {
    @JsonSerialize
    private final List<Product> data;

    public ProductWrapper(List<Product> data) {
      this.data = data;
    }

    public ProductWrapper(Iterable<Product> data) {
      this.data = StreamSupport.stream(data.spliterator(), false).collect(Collectors.toList());
    }
  }
}
