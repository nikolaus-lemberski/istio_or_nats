# NATS project

Demo app of 2 services and NATS in request/reply mode. For details of the projects see README files in *service-a* and *service-b*.

## Installation

### Podman / Docker

```sh
podman-compose up --build -d
```

### Kubernetes / OpenShift

#### NATS

```sh
helm repo add nats https://nats-io.github.io/k8s/helm/charts/
helm install nats nats/nats --values kubernetes/nats-ha.yaml
```

#### Apps

```sh
oc create -f kubernetes/a-deploy.yml
oc create -f kubernetes/b-deploy.yml
```

And the "silent" listener app Service X:

```sh
oc create -f kubernetes/x-deploy.yml
```