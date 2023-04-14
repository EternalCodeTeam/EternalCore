# The magic docs system

Docs system is a tool that allows developers to place information about their code directly into the source file using
annotations. These annotations are then processed by the system and converted into JSON files that can be used to create
a graphical documentation of the project.

## How to use
1. Add annotations for source file:

#### examples: 

#### Standard feature
```java

@FeatureDocs(
    name = "ExampleFeatureDocsService", // Name of feature
    permission = "example.feature.docs.service", // If feature has permission, please add it here
    description = { // Short description of feature
        "Creates a PackageStack from a package.",
        "Returns a PackageStack."
    }
)
class ExampleFeatureDocsService {
    
    
    @FeatureDocs(
        name = "ExampleFeatureDocsService#exampleMethod", // Name of feature
        permission = "example.feature.docs.service.exampleMethod", // If feature has permission, please add it here
        description = { // Short description of feature
            "Creates a PackageStack from a package.",
            "Returns a PackageStack."
        }
    )
    public void exampleMethod() {}
    
    public void exampleMethod2() {}
}

```

##### LiteCommand Standard
```java
@Route(name = "tpa", aliases = {"teleporta"})
@Permission("eternalcore.tpa")
public class TpaCommand {

    @Execute
    @Permission("eternalcore.tpa.send")
    @DocsDescription(description = "Sends tpa request", arguments = "<player>") // Description of command
    public void send(@Arg String player) {}

    @Execute(route = "accept")
    @Permission("eternalcore.tpa.accept")
    @Description(description = "Accepts tpa request")
    public void accept() {}


    @Execute(route = "deny")
    @Permission("eternalcore.tpa.deny")
    @Description(description = "Denies tpa request")
    public void deny() {}

}
```

#### LiteCommands with @RootRoute usage
```java
@RootRoute
@Permission("permissions.root")
@Permission("permissions.root2")
public class RootTestCommand {

    @Execute(route = "test-root")
    @Permission("permissions.root.execute")
    @DocsDescription(description = "This is descriptions")
    public void execute() {}


    @Execute(route = "test-root other")
    @Permission("permissions.root.execute.other")
    @DocsDescription(description = "This is descriptions", arguments = "<player>")
    public void executeOther(@Arg Player player) {}

}
```
