package io.elastic.jdee1;

import io.elastic.api.DynamicMetadataProvider;
import io.elastic.api.SelectModelProvider;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionParamsProvider implements DynamicMetadataProvider, SelectModelProvider {

  private static final Logger logger = LoggerFactory.getLogger(FunctionParamsProvider.class);

  public JsonObject getSelectModel(JsonObject configuration) {
    JsonObject result = Json.createObjectBuilder().build();
    JsonObject properties = getColumns(configuration);
    for (Map.Entry<String, JsonValue> entry : properties.entrySet()) {
      JsonValue field = entry.getValue();
      result = Json.createObjectBuilder().add(entry.getKey(), field.toString()).build();
    }
    return result;
  }

  /**
   * Returns Columns list as metadata
   */

  public JsonObject getMetaModel(JsonObject configuration) {
    JsonObject result = Json.createObjectBuilder().build();
    JsonObject inMetadata = Json.createObjectBuilder().build();
    logger.info("configuration: {}", configuration);
    JsonObject properties = getColumns(configuration);
    inMetadata = Json.createObjectBuilder().add("type", "object")
        .add("properties", properties).build();
    result = Json.createObjectBuilder().add("out", inMetadata)
        .add("in", inMetadata).build();
    return result;
  }

  public JsonObject getColumns(JsonObject configuration) {

    JsonObjectBuilder properties = Json.createObjectBuilder();
    Utils utils = new Utils();
    utils.getTemplate_actionPerformed(configuration);

    String ret;
    for(int i = 0; i < utils.BSFNParmsModel.getRowCount(); ++i) {
      ret = (String)utils.BSFNParmsModel.getValueAt(i, 0);
      String value = (String)utils.BSFNParmsModel.getValueAt(i, 1);
      if (value.compareTo("") != 0) {
        utils.setParameterValue(ret, i);
        properties.add(ret, "string");
      }
    }
    return properties.build();
  }

}
