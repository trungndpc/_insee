package vn.insee.retailer.webhook;

import org.json.JSONObject;

public abstract class ZaloEvent {
    public abstract  boolean isAccepted(JSONObject json);
    public abstract boolean process(JSONObject json);
}
