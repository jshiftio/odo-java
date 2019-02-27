package com.lordofthejars.odo.testbed.junit5;

import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class OdoExecutorStubInjector implements ParameterResolver {

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
}
