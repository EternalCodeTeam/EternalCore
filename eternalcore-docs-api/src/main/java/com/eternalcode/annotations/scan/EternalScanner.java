package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.reflect.PackageStack;
import com.eternalcode.annotations.scan.reflect.PackageUtil;

import java.util.ArrayList;
import java.util.List;

public class EternalScanner {

    private final ClassLoader classLoader;
    private final Package packageToScan;

    public EternalScanner(ClassLoader classLoader, Package packageToScan) {
        this.classLoader = classLoader;
        this.packageToScan = packageToScan;
    }

    public <RESULT, RESOLVER extends EternalScanResolver<RESULT>> List<RESULT> scan(RESOLVER resolver) {
        PackageStack packageStack = PackageUtil.createPackageStack(this.packageToScan, this.classLoader);

        return this.scan(packageStack, resolver);
    }

    private <RESULT, RESOLVER extends EternalScanResolver<RESULT>> List<RESULT> scan(PackageStack packageStack, RESOLVER resolver) {
        List<RESULT> results = new ArrayList<>();

        for (Class<?> classToScan : packageStack.getClasses()) {
            EternalScanRecord record = new EternalScanRecord(classToScan, List.of(classToScan.getDeclaredMethods()));

            results.addAll(resolver.resolve(record));
        }

        for (PackageStack subPackageStack : packageStack.getSubPackages()) {
            results.addAll(this.scan(subPackageStack, resolver));
        }

        return results;
    }

}
