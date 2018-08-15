package io.elastic.jdee1;


import io.elastic.api.InvalidCredentialsException;
import io.elastic.api.Message;
import io.elastic.jdee1.Utils;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link io.elastic.api.CredentialsVerifier} used to verfy that credentials provide by user are
 * valid. This is accomplished by sending a simple request to the Petstore API. In case of a
 * successful response (HTTP 200) we assume credentials are valid. Otherweise invalid.
 */
public class CredentialsVerifier implements io.elastic.api.CredentialsVerifier {

  private static final Logger logger = LoggerFactory.getLogger(
      io.elastic.jdee1.CredentialsVerifier.class);

  @Override
  public void verify(final JsonObject configuration) throws InvalidCredentialsException {
    logger.info("About to verify the provided API key by retrieving the user");
    try {


      Utils jdeinstance = new Utils();

      final JsonObjectBuilder result = Json.createObjectBuilder();
      result.add("result", jdeinstance.getTemplate_actionPerformed(configuration));

      logger.info("Emitting data");

      final Message data
          = new Message.Builder().body(result.build()).build();


    } catch (Exception e) {
      throw new InvalidCredentialsException("Failed to verify credentials", e);
    }
  }
}
