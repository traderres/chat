package app1.utilities.chat;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Decode a String into a {@link ChatProtocol}.
 */
public class ProtocolDecoder implements Decoder<String, ChatProtocol>
{
    private static final Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);


    private ObjectMapper mapper = new ObjectMapper();


    @PostConstruct
    public void ProtocolDecoderPostConstruct()
    {
        logger.debug("ProtocolDecoderPostConstruct() started.");
     }

    @Override
    public ChatProtocol decode(String aMesg)
    {
        logger.debug("decode()  aMesg={}", aMesg);

        try {
            return mapper.readValue(aMesg, ChatProtocol.class);
        }
        catch (Exception e)
        {
            logger.error("Error in decode() on aMesg=" + aMesg, e);
            throw new RuntimeException(e);
        }
    }
}