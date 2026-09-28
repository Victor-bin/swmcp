# SWMCP

A local MCP server built with Java 25 and Spring Boot 4.1.1 that queries [swapi.info](https://swapi.info/). It uses Spring AI MCP 2.0.1 and the STDIO transport. No API key is required.

## Tools

- `swapi_list_categories`: lists the collections available from the API.
- `swapi_list_resources`: lists all records in a category.
- `swapi_get_resource`: gets a record by category and numeric ID.
- `swapi_search_resources`: performs a case-insensitive search across the `name` and `title` fields.

Supported categories: `films`, `people`, `planets`, `species`, `vehicles`, and `starships`.

## Requirements

- JDK 25.
- Maven 3.9 or later.

## Build and run

Run these commands from the project root:

```sh
mvn test
mvn package
java -jar target/swmcp-0.0.1-SNAPSHOT.jar
```

The last command waits for MCP messages over STDIO. To use the server with an MCP client, configure the client as shown below instead of launching it manually.

## Client configuration

The VS Code configuration is in `.vscode/mcp.json`. After building the project, select **Start** in the MCP servers view.

For Claude Desktop, add the following to `claude_desktop_config.json` and replace the example with the absolute path to the generated JAR:

```json
{
  "mcpServers": {
    "swmcp": {
      "command": "java",
      "args": [
        "-jar",
        "/absolute/path/to/project/target/swmcp-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
```

## Configuration

Override the SWAPI base URL with the `swapi.base-url` property or the `SWAPI_BASE_URL` environment variable. The HTTP client uses a 5-second connection timeout and a 10-second read timeout.

Spring properties disable the web server, startup banner, and console logging so they cannot interfere with the STDIO protocol.

## License

This project is licensed under the GNU General Public License v3.0 or later. See [LICENSE](LICENSE).
