package com.matuga.mcp;

import com.matuga.mcp.model.SessionTools;
import java.util.List;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringIoMcpStdioServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringIoMcpStdioServerApplication.class, args);
  }

  @Bean
  public List<ToolCallback> springIOSessionTools(SessionTools sessionTools) {
    return List.of(ToolCallbacks.from(sessionTools));
  }
}
