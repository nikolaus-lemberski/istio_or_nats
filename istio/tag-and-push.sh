#!/bin/bash

echo "-- Pushing istio-service-a to quay.io"
podman tag istio-service-a:latest quay.io/nlembers/istio/istio-service-a:1.0
podman push quay.io/nlembers/istio/istio-service-a:1.0
echo -e "-- Done\n"

echo "-- Pushing istio-service-b to quay.io"
podman tag istio-service-b:latest quay.io/nlembers/istio/istio-service-b:1.0
podman push quay.io/nlembers/istio/istio-service-b:1.0
echo -e "-- Done\n"

echo "-- Pushing istio-service-x to quay.io"
podman tag istio-service-x:latest quay.io/nlembers/istio/istio-service-x:1.0
podman push quay.io/nlembers/istio/istio-service-x:1.0
echo -e "-- Done\n"
