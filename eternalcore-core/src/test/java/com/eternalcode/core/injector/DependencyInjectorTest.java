package com.eternalcode.core.injector;

import com.eternalcode.core.injector.annotations.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@DependencyInjectorTest.CustomService
class DependencyInjectorTest {

    static class ServiceA {
    }

    @Retention(RetentionPolicy.RUNTIME)

    @interface CustomService {
    }

    static class ServiceB {
        private final ServiceA serviceA;

        @Inject
        public ServiceB(ServiceA serviceA) {
            this.serviceA = serviceA;
        }
    }

    static class ServiceC {
        private final ServiceB serviceB;

        @Inject
        public ServiceC(ServiceB serviceB) {
            this.serviceB = serviceB;
        }
    }

    static class ServiceX {
        private final ServiceB serviceB;

        @Inject
        public ServiceX(ServiceB serviceB) {
            this.serviceB = serviceB;
        }
    }

    static class ServiceY {
        private final ServiceB serviceB;
        private final ServiceX serviceX;

        @Inject
        public ServiceY(ServiceB serviceB, ServiceX serviceX) {
            this.serviceB = serviceB;
            this.serviceX = serviceX;
        }

    }

    static class ServiceCycled {
        private final ServiceCycled serviceCycled;

        @Inject
        public ServiceCycled(ServiceCycled serviceCycled) {
            this.serviceCycled = serviceCycled;
        }
    }

    static class ServiceDeepCycled1 {
        private final ServiceDeepCycled2 serviceDeepCycled2;

        @Inject
        public ServiceDeepCycled1(ServiceDeepCycled2 serviceDeepCycled2) {
            this.serviceDeepCycled2 = serviceDeepCycled2;
        }
    }

    static class ServiceDeepCycled2 {
        private final ServiceDeepCycled1 serviceDeepCycled1;

        @Inject
        public ServiceDeepCycled2(ServiceDeepCycled1 serviceDeepCycled1) {
            this.serviceDeepCycled1 = serviceDeepCycled1;
        }
    }

    @Test
    @DisplayName("Should create single instance of service")
    void shouldCreateSingleInstance() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.register(ServiceA.class);

        ServiceA serviceA = dependencyInjector.getDependency(ServiceA.class);

        assertNotNull(serviceA);
    }

    @Test
    @DisplayName("Should create single instance of service with dependency")
    void shouldCreateSingleInstanceWithDependency() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.register(ServiceA.class);
        dependencyInjector.register(ServiceB.class);

        ServiceB serviceB = dependencyInjector.getDependency(ServiceB.class);

        assertNotNull(serviceB);
        assertNotNull(serviceB.serviceA);
        assertSame(serviceB.serviceA, dependencyInjector.getDependency(ServiceA.class));
    }

    @Test
    @DisplayName("Should create single instance of service with chained dependencies")
    void shouldCreateSingleInstanceWithChainedDependencies() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.register(ServiceA.class);
        dependencyInjector.register(ServiceB.class);
        dependencyInjector.register(ServiceC.class);
        dependencyInjector.register(ServiceX.class);
        dependencyInjector.register(ServiceY.class);

        ServiceC serviceC = dependencyInjector.getDependency(ServiceC.class);
        ServiceY serviceY = dependencyInjector.getDependency(ServiceY.class);

        assertNotNull(serviceC);
        assertNotNull(serviceC.serviceB);
        assertNotNull(serviceC.serviceB.serviceA);
        assertNotNull(serviceY);
        assertNotNull(serviceY.serviceB);
        assertNotNull(serviceY.serviceB.serviceA);
        assertNotNull(serviceY.serviceX);
        assertNotNull(serviceY.serviceX.serviceB);
        assertNotNull(serviceY.serviceX.serviceB.serviceA);

        assertSame(serviceC.serviceB, serviceY.serviceB);
        assertSame(serviceC.serviceB.serviceA, serviceY.serviceB.serviceA);
        assertSame(serviceC.serviceB.serviceA, serviceY.serviceX.serviceB.serviceA);
    }

    @Test
    @DisplayName("Should throw exception when dependency not found")
    void shouldThrowExceptionWhenDependencyNotFound() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.register(ServiceA.class);

        assertThrows(InjectException.class, () -> dependencyInjector.getDependency(ServiceB.class));
    }

    @Test
    @DisplayName("Should throw exception when dependency not found and no dependency registered")
    void shouldThrowExceptionWhenDependencyNotFoundAndNoDependencyRegistered() {
        DependencyInjector dependencyInjector = new DependencyInjector();

        assertThrows(InjectException.class, () -> dependencyInjector.getDependency(ServiceB.class));
    }

    @Test
    @DisplayName("Should throw exception when dependency cycled")
    void shouldThrowExceptionWhenDependencyCycled() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.register(ServiceCycled.class);

        assertThrows(InjectException.class, () -> dependencyInjector.getDependency(ServiceCycled.class));
    }

    @Test
    @DisplayName("Should throw exception when dependency deep cycled")
    void shouldThrowExceptionWhenDependencyDeepCycled() {
        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.register(ServiceDeepCycled1.class);
        dependencyInjector.register(ServiceDeepCycled2.class);

        assertThrows(InjectException.class, () -> dependencyInjector.getDependency(ServiceDeepCycled1.class));
    }

    @Test
    @DisplayName("Should scan package and register dependencies")
    void shouldScanPackageAndRegisterDependencies() {
        DependencyInjector dependencyInjector = new DependencyInjector(
            new DependencyProcessors()
                .on(CustomService.class)
        )
            .scan(dependencyScanner -> dependencyScanner
                .packages("com.eternalcode.core.injector")
            );


        DependencyInjectorTest service = dependencyInjector.getDependency(DependencyInjectorTest.class);
        assertNotNull(service);
    }

    @Test
    @DisplayName("Should scan package and auto register dependencies")
    void shouldScanPackageAndAutoRegisterDependencies() {
        AtomicBoolean isCreated = new AtomicBoolean(false);

        DependencyInjector dependencyInjector = new DependencyInjector(
            new DependencyProcessors()
                .on(CustomService.class, instance -> isCreated.set(instance != null))
        )
            .scan(dependencyScanner -> dependencyScanner
                .packages("com.eternalcode.core.injector")
            )
            .initializeAll();

        assertTrue(isCreated.get());
    }

}
