package io.jshift.odo.junit5;

import io.jshift.odo.api.RecordOutput;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;
import io.jshift.odo.odo.OdoExecutorStub;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

public class OdoExecutorStubInjector implements ParameterResolver, BeforeEachCallback, AfterEachCallback {

    private OdoExecutorStub odoExecutorStub = new OdoExecutorStub();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return OdoExecutorStub.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return odoExecutorStub;
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        final Optional<AnnotatedElement> element = context.getElement();

        element.ifPresent(annotatedElement -> {

            if (isAnnotated(annotatedElement, RecordOutput.class)) {
                final RecordOutput annotation = annotatedElement.getAnnotation(RecordOutput.class);
                odoExecutorStub.recordOutput(annotation.values());
            }

        });

    }

    @Override
    public void afterEach(ExtensionContext context) {
        odoExecutorStub.cleanOutput();
    }
}
