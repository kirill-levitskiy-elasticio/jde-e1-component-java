package io.elastic.jdee1;

import io.elastic.api.InvalidCredentialsException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdeCredentialsVerifier implements io.elastic.api.CredentialsVerifier {

  private static final Logger logger = LoggerFactory.getLogger(JdeCredentialsVerifier.class);

  @Override
  public void verify(JsonObject configuration) throws InvalidCredentialsException {
    Utils jdeinstance = new Utils();
    final JsonObjectBuilder result = Json.createObjectBuilder();

    try {
      result.add("result", jdeinstance.getTemplate_actionPerformed(configuration));
    } catch (Exception e) {
      throw new InvalidCredentialsException("Failed to connect to instance", e);
    } finally {
      if (result != null) {
        logger.info("Closing database connection");
      } else {
        logger.error("Failed to closed database connection");
      }
    }
  }

}
