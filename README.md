# Quote Weaver

[![Build](https://img.shields.io/github/actions/workflow/status/zbhavyai/quote-weaver/build.yml?label=Build)](https://github.com/zbhavyai/quote-weaver/actions/workflows/build.yml)
[![License](https://img.shields.io/github/license/zbhavyai/quote-weaver?label=License)](https://github.com/zbhavyai/quote-weaver/blob/main/LICENSE)

Generating and tweeting humorous rhyming quotes on programming topics using Gemini API, and as if they were said by a celebrity.

## Celebrity List

The list of celebrities was exported from IMDb's list of [Top 1000 Actors and Actresses](https://www.imdb.com/list/ls058011111/) and stored [here](src/main/resources/celebrities.json).

## Development

1. Put your API keys in environment variables or in the [`application.properties`](src/main/resources/application.properties) file. You would need to have Gemini API key and Twitter API keys. Refer to the `application.properties` file to see the required properties.

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
This old code, it runs so slow,
A tangled mess, where seeds don't grow.
But deep inside, a purpose lies,
Beneath the dust, a truth that never dies.

- as if by Kevin Costner

#quoteweaver #rhymingquotes #programmingquotes #project #quarkus
```

See my [X profile](https://x.com/zbhavyai/) for more such tweets.
