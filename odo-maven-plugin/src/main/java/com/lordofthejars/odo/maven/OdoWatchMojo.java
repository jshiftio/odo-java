package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.WatchCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "watch")
public class OdoWatchMojo extends AbstractMojo {
    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());
    @Parameter
    protected Map<String, String> watch;

    @Parameter
    protected String componentName;

    @Parameter
    protected int delay;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        WatchCommand.Builder builder = odo.watch();

        if (componentName!= null && componentName.length() > 0) {
            builder.withComponentName(componentName);
        }

        if (delay > 0) {
            builder.withDelay(delay);
        }

        WatchCommand watchCommand = builder.build();
        injectFields(watchCommand, watch, logger);

        watchCommand.execute();
    }
}
