package com.lordofthejars.odo.testbed.assertj;

import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.util.List;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class OdoExecutorAssertion extends AbstractAssert<OdoExecutorAssertion, OdoExecutorStub> {

    public OdoExecutorAssertion(OdoExecutorStub odoExecutorStub) {
        super(odoExecutorStub, OdoExecutorAssertion.class);
    }

    public static OdoExecutorAssertion assertThat(OdoExecutorStub odoExecutorStub) {
        return new OdoExecutorAssertion(odoExecutorStub);
    }

    public OdoExecutorAssertion hasExecuted(String ...commands) {
        final List<String> executedCommands = actual.getExecutedCommands();
        Assertions.assertThat(executedCommands).containsExactly(commands);
        return this;
    }

}
