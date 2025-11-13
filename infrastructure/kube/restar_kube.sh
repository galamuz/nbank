#!/bin/bash

#minikube start --driver=docker

kubectl delete configmap selenoid-config --ignore-not-found
kubectl create configmap selenoid-config --from-file=browsers.json=./nbank-chart/files/browsers.json

helm uninstall nbank

helm install nbank ./nbank-chart

kubectl port-forward svc/frontend 3000:80
kubectl port-forward svc/backend 4111:4111
kubectl port-forward svc/selenoid 4444:4444
kubectl port-forward svc/selenoid-ui 8080:8080


