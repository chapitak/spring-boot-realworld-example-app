package io.spring.core.article;

public enum RevType {
  생성,
  수정,
  삭제;

  public static String getName(int value) {
    return RevType.values()[value].name();
  }
}
