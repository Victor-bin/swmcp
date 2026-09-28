# Project Instructions

- This repository is a Java 17+ Spring Boot MCP server using the Spring AI MCP server starter and STDIO transport.
- Keep stdout reserved for MCP protocol traffic; console logging and the Spring banner must remain disabled for STDIO.
- Expose SWAPI operations through `@McpTool` methods in `SwapiTools`; keep HTTP access and input validation in `SwapiClient`.
- Official references: [Spring AI MCP server starter](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html), [Spring AI MCP annotations](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-annotations-server.html), [MCP Java SDK](https://github.com/modelcontextprotocol/java-sdk).
