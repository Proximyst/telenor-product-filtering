package com.proximyst.productfilter.model.product;

import java.util.EnumSet;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public enum ProductType {
  PHONE("phone", 0),
  SUBSCRIPTION("subscription", 1),
  ;

  private static final EnumSet<ProductType> VALUES = EnumSet.allOf(ProductType.class);

  /**
   * The type name of this product type.
   */
  @NonNull
  private final String typeName;

  /**
   * The product type id on the database.
   * <p>
   * This is used over ordinals as this is deterministic.
   */
  private final int sqlId;

  ProductType(@NonNull String typeName, int sqlId) {
    this.typeName = typeName;
    this.sqlId = sqlId;
  }

  /**
   * @return The type name of this product type.
   */
  @NonNull
  public String getTypeName() {
    return typeName;
  }

  /**
   * The product type id on the database.
   * <p>
   * This is used over ordinals as this is deterministic.
   *
   * @return This product type id.
   */
  public int getSqlId() {
    return sqlId;
  }

  @Nullable
  public static ProductType getByTypeName(@NonNull String name) {
    return VALUES.stream()
        .filter(type -> type.getTypeName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  @Nullable
  public static ProductType getBySqlId(int id) {
    return VALUES.stream()
        .filter(type -> type.getSqlId() == id)
        .findFirst()
        .orElse(null);
  }
}
