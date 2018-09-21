package io.elastic.jdee1.actions;

import io.elastic.api.ExecutionParameters;
import io.elastic.api.Message;
import io.elastic.api.Module;
import io.elastic.jdee1.Utils;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Action to create a pet.
 */
public class executeFunction implements Module {
    private static final Logger logger = LoggerFactory.getLogger(executeFunction.class);

    /**
     * Executes the actions's logic by sending a request to the Petstore API and emitting response to the platform.
     *
     * @param parameters execution parameters
     */
    @Override
    public void execute(final ExecutionParameters parameters) {
        logger.info("About to get functions list");
        // incoming message
        final Message message = parameters.getMessage();

        // body contains the mapped data
        final JsonObject body = message.getBody();

        // contains action's configuration
        final JsonObject configuration = parameters.getConfiguration();

        Utils jdeinstance = new Utils();

        final JsonObjectBuilder result = Json.createObjectBuilder();

        try {
            jdeinstance.jbExecute_actionPerformed(configuration, body);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        jdeinstance.getTemplate_actionPerformed(configuration);

        try {
            result.add("jdeResponse", jdeinstance.jbExecute_actionPerformed(configuration, body));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        logger.info("Emitting data");

        final Message data
            = new Message.Builder().body(result.build()).build();

        // emitting the message to the platform
        parameters.getEventEmitter().emitData(data);
    }
}
