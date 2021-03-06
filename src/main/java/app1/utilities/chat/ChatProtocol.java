package app1.utilities.chat;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChatProtocol implements JacksonEncoder.Encodable
{

    private String message = "";
    private String author = "";
    private long time = System.currentTimeMillis();
    private List<String> users = new ArrayList<String>();
    private List<String> rooms = new ArrayList<String>();
    private String uuid = UUID.randomUUID().toString();

    public ChatProtocol() {
        this("", "");
    }

    public ChatProtocol(String author, String message) {
        this.author = author;
        this.message = message;
        this.time = new Date().getTime();
    }

    public ChatProtocol(String author, String message, Collection<String> users, Collection<String> rooms) {
        this(author, message);
        this.users.addAll(users);
        this.rooms.addAll(rooms);
    }

    public ChatProtocol(Collection<String> users, Collection<String> rooms) {
        this.users.addAll(users);
        this.rooms.addAll(rooms);
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(Collection<String> users)
    {
        this.users.addAll(users);
    }

//    public void setUsers(List<String> users)
//    {
//        this.users = users;
//    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

}