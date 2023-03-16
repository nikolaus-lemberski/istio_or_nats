#!/bin/bash

echo "-- Pushing nats-service-a to quay.io"
podman tag nats-service-a:latest quay.io/nlembers/nats/nats-service-a:1.0
podman push quay.io/nlembers/nats/nats-service-a:1.0
echo -e "-- Done\n"

echo "-- Pushing nats-service-b to quay.io"
podman tag nats-service-b:latest quay.io/nlembers/nats/nats-service-b:1.0
podman push quay.io/nlembers/nats/nats-service-b:1.0
echo -e "-- Done\n"

echo "-- Pushing nats-service-x to quay.io"
podman tag nats-service-x:latest quay.io/nlembers/nats/nats-service-x:1.0
podman push quay.io/nlembers/nats/nats-service-x:1.0
echo -e "-- Done\n"
