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

## Sample Tweets

The final tweet posted would look like this:

```
A faulty line, a hidden blight,
It steals away my coding light.
I search and probe with earnest plea,
To find the truth, and set it free.

- as if by Andrew Garfield

#quoteweaver #project #rhymingquotes #technology #programming
```

See my [X profile](https://x.com/zbhavyai/) for more such tweets.
