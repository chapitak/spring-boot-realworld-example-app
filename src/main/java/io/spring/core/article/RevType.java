package io.spring.core.article;

public enum RevType {
  생성(0),
  수정(1),
  삭제(2);

  int index;
  RevType(int index) {
    this.index = index;
  }

  public static String getName(int value) {
    return RevType.values()[value].name();
  }
  public int index() {
    return this.index;
  }
}
