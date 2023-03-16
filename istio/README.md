# Istio Samples

## OpenShift Service Mesh preparation

See [OpenShift Docs](https://docs.openshift.com/container-platform/4.11/service_mesh/v2x/installing-ossm.html)

Install in order:

* **OpenShift Elasticsearch**
Namespace: openshift-operators-redhat
* **Red Hat OpenShift distributed tracing platform**
Namespace: openshift-distributed-tracing
* **Kiali**
Namespace: openshift-operators
* **OpenShift Service Mesh Console**  
Namespace: openshift-operators
* **Red Hat OpenShift Service Mesh**
Namespace: openshift-operators  

Then:

* Create "servicemesh-apps" namespace, where the apps will be installed inside
    ```sh
    oc create namespace servicemesh-apps
    ```
* Create "istio-system" namespace, where the Service Mesh Control plane will reside, and switch to it
    ```sh
    oc new-project istio-system
    ```
* Install ServiceMeshControlPlane (kubernetes/controlplane.yml) inside "istio-system" namespace
    ```sh
    oc apply -f /kubernetes/controlplane.yml
    ```
* Create ServiceMeshMemberRoll (kubernetes/memberroll.yml) inside "istio-system" namespace
    ```sh
    oc apply -f kubernetes/memberroll.yml
    ```
* Create Gateway (kubernetes/gateway.yml)  
    ```sh
    oc apply -f kubernetes/gateway.yml
    ```

## Deploy the apps


```sh
oc create -f kubernetes/a-deploy.yml
oc create -f kubernetes/b-deploy.yml
oc create -f kubernetes/gateway.yml
```

Get the route to Service A:
```sh
oc get route istio-ingressgateway -n YOUR_ISTIO_SYSTEM_NAMESPACE
curl ROUTE/service-a
```

Now play around with the 'crash' and 'repair' endpoints of Service B (just make port-forwarding to call these endpoints, then call the route to Service A to see what happens).

## Circuit Breaker and Retry

Bring a Circuit Breaker and Retry policy in place, to avoid cascading failures and error propagation to the end user if an app is unhealthy (like Service B one app instance is in crash mode).

```sh
oc create -f kubernetes/circuitbreaker_dr.yml
oc create -f kubernetes/retry_vs.yml
```

## Traffic Mirroring

First we do some cleaning and remove our Circuit Breaker and Retry policy.

```sh
oc delete -f kubernetes/circuitbreaker_dr.yml
oc delete -f kubernetes/retry_vs.yml
```

Now we deploy the "audit" app Service X:

```sh
oc create -f kubernetes/x-deploy.yml
```

Follow the logs of Service X:

```sh
oc logs -f SERVICE_X_POD_NAME
```

Open second terminal and call Service A:

```sh
curl ROUTE/service-a
```

Check the logs of Service X. Nothing there.

Now we ask Istio to do the traffic mirroring, sending a copy of each request to service-b to service-x:

```sh
oc create -f kubernetes/mirroring_dr_vs.yml
```

Call Service A endpoint again and check the logs of Service X. Now we have our "auditing" working.
