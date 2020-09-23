package com.proximyst.productfilter;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.proximyst.productfilter.model.processing.CsvProduct;
import com.proximyst.productfilter.model.product.Product;
import com.proximyst.productfilter.model.product.ProductProperty;
import com.proximyst.productfilter.model.product.ProductPropertyRepository;
import com.proximyst.productfilter.model.product.ProductRepository;
import java.io.StringReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataParserTest {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductPropertyRepository productPropertyRepository;

  @Test
  void parsingTest() {
    CsvToBean<CsvProduct> mapper = new CsvToBeanBuilder<CsvProduct>(new StringReader(
        "Product type,Product properties,Price,Store address\n"
            + "phone,color:red,3.0,\"Testing, Stockholm\"\n"
            + "subscription,gb_limit:7.45,16000,\"Testing2, Stockholm2\""
    ))
        .withType(CsvProduct.class)
        .withIgnoreLeadingWhiteSpace(true)
        .build();

    mapper.stream()
        .forEach(prod -> {
          Product product = prod.createProduct();
          productRepository.save(product);

          ProductProperty productProperty = prod.createProperty(product.getId());
          productPropertyRepository.save(productProperty);
          product.getProperties().add(productProperty);
        });
  }
}
