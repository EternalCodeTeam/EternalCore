package com.eternalcode.annotations.scan.reflect;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class PackageUtil {

    public static PackageStack createPackageStack(Package packageToSearch, ClassLoader classLoader) {
        return resolvePackageStack(packageToSearch, classLoader).packageStack();
    }

    private static Result resolvePackageStack(Package packageToSearch, ClassLoader classLoader) {
        String packageName = packageToSearch.getName();

        try {
            PackageStack packageStack = PackageStack.empty(packageToSearch);

            ClassPath classPath = ClassPath.from(classLoader);
            Set<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive(packageName);
            List<String> loadedPackages = new ArrayList<>();

            for (ClassPath.ClassInfo info : classes) {
                String subPackageName = info.getPackageName();

                if (subPackageName.equals(packageName)) {
                    try {
                        Class<?> clazz = Class.forName(info.getName(), false, classLoader);

                        packageStack = packageStack.withClass(clazz);
                        continue;
                    }
                    catch (NoClassDefFoundError error) {
                        continue;
                    }
                }

                if (loadedPackages.contains(subPackageName)) {
                    continue;
                }

                try {
                    Class.forName(info.getName(), false, classLoader);
                }
                catch (NoClassDefFoundError error) {
                    continue;
                }

                Package subPackage = classLoader.getDefinedPackage(subPackageName);
                Result result = resolvePackageStack(subPackage, classLoader);
                PackageStack subPackageStack = result.packageStack();

                loadedPackages.addAll(result.loadedPackages());

                if (!subPackageStack.getClasses().isEmpty() || !subPackageStack.getSubPackages().isEmpty()) {
                    packageStack = packageStack.withSubPackage(subPackageStack);
                }

                loadedPackages.add(subPackageName);
            }

            return new Result(packageStack, loadedPackages);
        }
        catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private record Result(PackageStack packageStack, List<String> loadedPackages) {
    }

}
