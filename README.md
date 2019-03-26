## Odo-java

[![CircleCI](https://circleci.com/gh/jshiftio/io.jshift.odo-java.svg?style=svg)](https://circleci.com/gh/jshiftio/io.jshift.odo-java)

This library provides a wrapper for [io.jshift.odo](https://github.com/redhat-developer/io.jshift.odo) which is a CLI tool for developers who are writing,
building and deploying applications on Openshift. on OpenShift. With Odo, developers get an opinionated CLI tool that supports fast,  iterative development which abstracts away Kubernetes and OpenShift concepts, thus allowing them to focus on what's most important 
to them: code.

### Usage
* Create Odo instace:
```
        final Odo io.jshift.odo = new Odo();
```

* Create application, add a component of type nodejs to your application :
```
        io.jshift.odo.create("nodejs").build().execute(cloneRepo);
```

* Deploy your application :
```
        io.jshift.odo.push().build().execute(cloneRepo);
```

* Expose your application endpoint :
```
        io.jshift.odo.link("provider").withComponent("consumer").withPort("8080").build().execute(cloneRepo);

```
