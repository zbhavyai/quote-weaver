# Quote Weaver

Generating humorous quotes (using Gemini) on a particular topic, and as if they were said by some popular person.

## Development

Before you start development on this project, run the prep target. This will install a hook that would check your commit for code formatting issues.

```shell
make prep
```

Run the application in dev mode that enables live coding. Quarkus dev UI would be accessible at [http://127.0.0.1:3003/q/dev-ui/welcome](http://127.0.0.1:3003/q/dev-ui/welcome).

```shell
make dev
```

## Local packaging and running

You must have `docker` or `podman` installed for this to work. `make` will automatically select the available container engine.

1. Build and run the application in containers

   ```shell
   make run
   ```

1. Stop the application and remove the container images

   ```shell
   make destroy
   ```
