## Spring I/O MCP Server

A Model Context Protocol (MCP) server that provides AI assistants with access to Spring I/O 2025 conference session data. This server exposes conference information including talks, workshops, speakers, and scheduling details through a standardized MCP interface.

---
## What is MCP?
The Model Context Protocol (MCP) is an open standard that enables AI assistants to securely connect to external data sources and tools. This project implements an MCP server that makes Spring I/O conference data accessible to AI models, allowing them to answer questions about sessions, speakers, schedules, and more.

---
## Project Features
Conference Session Access: Complete database of Spring I/O 2025 sessions
MCP Compliance: Fully compatible with MCP standard for AI integration
Rich Session Data: Includes speakers, room assignments, session types, and timing
Spring Boot Integration: Built with Spring Boot 3.5 and Spring AI MCP support
JSON Data Source: Flexible data management through JSON configuration

---
## Getting Started

### Building the Project

The project includes Maven wrapper scripts for easy building:

```bash
# On Unix/macOS
./mvnw clean compile

# On Windows
mvnw.cmd clean compile
```

### Running Tests

Verify everything works correctly by running the test suite:

```bash
# Unix/macOS
./mvnw test

# Windows
mvnw.cmd test
```

## How to Run the Application

### Standard Execution

Build and run the application using Maven:

```bash
# Build the JAR file
./mvnw clean package

after running above command you will see - Replacing main artifact ..... save that text for MCP inspector.

# Run the application
java -jar target/spring-io-mcp-0.0.1-SNAPSHOT.jar
works with MCP inspector java -jar "C:..\...\target\spring-io-mcp-stdio-server-0.0.1-SNAPSHOT.jar"

```

### Development Mode

For development, you can run directly with Maven:

```bash
./mvnw spring-boot:run
```
### MCP Inspector
```
Make Sure latest nodeJs version is installed
Add the nodeJs path to env variable "Path"
$ node -v // make sure node is installed correctly and configured

run  "npx @modelcontextprotocol/inspector"
you will get a url where MCP inspector will be launched on localhost:6274.

command = java
Arguments = -jar "path_copied_in_above_step"
```

### MCP Server Configuration

The application is configured as an MCP server with the following settings:

- **Server Name**: `spring-io-sessions-mcp`
- **Version**: `0.0.1`
- **Transport**: STDIO (Standard Input/Output)
- **Application Type**: Non-web (console application)

**Important STDIO Configuration**: When using STDIO transport, it's essential to disable Spring Boot's banner and logging to prevent interference with MCP communication:

```properties
# Disable banner and logging for STDIO transport
spring.main.banner-mode=off
logging.level.root=OFF
```

Without these settings, Spring Boot's startup messages and log output would corrupt the MCP protocol communication over STDIO.

## Code Examples

### Session Data Structure

The server works with session data structured as follows:

```java
public record Session(
    String day, 
    String time, 
    String title, 
    String type, 
    String[] speakers, 
    String room
) {}
```

### Conference Model

Conference information is represented using this structure:

```java
public record Conference(
    String name, 
    int year, 
    String[] dates, 
    String location, 
    List<Session> sessions
) {}
```

### MCP Tool Implementation

The core functionality is exposed through an MCP tool:

```java
@Tool(
    name = "spring-io-sessions", 
    description = "Returns all sessions for Spring I/O 2025 Conference"
)
public List<Session> findAllSessions() {
    return sessions;
}
```

### Spring Configuration

The application registers MCP tools using Spring's configuration:

```java
@Bean
public List<ToolCallback> springIOSessionTools(SessionTools sessionTools) {
    return List.of(ToolCallbacks.from(sessionTools));
}
```

### Session Loading Process

Sessions are loaded from JSON during application startup:

```java
@PostConstruct
public void init() {
    try (InputStream inputStream = 
         TypeReference.class.getResourceAsStream("/sessions.json")) {
        var conference = objectMapper.readValue(inputStream, Conference.class);
        this.sessions = conference.sessions();
    } catch (IOException e) {
        throw new RuntimeException("Failed to read JSON data", e);
    }
}
```

## Data Configuration

### Session Types

The conference includes various session types:

- **talks**: Technical presentations and deep dives
- **workshops**: Hands-on learning sessions
- **keynote**: Major conference presentations
- **break**: Coffee breaks and networking time
- **networking**: Social events and community building
- **logistics**: Registration and organizational events

### Sample Session Data

Here's an example of how session data is structured:

```json
{
  "day": "2025-05-22",
  "time": "11:00-11:50",
  "title": "Demystifying Spring Boot Magic",
  "type": "talk",
  "speakers": ["Patrick Baumgartner"],
  "room": "Auditorium"
}
```

## MCP Integration

### Connecting to AI Assistants

This server can be connected to AI assistants that support MCP. The server provides:

1. **Session Discovery**: Find sessions by various criteria
2. **Speaker Information**: Access speaker details for each session
3. **Schedule Navigation**: Browse sessions by day and time
4. **Room Management**: Understand venue layout and session locations