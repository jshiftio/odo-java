package io.jshift.odo.core.resolvers;

import io.jshift.odo.api.LocationResolver;
import io.jshift.odo.api.OdoConfiguration;
import org.assertj.core.api.Assertions;
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

    @AfterEach
    public void unset_system_property() {
        System.clearProperty(LocationResolverChain.ODO_VERSION);
    }

}
