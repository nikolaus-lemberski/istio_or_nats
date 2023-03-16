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



