package com.lordofthejars.odo.testbed.junit5;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.CatalogCommand;
import com.lordofthejars.odo.core.commands.CatalogListCommand;
import com.lordofthejars.odo.testbed.api.Catalog;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

public class OpenShiftCatalogConditionExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

        AnnotatedElement annotatedElement =
            context.getElement().orElseThrow(() -> new IllegalStateException("No test class found."));

        if (isAnnotated(annotatedElement, Catalog.class)) {

            final Odo odo = new Odo();
            final Catalog catalog = annotatedElement.getAnnotation(Catalog.class);

            if (catalog.components().length > 0) {

                final List<String> components = getInstalledComponents(odo);
                final String[] requiredComponents = catalog.components();

                for (String requiredComponent : requiredComponents) {
                    if (!components.contains(requiredComponent)) {
                        return ConditionEvaluationResult.disabled(
                            String.format("%s component was expected in catalog but only these elements %s found. "
                                    + "In case of Java run:%n  "
                                    + "oc import-image openjdk18 --from=registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift --confirm %n "
                                    + "oc annotate istag/openjdk18:latest tags=builder %n"
                                , requiredComponent, components));
                    }
                }
            }

            final String[] requiredServices = catalog.services();
            if (requiredServices.length > 0) {
                final List<String> services = getInstalledServices(odo);

                for (String requiredService : requiredServices) {
                    if (!services.contains(requiredService)) {
                        return ConditionEvaluationResult.disabled(
                            String.format("%s service was expected in catalog but only these elements %s found."
                                , requiredService, services));
                    }
                }
            }

            return ConditionEvaluationResult.enabled("All catalog expectations met");
        }



        return ConditionEvaluationResult.enabled("No Catalog restriction.");

    }

    private List<String> getInstalledComponents(final Odo odo) {
        return odo.listCatalog("components").build().execute();
    }

    private List<String> getInstalledServices(final Odo odo) {
        return odo.listCatalog("services").build().execute();
    }
}
