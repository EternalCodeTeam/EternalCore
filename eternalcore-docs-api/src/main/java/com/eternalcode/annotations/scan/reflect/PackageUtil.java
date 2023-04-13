package com.eternalcode.annotations.scan.reflect;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class PackageUtil {

    public static PackageStack createPackageStack(Package packageToSearch, ClassLoader classLoader) {
        String packageName = packageToSearch.getName();

        try {
            PackageStack packageStack = PackageStack.empty(packageToSearch);

            ClassPath classPath = ClassPath.from(classLoader);
            Set<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive(packageName);
            List<String> loadedPackages = new ArrayList<>();

            for (ClassPath.ClassInfo info : classes) {
                String subPackageName = info.getPackageName();

                if (subPackageName.equals(packageName)) {
                    Class<?> clazz = Class.forName(info.getName());

                    packageStack = packageStack.withClass(clazz);
                    continue;
                }

                if (loadedPackages.contains(subPackageName)) {
                    continue;
                }

                Class.forName(info.getName());
                Package subPackage = classLoader.getDefinedPackage(subPackageName);
                PackageStack subPackageStack = createPackageStack(subPackage, classLoader);

                if (!subPackageStack.getClasses().isEmpty() && subPackageStack.getSubPackages().isEmpty()) {
                    packageStack = packageStack.withSubPackage(subPackageStack);
                }

                loadedPackages.add(subPackageName);
            }

            return packageStack;
        }
        catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

}
