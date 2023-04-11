package com.eternalcode.annotations.scan.reflect;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class PackageUtil {

    @FeatureDocs(
        name = "createPackageStack",
        permission = "com.eternalcode.annotations.createPackageStack",
        description = {
            "Creates a PackageStack from a package.",
            "Returns a PackageStack."
        }
    )
    public static PackageStack createPackageStack(Package packageToSearch, ClassLoader classLoader) {
        String packageName = packageToSearch.getName();

        try {
            PackageStack packageStack = PackageStack.empty(packageToSearch);

            ClassPath classPath = ClassPath.from(classLoader);
            Set<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive(packageName);
            int packageNameLength = packageName.length();
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

                if (subPackageName.substring(packageNameLength + 1).contains(".")) {
                    continue;
                }

                Class.forName(info.getName());
                Package subPackage = classLoader.getDefinedPackage(subPackageName);
                PackageStack subPackageStack = createPackageStack(subPackage, classLoader);

                packageStack = packageStack.withSubPackage(subPackageStack);
                loadedPackages.add(subPackageName);
            }

            return packageStack;
        }
        catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

}
