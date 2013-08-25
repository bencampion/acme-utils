acme-utils
==========

AcmeUtils.java
--------------

Useful Java utility methods implemented using novel, inefficient, obfuscated and wrong headed techniques.

InstanceUtils.java
------------------

Lightweight, enterprise ready, instance management solution that can be used to create and manage singleton instances of any class with a nullary constructor. Evil reflection is used to allow new instantiation of classes with inaccessible constructors, such as classes that implement the singleton pattern. InstanceUtils is itself one such class, making it possible for an InstanceUtils instance to create and manage one new instance of itself. This powerful feature allows the creation of unlimited singletons where each singleton instance is managed via an instance of InstanceUtils.

Example:

```java
Object o1 = InstanceUtils.getInstance().getInstance(Object.class);
Object o2 = InstanceUtils.getInstance().getInstance(Object.class);
Object o3 = InstanceUtils.getInstance().getInstance(InstanceUtils.class).getInstance(Object.class);
Object o4 = InstanceUtils.getInstance().getInstance(InstanceUtils.class).getInstance(Object.class);
System.out.println(o1 == o2); // true
System.out.println(o2 == o3); // false
System.out.println(o3 == o4); // true
```

Convinience methods:

```java
InstanceUtils iu = InstanceUtils.getInstance(3);
int n = InstanceUtils.instances();
String msg = String.format("That was instance %d of %d", iu.instance(), n);
System.out.println(msg);
```