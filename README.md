# RSO: Microservice Events

Microservice which manages events in our service

## Prerequisites

```bash
docker run -d --name pg-events -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=events -p 5432:5432 postgres:13
```