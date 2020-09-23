package com.proximyst.productfilter.model.product;

import java.util.EnumSet;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public enum ProductColor {
  GREEN("grön", 0),
  PURPUR("purpur", 1),
  GOLDEN("guld", 2),
  BROWN("brun", 3),
  PINK("rosa", 4),
  RED("röd", 5),
  WHITE("vit", 6),
  PURPLE("lila", 7),
  GRAY("grå", 8),
  SILVER("silver", 9),
  BLACK("svart", 10),
  YELLOW("gul", 11),
  ;

  private static final EnumSet<ProductColor> VALUES = EnumSet.allOf(ProductColor.class);

  @NonNull
  private final String typeName;

  private final int sqlId;

  ProductColor(@NonNull String typeName, int sqlId) {
    this.typeName = typeName;
    this.sqlId = sqlId;
  }

  @NonNull
  public String getTypeName() {
    return typeName;
  }

  public int getSqlId() {
    return sqlId;
  }

  @Nullable
  public static ProductColor getByTypeName(@NonNull String name) {
    return VALUES.stream()
        .filter(type -> type.getTypeName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  @Nullable
  public static ProductColor getBySqlId(int id) {
    return VALUES.stream()
        .filter(type -> type.getSqlId() == id)
        .findFirst()
        .orElse(null);
  }
}
