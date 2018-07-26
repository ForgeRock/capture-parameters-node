package org.forgerock.openam.auth.nodes;

import com.google.inject.assistedinject.Assisted;
import com.sun.identity.sm.RequiredValueValidator;
import org.forgerock.openam.auth.node.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.forgerock.json.JsonValue;
import org.forgerock.openam.annotations.sm.Attribute;

@Node.Metadata(outcomeProvider = SingleOutcomeNode.OutcomeProvider.class, configClass = CaptureParametersNode.Config.class)

public class CaptureParametersNode extends SingleOutcomeNode {


    private final Logger logger = LoggerFactory.getLogger("amAuth");

    private final Config config;

    interface Config {

        @Attribute(order = 100, validators = {RequiredValueValidator.class})
        default Map<String, String> mappings() { return Collections.emptyMap(); }

    }

    @Inject
    public CaptureParametersNode(@Assisted Config config) {
        this.config = config;
    }

    @Override
    public Action process(TreeContext treeContext) throws NodeProcessException {

        JsonValue newState = treeContext.sharedState.copy();

        logger.debug("CaptureParametersNode started");
        config.mappings().forEach((parameterKey,sharedStateKey)->
                {
                    //Pull out the appropriate parameter value based on key
                    List<String> parameterValue = treeContext.request.parameters.get(parameterKey);
                    //Take found parameter value and add to new shared state based on config key
                    newState.put(sharedStateKey,parameterValue);
                }
        );

            //Update shared state
            return goToNext().replaceSharedState(newState).build();
    }

}
