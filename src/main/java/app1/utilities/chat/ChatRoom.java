package app1.utilities.chat;

import org.atmosphere.config.service.*;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.MetaBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple annotated class that demonstrate the power of Atmosphere. This class supports all transports, support
 * message length guarantee, heart beat, message cache thanks to the {@link ManagedService}.
 */
@Singleton
@ManagedService(path = "/chat/{room: [a-zA-Z][a-zA-Z_0-9]*}")
public class ChatRoom
{
    private static final Logger logger = LoggerFactory.getLogger(ChatRoom.class);

    private final ConcurrentHashMap<String, String> users = new ConcurrentHashMap<String, String>();

    private final static String CHAT = "/chat/";

    @PathParam("room")
    private String chatroomName;

    @Inject
    private BroadcasterFactory factory;

    @Inject
    private AtmosphereResourceFactory resourceFactory;

    @Inject
    private MetaBroadcaster metaBroadcaster;

    public ChatRoom()
    {
        logger.debug("ChatRoom() started.");
    }

    @PostConstruct
    public void ChatroomPostConstruct()
    {
        logger.debug("ChatroomPostConstruct() started");
    }

    /**
     * Invoked when the connection as been fully established and suspended, e.g ready for receiving messages.
     *
     * @param r
     */
    @Ready(encoders = {JacksonEncoder.class})
    @DeliverTo(DeliverTo.DELIVER_TO.ALL)
    public ChatProtocol onReady(final AtmosphereResource r) {
        logger.debug("onReady() started.  Browser {} has connected.", r.uuid());
        return new ChatProtocol(users.keySet(), getRooms(factory.lookupAll()));
    }

    private static Collection<String> getRooms(Collection<Broadcaster> broadcasters)
    {
        logger.debug("getRooms() started");

        Collection<String> result = new ArrayList<String>();
        for (Broadcaster broadcaster : broadcasters)
        {
            if (!("/*".equals(broadcaster.getID())))
            {
                // if no room is specified, use ''
                String[] p = broadcaster.getID().split("/");
                result.add(p.length > 2 ? p[2] : "");
            }
        };
        return result;
    }

    /**
     * Invoked when the client disconnect or when an unexpected closing of the underlying connection happens.
     *
     * @param event
     */
    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event)
    {
        logger.debug("onDisconnect() started.");

        if (event.isCancelled())
        {
            // We didn't get notified, so we remove the user.
            users.values().remove(event.getResource().uuid());
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        }
        else if (event.isClosedByClient())
        {
            logger.debug("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    /**
     * Simple annotated class that demonstrate how {@link org.atmosphere.config.managed.Encoder} and {@link org.atmosphere.config.managed.Decoder
     * can be used.
     *
     * @param message an instance of {@link ChatProtocol }
     * @return
     * @throws IOException
     */
    @Message(encoders = {JacksonEncoder.class}, decoders = {ProtocolDecoder.class})
    public ChatProtocol onMessage(ChatProtocol message) throws Exception
    {
        logger.debug("onMessage()  message={}", message.toString());

        if (message.getMessage().contains("disconnecting"))
        {
            users.remove(message.getAuthor());
            return new ChatProtocol(message.getAuthor(), " disconnected from room " + chatroomName, users.keySet(), getRooms(factory.lookupAll()));
        }

        if (!users.containsKey(message.getAuthor()))
        {
            users.put(message.getAuthor(), message.getUuid());
            return new ChatProtocol(message.getAuthor(), " entered room " + chatroomName, users.keySet(), getRooms(factory.lookupAll()));
        }

        message.setUsers(users.keySet());
        logger.debug("{} just send {}", message.getAuthor(), message.getMessage());
        return new ChatProtocol(message.getAuthor(), message.getMessage(), users.keySet(), getRooms(factory.lookupAll()));
    }


    @Message(decoders = {UserDecoder.class})
    public void onPrivateMessage(UserMessage user) throws IOException
    {
        logger.debug("onPrivateMessage() started user={}", user.toString());

        String userUUID = users.get(user.getUser());
        if (userUUID != null)
        {
            // Retrieve the original AtmosphereResource
            AtmosphereResource r = resourceFactory.find(userUUID);

            if (r != null)
            {
                ChatProtocol m = new ChatProtocol(user.getUser(), " sent you a private message: " + user.getMessage().split(":")[1], users.keySet(), getRooms(factory.lookupAll()));
                if (!user.getUser().equalsIgnoreCase("all"))
                {
                    Broadcaster destBroadcaster = factory.lookup(CHAT + chatroomName);

                    // Send the message to the private user
                    destBroadcaster.broadcast(m, r);
                }
            }
        }
        else
        {
            ChatProtocol m = new ChatProtocol(user.getUser(), " sent a message to all chatroom: " + user.getMessage().split(":")[1], users.keySet(), getRooms(factory.lookupAll()));
            metaBroadcaster.broadcastTo("/*", m);
        }
    }

}