package app1.utilities.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Encode a {@link ChatProtocol} into a String
 */
public class JacksonEncoder implements Encoder<JacksonEncoder.Encodable, String>
{
    private static final Logger logger = LoggerFactory.getLogger(JacksonEncoder.class);

    private ObjectMapper mapper = new ObjectMapper();

    public JacksonEncoder()
    {
        logger.debug("JacksonEncoder() started.");
    }

    @PostConstruct
    public void JacksonEncoderPostConstruct()
    {
        logger.debug("JacksonEncoderPostConstruct() started.");
     }

    @Override
    public String encode(Encodable m)
    {
        logger.debug("encode()  m={}", m.toString());

        try {
            return mapper.writeValueAsString(m);
        }
        catch (Exception e)
        {
            logger.error("Error in encode()", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Marker interface for Jackson.
     */
    public static interface Encodable {
    }
}