# SWMCP

An MCP server built with Java 25 and Spring Boot 4.1.1 that queries [swapi.info](https://swapi.info/). It uses Spring AI MCP 2.0.1 and the Streamable HTTP transport. No API key is required.

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

The server listens on port `8080` and exposes the MCP endpoint at `http://localhost:8080/mcp`.

## Client configuration

The VS Code configuration is in `.vscode/mcp.json`. Start the application and then select **Start** in the MCP servers view. Other Streamable HTTP clients can connect to `http://localhost:8080/mcp`.

## Docker

Build and run the image:

```sh
docker build -t swmcp .
docker run --rm -p 8080:8080 swmcp
```

The container runs as an unprivileged user. Set a different SWAPI endpoint with `-e SWAPI_BASE_URL=https://example.test`.

## Configuration

Override the listening port with `PORT`. Override the SWAPI base URL with the `swapi.base-url` property or the `SWAPI_BASE_URL` environment variable. The HTTP client uses a 5-second connection timeout and a 10-second read timeout.

## License

This project is licensed under the GNU General Public License v3.0 or later. See [LICENSE](LICENSE).
