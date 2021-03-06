package com.rocketchat.core.factory;

import com.rocketchat.common.data.model.BaseRoom;
import com.rocketchat.core.ChatRoom;
import com.rocketchat.core.RocketChatClient;
import com.rocketchat.core.model.Subscription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * Created by sachin on 29/7/17.
 */

// TODO: 29/7/17 add methods for getting rooms based on favourites, one to one and groups (public and private)
// TODO: 29/7/17 might have to make separate arraylist for each type, seems little impossible, better to keep generic
public class ChatRoomFactory {

    private RocketChatClient client;
    private List<ChatRoom> rooms;

    public static final String FAVORITE = "f";
    public static final String DIRECT = "d";
    public static final String PUBLIC = "c";
    public static final String PRIVATE = "p";

    public ChatRoomFactory(RocketChatClient client) {
        this.client = client;
        rooms = new ArrayList<>();
    }

    private ChatRoom createChatRoom(BaseRoom room) {
        return new ChatRoom(client, room);
    }

    public ChatRoomFactory createChatRooms(List<? extends BaseRoom> roomObjects) {
        removeAllChatRooms();
        for (BaseRoom room : roomObjects) {
            rooms.add(createChatRoom(room));
        }
        return this;
    }

    public ChatRoomFactory addChatRoom(BaseRoom room) {
        if (getChatRoomByName(room.name()) == null) {
            ChatRoom newRoom = createChatRoom(room);
            rooms.add(newRoom);
        }
        return this;
    }

    public List<ChatRoom> getChatRooms() {
        return rooms;
    }


    public List<ChatRoom> getPrivateGroups() {
        List<ChatRoom> groups = new ArrayList<>();
        for (ChatRoom room : rooms) {
            if (room.getRoomData().type() == BaseRoom.RoomType.PRIVATE) {
                groups.add(room);
            }
        }
        return groups;
    }

    public List<ChatRoom> getPublicGroups() {
        List<ChatRoom> groups = new ArrayList<>();
        for (ChatRoom room : rooms) {
            if (room.getRoomData().type() == BaseRoom.RoomType.PUBLIC) {
                groups.add(room);
            }
        }
        return groups;
    }

    public List<ChatRoom> getDirectRooms() {
        List<ChatRoom> directRooms = new ArrayList<>();
        for (ChatRoom room : rooms) {
            if (room.getRoomData().type() == BaseRoom.RoomType.ONE_TO_ONE) {
                directRooms.add(room);
            }
        }
        return directRooms;
    }

    public List<ChatRoom> getFavoriteRooms() {
        List<ChatRoom> favorites = new ArrayList<>();
        for (ChatRoom room : rooms) {
            BaseRoom roomObject = room.getRoomData();
            if (roomObject instanceof Subscription) {
                if (((Subscription) roomObject).favourite()) {
                    favorites.add(room);
                }
            }
        }
        return favorites;
    }

    private List<ChatRoom> removeFavorite(List<ChatRoom> rooms) {

        ListIterator<ChatRoom> roomListIterator = rooms.listIterator();
        while (roomListIterator.hasNext()) {
            BaseRoom roomObject = roomListIterator.next().getRoomData();
            if (roomObject instanceof Subscription) {
                if (((Subscription) roomObject).favourite()) {
                    roomListIterator.remove();
                }
            }
        }
        return rooms;
    }

    /**
     * This has four types of rooms
     * Favorite room can have all types of rooms, other rooms do not contains favorites
     *
     * @return returns sorted rooms in the form of hashmap with keys
     * 1. ChatRoomFactory.FAVORITE
     * 2. ChatRoomFactory.DIRECT
     * 3. ChatRoomFactory.PUBLIC
     * 4. ChatRoomFactory.PRIVATE
     */

    public Map<String, List<ChatRoom>> getSortedRooms() {
        Map<String, List<ChatRoom>> rooms = new HashMap<>();
        rooms.put(FAVORITE, getFavoriteRooms());
        rooms.put(DIRECT, removeFavorite(getDirectRooms()));
        rooms.put(PUBLIC, removeFavorite(getPublicGroups()));
        rooms.put(PRIVATE, removeFavorite(getPrivateGroups()));
        return rooms;
    }

    public ChatRoom getChatRoomByName(String roomName) {
        for (ChatRoom room : rooms) {
            if (room.getRoomData().name().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public ChatRoom getChatRoomById(String roomId) {
        for (ChatRoom room : rooms) {
            if (room.getRoomData().roomId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Boolean removeChatRoomByName(String roomName) {
        for (ChatRoom room : rooms) {
            if (room.getRoomData().name().equals(roomName)) {
                return rooms.remove(room);
            }
        }
        return false;
    }

    public Boolean removeChatRoomById(String roomId) {
        for (ChatRoom room : rooms) {
            if (room.getRoomData().roomId().equals(roomId)) {
                return rooms.remove(room);
            }
        }
        return false;
    }

    public Boolean removeChatRoom(ChatRoom room) {
        return rooms.remove(room);
    }

    public void removeAllChatRooms() {
        rooms.clear();
    }

}
