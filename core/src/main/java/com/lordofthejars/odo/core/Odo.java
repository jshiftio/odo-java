package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.OdoConfiguration;
import com.lordofthejars.odo.core.commands.AppCommand;
import com.lordofthejars.odo.core.commands.AppCreateCommand;
import com.lordofthejars.odo.core.commands.AppDeleteCommand;
import com.lordofthejars.odo.core.commands.AppSetCommand;
import com.lordofthejars.odo.core.commands.CatalogCommand;
import com.lordofthejars.odo.core.commands.CatalogListCommand;
import com.lordofthejars.odo.core.commands.CreateCommand;
import com.lordofthejars.odo.core.commands.DeleteCommand;
import com.lordofthejars.odo.core.commands.LinkCommand;
import com.lordofthejars.odo.core.commands.ProjectCommand;
import com.lordofthejars.odo.core.commands.ProjectCreateCommand;
import com.lordofthejars.odo.core.commands.ProjectDeleteCommand;
import com.lordofthejars.odo.core.commands.ProjectSetCommand;
import com.lordofthejars.odo.core.commands.PushCommand;
import com.lordofthejars.odo.core.commands.ServiceCommand;
import com.lordofthejars.odo.core.commands.ServiceCreateCommand;
import com.lordofthejars.odo.core.commands.ServiceDeleteCommand;
import com.lordofthejars.odo.core.commands.StorageCommand;
import com.lordofthejars.odo.core.commands.StorageCreateCommand;
import com.lordofthejars.odo.core.commands.StorageDeleteCommand;
import com.lordofthejars.odo.core.commands.StorageMountCommand;
import com.lordofthejars.odo.core.commands.StorageUnmountCommand;
import com.lordofthejars.odo.core.commands.UnlinkCommand;
import com.lordofthejars.odo.core.commands.UrlCommand;
import com.lordofthejars.odo.core.commands.UrlCreateCommand;
import com.lordofthejars.odo.core.commands.UrlDeleteCommand;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Odo {

    private static final Logger logger = Logger.getLogger(Odo.class.getName());
    private final OdoConfiguration odoConfiguration;

    private InstallManager installManager = new InstallManager();
    private OdoExecutor odoExecutor;

    protected Path odoHome;

    public Odo() {
        this(new OdoConfiguration());
    }

    public Odo(final OdoConfiguration odoConfiguration) {
        this.odoConfiguration = odoConfiguration;
        install();
        odoExecutor = new OdoExecutor(this.odoHome, this.odoConfiguration);
    }

    Odo(OdoExecutor odoExecutor) {
        this.odoConfiguration = new OdoConfiguration();
        this.odoExecutor = odoExecutor;
    }

    protected void install() {
        try {
            odoHome = odoHome == null ? installManager.install(this.odoConfiguration) : odoHome;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public CreateCommand.Builder create(String componentType) {
        return new CreateCommand.Builder(componentType, this.odoExecutor);
    }

    public DeleteCommand.Builder delete(String componentName) {
        return new DeleteCommand.Builder(componentName, this.odoExecutor);
    }

    public PushCommand.Builder push() {
        return new PushCommand.Builder(this.odoExecutor);
    }

    public LinkCommand.Builder link(String name) {
        return new LinkCommand.Builder(name, this.odoExecutor);
    }

    public UnlinkCommand.Builder unlink(String name) {
        return new UnlinkCommand.Builder(name, this.odoExecutor);
    }

    public CatalogListCommand.Builder listCatalog(String component) {
        final CatalogCommand catalogcommand = new CatalogCommand.Builder().build();
        return new CatalogListCommand.Builder(catalogcommand, component, this.odoExecutor);
    }

    public AppCreateCommand.Builder createApp() {
        final AppCommand appCommand = new AppCommand.Builder().build();
        return new AppCreateCommand.Builder(appCommand, this.odoExecutor);
    }

    public AppDeleteCommand.Builder deleteApp(String appName) {
        final AppCommand appCommand = new AppCommand.Builder().build();
        return new AppDeleteCommand.Builder(appCommand, appName, this.odoExecutor);
    }

    public AppSetCommand.Builder setApp(String appName) {
        final AppCommand appCommand = new AppCommand.Builder().build();
        return new AppSetCommand.Builder(appCommand, appName, this.odoExecutor);
    }

    public ProjectCreateCommand.Builder createProject(String projectName) {
        final ProjectCommand projectCommand = new ProjectCommand.Builder().build();
        return new ProjectCreateCommand.Builder(projectCommand, projectName, this.odoExecutor);
    }

    public ProjectDeleteCommand.Builder deleteProject(String projectName) {
        final ProjectCommand projectCommand = new ProjectCommand.Builder().build();
        return new ProjectDeleteCommand.Builder(projectCommand, projectName, odoExecutor);
    }

    public ProjectSetCommand.Builder setProject(String projectName) {
        final ProjectCommand projectCommand = new ProjectCommand.Builder().build();
        return new ProjectSetCommand.Builder(projectCommand, projectName, this.odoExecutor);
    }

    public ServiceCreateCommand.Builder createService(String serviceType, String plan) {
        final ServiceCommand serviceCommand = new ServiceCommand.Builder().build();
        return new ServiceCreateCommand.Builder(serviceCommand, serviceType, plan, this.odoExecutor);
    }

    public ServiceDeleteCommand.Builder deleteService(String serviceName) {
        final ServiceCommand serviceCommand = new ServiceCommand.Builder().build();
        return new ServiceDeleteCommand.Builder(serviceCommand, serviceName, this.odoExecutor);
    }

    public StorageCreateCommand.Builder createStorage(String storageName) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageCreateCommand.Builder(storageCommand, storageName, this.odoExecutor);
    }

    public StorageDeleteCommand.Builder deleteStorage(String storageName) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageDeleteCommand.Builder(storageCommand, storageName, this.odoExecutor);
    }

    public StorageMountCommand.Builder mountStorage(String storageName) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageMountCommand.Builder(storageCommand, storageName, this.odoExecutor);
    }

    public StorageUnmountCommand.Builder unmountStorage(String storageName) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageUnmountCommand.Builder(storageCommand, storageName, this.odoExecutor);
    }

    public UrlCreateCommand.Builder createUrl() {
        final UrlCommand urlCommand = new UrlCommand.Builder().build();
        return new UrlCreateCommand.Builder(urlCommand, this.odoExecutor);
    }

    public UrlDeleteCommand.Builder deleteUrl(String urlName) {
        final UrlCommand urlCommand = new UrlCommand.Builder().build();
        return new UrlDeleteCommand.Builder(urlCommand, urlName, this.odoExecutor);
    }

}
