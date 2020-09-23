package com.proximyst.productfilter;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.proximyst.productfilter.model.processing.CsvProduct;
import com.proximyst.productfilter.model.product.Product;
import com.proximyst.productfilter.model.product.ProductProperty;
import com.proximyst.productfilter.model.product.ProductPropertyRepository;
import com.proximyst.productfilter.model.product.ProductRepository;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProductFilterApplication implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductFilterApplication.class);

  @Value("${DATA_FILE:data.csv}")
  private String dataFilePath;
  private ProductRepository productRepository;
  private ProductPropertyRepository productPropertyRepository;

  @Autowired
  public ProductFilterApplication(ProductRepository productRepository,
      ProductPropertyRepository productPropertyRepository) {
    this.productRepository = productRepository;
    this.productPropertyRepository = productPropertyRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(ProductFilterApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // This entire method is not the prettiest, but it does the job.
    //
    // Ideally, one would just have an importer to convert from the CSV to SQL
    // insert statements.

    File file = Path.of(dataFilePath).toFile();
    if (!file.isFile()) {
      LOGGER.warn("There is no data loaded (no such file: {})", dataFilePath);
      return;
    }

    try (Reader reader = new FileReader(file)) {
      CsvToBean<CsvProduct> mapper = new CsvToBeanBuilder<CsvProduct>(reader)
          .withType(CsvProduct.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build();

      // Manually parse; that's the easiest method for data which should've been
      // provided in the database in proper form anyways.
      for (CsvProduct data : mapper.parse()) {
        Product product = data.createProduct();
        productRepository.save(product); // Save the base product.
        ProductProperty property = data.createProperty(product.getId());
        productPropertyRepository.save(property); // Save the property.
        product.getProperties().add(property); // Associate the property with the product.
      }
    }
  }
}
