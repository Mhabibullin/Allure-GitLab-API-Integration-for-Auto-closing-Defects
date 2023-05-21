package mkhabibullin.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {

    public JSONObject toJSONObject(Object object) {
        try {
            return new JSONObject(toJsonString(object));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJsonString(Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(object);
    }

    public Object readValue(String json, Class _class) throws Exception {
        Object object;
        ObjectMapper objectMapper = new ObjectMapper();
        object = objectMapper.readValue(json, _class);
        return object;

    }
}
