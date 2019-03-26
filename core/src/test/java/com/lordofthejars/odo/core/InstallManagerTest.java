package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.LocationResolver;
import com.lordofthejars.odo.api.OdoConfiguration;
import com.lordofthejars.odo.core.resolvers.LocationResolverChain;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstallManagerTest {

    @Mock
    LocationResolverChain locationResolverChain;

    @Mock
    LocationResolver locationResolver;

    @Test
    public void should_install_file_with_executable_permission() throws IOException {

        // Given

        when(locationResolverChain.getLocationResolver(any(OdoConfiguration.class))).thenReturn(locationResolver);
        when(locationResolver.getName()).thenReturn("helloworld.txt");
        when(locationResolver.loadResource()).thenReturn(InstallManagerTest.class.getClassLoader().getResourceAsStream("binaries/hello.txt"));

        InstallManager installManager = new InstallManager();
        installManager.locationResolverChain = locationResolverChain;

        // When

        final Path installPath = installManager.install(new OdoConfiguration());

        // Then
        assertThat(installPath)
            .exists()
            .isExecutable();

    }

    @Test
    public void should_install_file_in_concrete_location(@TempDir Path installationDir) throws IOException {

        // Given

        when(locationResolverChain.getLocationResolver(any(OdoConfiguration.class))).thenReturn(locationResolver);
        when(locationResolver.getName()).thenReturn("helloworld.txt");
        when(locationResolver.loadResource()).thenReturn(InstallManagerTest.class.getClassLoader().getResourceAsStream("binaries/hello.txt"));

        InstallManager installManager = new InstallManager();
        installManager.locationResolverChain = locationResolverChain;

        // When

        final OdoConfiguration odoConfiguration = new OdoConfiguration();
        odoConfiguration.setInstallationDir(installationDir);

        final Path installPath = installManager.install(odoConfiguration);

        // Then

        assertThat(installPath).startsWith(installationDir);

    }

    @Test
    public void should_uninstall_file() throws IOException {

        // Given

        when(locationResolverChain.getLocationResolver(any(OdoConfiguration.class))).thenReturn(locationResolver);
        when(locationResolver.getName()).thenReturn("helloworld.txt");
        when(locationResolver.loadResource()).thenReturn(InstallManagerTest.class.getClassLoader().getResourceAsStream("binaries/hello.txt"));

        InstallManager installManager = new InstallManager();
        installManager.locationResolverChain = locationResolverChain;

        // When

        final Path installPath = installManager.install(new OdoConfiguration());
        installManager.uninstall();

        // Then

        assertThat(installPath)
            .doesNotExist();


    }

}
