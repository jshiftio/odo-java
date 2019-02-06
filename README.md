## Odo-java

This library provides a wrapper for [odo](https://github.com/redhat-developer/odo) which is a CLI tool for developers who are writing,
building and deploying applications on Openshift. on OpenShift. With Odo, developers get an opinionated CLI tool that supports fast,  iterative development which abstracts away Kubernetes and OpenShift concepts, thus allowing them to focus on what's most important 
to them: code.

### Usage
* Create Odo instace:
```
        final Odo odo = new Odo();
```

* Create application, add a component of type nodejs to your application :
```
        final CreateCommand createCommand = new CreateCommand
            .Builder("nodejs")
            .withComponentName("nodejs")
            .build();

        odo.execute(cloneRepo, createCommand); 
```

* Deploy your application :
```
        final PushCommand pushCommand = new PushCommand
            .Builder()
            .build();

        odo.execute(cloneRepo, pushCommand);
```

* Expose your application endpoint :
```
        final UrlCreateCommand urlCreateCommand = new UrlCreateCommand
            .Builder()
            .withComponentName("route")
            .build();

         odo.execute(cloneRepo, urlCommand);

```
