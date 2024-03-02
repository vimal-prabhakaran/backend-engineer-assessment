package com.midas.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentProviderType {
  STRIPE("stripe");

  private final String name;
}
