# Quote Weaver

[![Build](https://img.shields.io/github/actions/workflow/status/zbhavyai/quote-weaver/build.yml?label=Build)](https://github.com/zbhavyai/quote-weaver/actions/workflows/build.yml)
[![License](https://img.shields.io/github/license/zbhavyai/quote-weaver?label=License)](https://github.com/zbhavyai/quote-weaver/blob/main/LICENSE)

Generating and tweeting humorous rhyming quotes using Gemini API on programming topics, and as if they were said by a celebrity.

## Development

1. Put your API keys in environment variables or in the [`application.properties`](src/main/resources/application.properties) file.

1. Build and run the application

   ```shell
   make build
   make run
   ```

> [!NOTE]
> This application is configured to work like a command line application, and running it in the dev mode or executing the application's JAR/executable would post the tweet.
