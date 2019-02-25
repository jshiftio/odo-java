package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.OdoConfiguration;
import com.lordofthejars.odo.core.commands.AppCommand;
import com.lordofthejars.odo.core.commands.AppCreateCommand;
import com.lordofthejars.odo.core.commands.AppDeleteCommand;
import com.lordofthejars.odo.core.commands.AppDescribeCommand;
import com.lordofthejars.odo.core.commands.AppListCommand;
import com.lordofthejars.odo.core.commands.AppSetCommand;
import com.lordofthejars.odo.core.commands.CatalogCommand;
import com.lordofthejars.odo.core.commands.CatalogListCommand;
import com.lordofthejars.odo.core.commands.ComponentCommand;
import com.lordofthejars.odo.core.commands.ComponentCreateCommand;
import com.lordofthejars.odo.core.commands.ComponentDeleteCommand;
import com.lordofthejars.odo.core.commands.ComponentDescribeCommand;
import com.lordofthejars.odo.core.commands.ComponentLinkCommand;
import com.lordofthejars.odo.core.commands.ComponentListCommand;
import com.lordofthejars.odo.core.commands.ComponentPushCommand;
import com.lordofthejars.odo.core.commands.ComponentUnlinkCommand;
import com.lordofthejars.odo.core.commands.ComponentUpdateCommand;
import com.lordofthejars.odo.core.commands.ProjectCommand;
import com.lordofthejars.odo.core.commands.ProjectCreateCommand;
import com.lordofthejars.odo.core.commands.ProjectDeleteCommand;
import com.lordofthejars.odo.core.commands.ProjectSetCommand;
import com.lordofthejars.odo.core.commands.ServiceCommand;
import com.lordofthejars.odo.core.commands.ServiceCreateCommand;
import com.lordofthejars.odo.core.commands.ServiceDeleteCommand;
import com.lordofthejars.odo.core.commands.StorageCommand;
import com.lordofthejars.odo.core.commands.StorageCreateCommand;
import com.lordofthejars.odo.core.commands.StorageDeleteCommand;
import com.lordofthejars.odo.core.commands.StorageListCommand;
import com.lordofthejars.odo.core.commands.StorageMountCommand;
import com.lordofthejars.odo.core.commands.StorageUnmountCommand;
import com.lordofthejars.odo.core.commands.UrlCommand;
import com.lordofthejars.odo.core.commands.UrlCreateCommand;
import com.lordofthejars.odo.core.commands.UrlDeleteCommand;
import com.lordofthejars.odo.core.commands.UrlListCommand;
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

    public ComponentCreateCommand.Builder createComponent(String componentType) {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentCreateCommand.Builder(componentCommand, componentType, this.odoExecutor);
    }

    public ComponentDeleteCommand.Builder deleteComponent(String componentName) {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentDeleteCommand.Builder(componentCommand, componentName, this.odoExecutor);
    }

    public ComponentUpdateCommand.Builder updateComponent() {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentUpdateCommand.Builder(componentCommand, odoExecutor);
    }

    public ComponentPushCommand.Builder pushComponent() {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentPushCommand.Builder(componentCommand, this.odoExecutor);
    }

    public ComponentLinkCommand.Builder linkComponent(String name) {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentLinkCommand.Builder(componentCommand, name, this.odoExecutor);
    }

    public ComponentUnlinkCommand.Builder unlinkComponent(String name) {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentUnlinkCommand.Builder(componentCommand, name, this.odoExecutor);
    }

    public ComponentDescribeCommand.Builder describeComponent() {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentDescribeCommand.Builder(componentCommand, odoExecutor);
    }

    public ComponentListCommand.Builder listComponents() {
        final ComponentCommand componentCommand = new ComponentCommand.Builder().build();
        return new ComponentListCommand.Builder(componentCommand, odoExecutor);
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

    public AppListCommand.Builder listApps() {
        final AppCommand appCommand = new AppCommand.Builder().build();
        return new AppListCommand.Builder(appCommand, this.odoExecutor);
    }

    public AppDescribeCommand.Builder describeApp() {
        final AppCommand appCommand = new AppCommand.Builder().build();
        return new AppDescribeCommand.Builder(appCommand, this.odoExecutor);
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

    public StorageListCommand.Builder listStorage() {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageListCommand.Builder(storageCommand, odoExecutor);
    }

    public UrlCreateCommand.Builder createUrl() {
        final UrlCommand urlCommand = new UrlCommand.Builder().build();
        return new UrlCreateCommand.Builder(urlCommand, this.odoExecutor);
    }

    public UrlDeleteCommand.Builder deleteUrl(String urlName) {
        final UrlCommand urlCommand = new UrlCommand.Builder().build();
        return new UrlDeleteCommand.Builder(urlCommand, urlName, this.odoExecutor);
    }

    public UrlListCommand.Builder listUrls() {
        final UrlCommand urlCommand = new UrlCommand.Builder().build();
        return new UrlListCommand.Builder(urlCommand, odoExecutor);
    }

}
