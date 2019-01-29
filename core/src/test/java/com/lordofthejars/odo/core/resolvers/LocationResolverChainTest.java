package com.lordofthejars.odo.core.resolvers;

import com.lordofthejars.odo.api.LocationResolver;
import com.lordofthejars.odo.api.OdoConfiguration;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationResolverChainTest {


    @Test
    public void should_use_classpath_locator_by_default() {

        // Given

        final LocationResolverChain locationResolverChain = new LocationResolverChain();

        // When

        final LocationResolver locationResolver = locationResolverChain.getLocationResolver(new OdoConfiguration());

        // Then

        assertThat(locationResolver)
            .isInstanceOf(ClasspathLocationResolver.class);
    }

    @Test
    public void should_use_url_locator_if_version_set() {

        // Given

        final LocationResolverChain locationResolverChain = new LocationResolverChain();

        // When

        final OdoConfiguration odoConfiguration = new OdoConfiguration();
        odoConfiguration.setVersion("0.0.17");
        final LocationResolver locationResolver = locationResolverChain.getLocationResolver(odoConfiguration);

        // Then

        assertThat(locationResolver)
            .isInstanceOf(UrlLocationResolver.class);

    }

    @Test
    public void should_use_url_locator_if_version_system_property_set() {

        // Given

        final LocationResolverChain locationResolverChain = new LocationResolverChain();

        // When

        System.setProperty(LocationResolverChain.ODO_VERSION, "0.0.17");
        final OdoConfiguration odoConfiguration = new OdoConfiguration();
        final LocationResolver locationResolver = locationResolverChain.getLocationResolver(odoConfiguration);

        // Then

        assertThat(locationResolver)
            .isInstanceOf(UrlLocationResolver.class);
    }

    @Test
    public void should_use_local_locator_if_local_path_set() {

        // Given

        final LocationResolverChain locationResolverChain = new LocationResolverChain();

        // When

        final OdoConfiguration odoConfiguration = new OdoConfiguration();
        odoConfiguration.setLocalOdo(Paths.get("hello.txt"));

        final LocationResolver locationResolver = locationResolverChain.getLocationResolver(odoConfiguration);

        // Then

        assertThat(locationResolver)
            .isInstanceOf(LocalLocationResolver.class);

    }

    @AfterEach
    public void unset_system_property() {
        System.clearProperty(LocationResolverChain.ODO_VERSION);
    }

}
