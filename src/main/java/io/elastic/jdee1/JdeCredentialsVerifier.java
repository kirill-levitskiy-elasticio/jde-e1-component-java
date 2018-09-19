package io.elastic.jdee1;

import io.elastic.api.CredentialsVerifier;
import io.elastic.api.InvalidCredentialsException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdeCredentialsVerifier implements CredentialsVerifier {

  private static final Logger logger = LoggerFactory.getLogger(JdeCredentialsVerifier.class);

  @Override
  public void verify(JsonObject configuration) throws InvalidCredentialsException {
    logger.info("About to verify provided JDE E1 Credentials");

    Utils jdeinstance = new Utils();
    final JsonObjectBuilder result = Json.createObjectBuilder();

    try {
      result.add("result", jdeinstance.getTemplate_actionPerformed(configuration));
      logger.info("Result: {}", result);
    } catch (Exception e) {
      throw new InvalidCredentialsException("Failed to connect to instance", e);
    } finally {
      if (result != null) {
        logger.info("Closing connection");
      } else {
        logger.error("Failed to closed connection");
      }
    }
  }

}
