package org.forgerock.openam.auth.nodes;

import java.util.Collections;
import java.util.Map;

import org.forgerock.openam.auth.node.api.AbstractNodeAmPlugin;
import org.forgerock.openam.auth.node.api.Node;

public class CaptureParametersNodePlugin extends AbstractNodeAmPlugin {

    @Override
    public String getPluginVersion() {
        return "1.1.0";
    }

    protected Map<String, Iterable<? extends Class<? extends Node>>> getNodesByVersion() {
        return Collections.singletonMap(this.getPluginVersion(), Collections.singletonList(CaptureParametersNode.class));
    }
}
