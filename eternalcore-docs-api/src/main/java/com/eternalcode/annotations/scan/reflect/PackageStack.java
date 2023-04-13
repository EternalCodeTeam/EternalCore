package com.eternalcode.annotations.scan.reflect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PackageStack {

    private final Package original;
    private final List<Class<?>> classes;
    private final List<PackageStack> subPackages;

    private PackageStack(Package original, List<Class<?>> classes, List<PackageStack> subPackages) {
        this.original = original;
        this.classes = classes;
        this.subPackages = subPackages;
    }

    static PackageStack empty(Package pack) {
        return new PackageStack(pack, Collections.emptyList(), Collections.emptyList());
    }

    static PackageStack of(Package pack, Collection<Class<?>> classes, Collection<PackageStack> subPackages) {
        return new PackageStack(pack, new ArrayList<>(classes), new ArrayList<>(subPackages));
    }

    public Package getOriginal() {
        return original;
    }

    public String getName() {
        return original.getName();
    }

    public List<Class<?>> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<PackageStack> getSubPackages() {
        return Collections.unmodifiableList(subPackages);
    }

    PackageStack withSubPackage(PackageStack subPackage) {
        ArrayList<PackageStack> subPackages = new ArrayList<>(this.subPackages);
        subPackages.add(subPackage);

        return new PackageStack(this.original, this.classes, subPackages);
    }

    PackageStack withClass(Class<?> clazz) {
        ArrayList<Class<?>> classes = new ArrayList<>(this.classes);
        classes.add(clazz);

        return new PackageStack(this.original, classes, this.subPackages);
    }

}
