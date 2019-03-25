package com.lordofthejars.odo.detectors;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.component.ComponentDetector;
import com.lordofthejars.odo.detectors.extractor.Dependency;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.service.ServiceDetector;
import com.lordofthejars.odo.detectors.spi.Detector;
import com.lordofthejars.odo.detectors.util.Packaging;
import com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({OdoExecutorStubInjector.class, MockitoExtension.class})
public class DetectorManagerTest {

    @Mock
    Extractor extractor;

    @Mock
    DetectorLoader detectorLoader;

    @Test
    public void should_link_component_and_services(OdoExecutorStub odoExecutorStub) {

        // Given

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(new Dependency("mysql", "mysql-connector-java"));
        Mockito.when(extractor.extractDependencies()).thenReturn(dependencySet);
        Mockito.when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));
        Mockito.when(extractor.extractTypeOfProject()).thenReturn(Packaging.JAR);
        Mockito.when(extractor.extractArtifactId()).thenReturn("demo");

        final Odo odo = new Odo(odoExecutorStub);
        final DetectorManager detectorManager = new DetectorManager(extractor, odo);

        // When

        detectorManager.execute();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create mysql-persistent --plan default --wait",
                "odo component create openjdk18 demo --wait",
                "odo component push",
                "odo url create --component demo --port 8080",
                "odo component link mysql-persistent --component demo --wait");
    }

    @Test
    public void should_order_components_by_order_value(OdoExecutorStub odoExecutorStub) {

        // Given

        final ComponentDetector1 componentDetector1 = Mockito.spy(new ComponentDetector1());
        final ComponentDetector2 componentDetector2 = Mockito.spy(new ComponentDetector2());

        Mockito.when(detectorLoader.componentDetectors()).thenReturn(Arrays.asList(componentDetector2, componentDetector1));
        Mockito.when(detectorLoader.serviceDetectors()).thenReturn(new ArrayList<>());

        final Odo odo = new Odo(odoExecutorStub);
        final DetectorManager detectorManager = new DetectorManager(extractor, odo);
        detectorManager.detectorLoader = detectorLoader;

        // When

        detectorManager.execute();

        // Then

        Mockito.verify(componentDetector1).apply();
        Mockito.verify(componentDetector2, Mockito.times(0)).apply();

    }

    @Test
    public void should_override_services(OdoExecutorStub odoExecutorStub) {

        // Given

        final ServiceDetector1 serviceDetector1 = new ServiceDetector1();
        final ServiceDetector2 serviceDetector2 = new ServiceDetector2();

        Mockito.when(detectorLoader.componentDetectors()).thenReturn(new ArrayList<>());
        Mockito.when(detectorLoader.serviceDetectors()).thenReturn(Arrays.asList(serviceDetector2, serviceDetector1));

        final Odo odo = new Odo(odoExecutorStub);
        final DetectorManager detectorManager = new DetectorManager(extractor, odo);
        detectorManager.detectorLoader = detectorLoader;

        // When

        detectorManager.execute();

        // Then

        Assertions.assertThat(serviceDetector1.applied).isTrue();
        Assertions.assertThat(serviceDetector2.applied).isFalse();
    }

    class ServiceDetector1 extends ServiceDetector {

        boolean applied;

        @Override
        public int order() {
            return 100;
        }

        @Override
        public Optional<Class<? extends Detector>> overrides() {
            return Optional.of(ServiceDetector2.class);
        }

        @Override
        public boolean detect() {
            return true;
        }

        @Override
        public String apply() {
            applied = true;
            return null;
        }
    }

    class ServiceDetector2 extends ServiceDetector {

        boolean applied;

        @Override
        public int order() {
            return 10;
        }

        @Override
        public boolean detect() {
            return true;
        }

        @Override
        public String apply() {
            applied = true;
            return null;
        }
    }

    class ComponentDetector1 extends ComponentDetector {

        @Override
        public int order() {
            return 1000;
        }

        @Override
        public boolean detect() {
            return true;
        }

        @Override
        public String apply() {
            return null;
        }
    }

    class ComponentDetector2 extends ComponentDetector {

        @Override
        public int order() {
            return 0;
        }

        @Override
        public boolean detect() {
            return true;
        }

        @Override
        public String apply() {
            return null;
        }
    }

}
