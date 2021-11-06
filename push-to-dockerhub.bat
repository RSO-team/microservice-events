docker build -t lgaljo/rt_basketball_events -f Dockerfile_with_maven_build .
docker tag lgaljo/rt_basketball_events lgaljo/rt_basketball_events:latest
docker push -a lgaljo/rt_basketball_events