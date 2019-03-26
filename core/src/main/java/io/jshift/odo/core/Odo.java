package io.jshift.odo.core;

import io.jshift.odo.api.OdoConfiguration;
import io.jshift.odo.core.commands.ComponentCreateCommand;
import java.io.IOException;
import java.nio.file.Path;
import io.jshift.odo.core.commands.AppCommand;
import io.jshift.odo.core.commands.AppCreateCommand;
import io.jshift.odo.core.commands.AppDeleteCommand;
import io.jshift.odo.core.commands.AppDescribeCommand;
import io.jshift.odo.core.commands.AppListCommand;
import io.jshift.odo.core.commands.AppSetCommand;
import io.jshift.odo.core.commands.CatalogCommand;
import io.jshift.odo.core.commands.CatalogListCommand;
import io.jshift.odo.core.commands.ComponentCommand;
import io.jshift.odo.core.commands.ComponentDeleteCommand;
import io.jshift.odo.core.commands.ComponentDescribeCommand;
import io.jshift.odo.core.commands.ComponentLinkCommand;
import io.jshift.odo.core.commands.ComponentListCommand;
import io.jshift.odo.core.commands.ComponentPushCommand;
import io.jshift.odo.core.commands.ComponentUnlinkCommand;
import io.jshift.odo.core.commands.ComponentUpdateCommand;
import io.jshift.odo.core.commands.ProjectCommand;
import io.jshift.odo.core.commands.ProjectCreateCommand;
import io.jshift.odo.core.commands.ProjectDeleteCommand;
import io.jshift.odo.core.commands.ProjectSetCommand;
import io.jshift.odo.core.commands.ServiceCommand;
import io.jshift.odo.core.commands.ServiceCreateCommand;
import io.jshift.odo.core.commands.ServiceDeleteCommand;
import io.jshift.odo.core.commands.StorageCommand;
import io.jshift.odo.core.commands.StorageCreateCommand;
import io.jshift.odo.core.commands.StorageDeleteCommand;
import io.jshift.odo.core.commands.StorageListCommand;
import io.jshift.odo.core.commands.StorageMountCommand;
import io.jshift.odo.core.commands.StorageUnmountCommand;
import io.jshift.odo.core.commands.UrlCommand;
import io.jshift.odo.core.commands.UrlCreateCommand;
import io.jshift.odo.core.commands.UrlDeleteCommand;
import io.jshift.odo.core.commands.UrlListCommand;
import io.jshift.odo.core.commands.WatchCommand;

public class Odo {

    private final OdoConfiguration odoConfiguration;

    private InstallManager installManager = new InstallManager();
    private CliExecutor odoExecutor;

    protected Path odoHome;

    public Odo() {
        this(new OdoConfiguration());
    }

    public Odo(final OdoConfiguration odoConfiguration) {
        this.odoConfiguration = odoConfiguration;
        install();
        odoExecutor = new OdoExecutor(this.odoHome, this.odoConfiguration);
    }

    public Odo(CliExecutor odoExecutor) {
        this.odoConfiguration = new OdoConfiguration();
        this.odoExecutor = odoExecutor;
    }

    protected void install() {
        try {
            if (this.odoConfiguration.isLocalOdoSet()) {
                odoHome = this.odoConfiguration.getLocalOdo();
            } else {
                odoHome = odoHome == null ? installManager.install(this.odoConfiguration) : odoHome;
            }
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

    public AppDeleteCommand.Builder deleteApp() {
        final AppCommand appCommand = new AppCommand.Builder().build();
        return new AppDeleteCommand.Builder(appCommand, this.odoExecutor);
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

    public StorageCreateCommand.Builder createStorage(String storageName, String path) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageCreateCommand.Builder(storageCommand, storageName, path, this.odoExecutor);
    }

    public StorageDeleteCommand.Builder deleteStorage(String storageName) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageDeleteCommand.Builder(storageCommand, storageName, this.odoExecutor);
    }

    public StorageMountCommand.Builder mountStorage(String storageName, String path) {
        final StorageCommand storageCommand = new StorageCommand.Builder().build();
        return new StorageMountCommand.Builder(storageCommand, storageName, path, this.odoExecutor);
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

    public WatchCommand.Builder watch() {
        return new WatchCommand.Builder(odoExecutor);
    }

}
