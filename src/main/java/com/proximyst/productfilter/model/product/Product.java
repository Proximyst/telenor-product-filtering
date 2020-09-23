package com.proximyst.productfilter.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.proximyst.productfilter.model.hibernate.ProductTypeConverter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "products")
@JsonSerialize
public class Product implements Serializable {
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private long id;

  @JsonSerialize
  @Column(name = "type", updatable = false, nullable = false)
  @Convert(converter = ProductTypeConverter.class)
  @NonNull
  private ProductType type;

  @JsonSerialize
  @Column(name = "price", nullable = false)
  private float price;

  @JsonSerialize
  @Column(name = "store", nullable = false)
  @NonNull
  private String address;

  @JsonSerialize
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  @NonNull
  private Set<ProductProperty> properties = new HashSet<>();

  public Product() {
  }

  public Product(
      @NonNull ProductType type,
      float price,
      @NonNull String address,
      @NonNull ProductProperty... properties
  ) {
    this.type = type;
    this.price = price;
    this.address = address;
    this.properties = new HashSet<>(Arrays.asList(properties));
  }

  public long getId() {
    return id;
  }

  @NonNull
  public ProductType getType() {
    return type;
  }

  public float getPrice() {
    return price;
  }

  @NonNull
  public String getAddress() {
    return address;
  }

  @NonNull
  public Set<ProductProperty> getProperties() {
    return properties;
  }

  @NonNull
  public Optional<ProductProperty> getPropertyByName(String key) {
    for (ProductProperty property : properties) {
      if (property.getKey().equalsIgnoreCase(key)) {
        return Optional.of(property);
      }
    }

    return Optional.empty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return getId() == product.getId() &&
        Float.compare(product.getPrice(), getPrice()) == 0 &&
        getType() == product.getType() &&
        getAddress().equals(product.getAddress()) &&
        getProperties().equals(product.getProperties());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getType(), getPrice(), getAddress(), getProperties());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("type=" + type)
        .add("price=" + price)
        .add("address='" + address + "'")
        .add("properties=" + properties)
        .toString();
  }
}
