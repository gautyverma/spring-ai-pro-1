package com.matuga.ai.a8_tools.b1_dateTime;

import java.time.LocalDateTime;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateTimeTools {

  @Tool(description = "Get the current date and time in the user's timezone")
  String getCurrentDateTime() {
    System.out.println("gauty-dateTime-tool");
    return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
  }
}
