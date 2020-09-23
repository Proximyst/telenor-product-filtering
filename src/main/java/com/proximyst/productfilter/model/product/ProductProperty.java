package com.proximyst.productfilter.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "properties")
@IdClass(ProductProperty.PropertyKey.class)
public class ProductProperty implements Serializable {
  @JsonIgnore
  @Id
  @Column(name = "product_id", updatable = false, nullable = false)
  private long productId;

  @JsonSerialize
  @Id
  @Column(name = "key", updatable = false, nullable = false)
  @NonNull
  private String key;

  @JsonSerialize
  @Column(name = "value", updatable = false, nullable = false)
  @NonNull
  private String value;

  public ProductProperty() {
  }

  public ProductProperty(long productId, @NonNull String key, @NonNull String value) {
    this.productId = productId;
    this.key = key;
    this.value = value;
  }

  public long getProductId() {
    return productId;
  }

  @NonNull
  public String getKey() {
    return key;
  }

  @NonNull
  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductProperty that = (ProductProperty) o;
    return getProductId() == that.getProductId() &&
        getKey().equals(that.getKey()) &&
        getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getProductId(), getKey(), getValue());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ProductProperty.class.getSimpleName() + "[", "]")
        .add("productId=" + productId)
        .add("key='" + key + "'")
        .add("value='" + value + "'")
        .toString();
  }

  public static final class PropertyKey implements Serializable {
    private long productId;
    private String key;

    public PropertyKey() {
    }

    public PropertyKey(long productId, String key) {
      this.productId = productId;
      this.key = key;
    }

    public long getProductId() {
      return productId;
    }

    public String getKey() {
      return key;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      PropertyKey that = (PropertyKey) o;
      return getProductId() == that.getProductId() &&
          getKey().equals(that.getKey());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getProductId(), getKey());
    }
  }
}
