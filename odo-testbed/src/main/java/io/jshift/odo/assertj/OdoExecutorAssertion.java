package io.jshift.odo.assertj;

import java.util.List;
import io.jshift.odo.odo.OdoExecutorStub;
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
