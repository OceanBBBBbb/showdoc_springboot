package com.ocean.showdoc.common;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageSource extends ResourceBundleMessageSource {

  private MessageSource() {
    setBasename("com.ocean.showdoc.message");
    setDefaultEncoding("UTF-8");
    setFallbackToSystemLocale(false);
  }

  public static MessageSourceAccessor getAccessor() {
    return new MessageSourceAccessor(new MessageSource());
  }

}
