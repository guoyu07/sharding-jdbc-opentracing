# Sharding-JDBC OpenTracing Plugin

## Introduction

The plugin will add [OpenTracing](http://opentracing.io/) instrumentation to Sharding-JDBC. This plugin makes it 
easy for developers using the Sharding-JDBC to incorporate the tracer that support OpenTracing.


## How to use

### Dependency

```xml
<dependency>
    <groupId>io.shardingjdbc</groupId>
    <artifactId>sharding-jdbc-opentracing</artifactId>
    <version>${latest.version}</version>
</dependency>
```

### Set jvm property

Set `-Dsjdbc.opentracing.tracer.class=OPENTRACING_TRACER_CLASS_NAME` to start the application. `OPENTRACING_TRACER_CLASS_NAME` MUST implement
`io.opentracing.Tracer`

### Initialize plugin

Invoke `io.shardingjdbc.opentracing.ShardingJDBCTracer#init` method before using Sharding-JDBC components.