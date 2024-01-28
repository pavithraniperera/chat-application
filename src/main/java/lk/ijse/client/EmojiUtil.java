package lk.ijse.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmojiUtil {

    public static ObservableList<String> getEmojiList() {
        // Add your emoji characters to the list
        return FXCollections.observableArrayList("😊","😍", "\uD83D\uDE02","\uD83C\uDF39",
                "\uD83D\uDE00", // Grinning Face
                "\uD83D\uDE0D", // Smiling Face with Heart-Eyes
                "\uD83D\uDE02", // Face with Tears of Joy
                "\uD83D\uDC4D", // Thumbs Up
                "\uD83D\uDC4E", // Thumbs Down
                "\uD83D\uDC4F", // Clapping Hands
                "\uD83D\uDE09", // Winking Face
                "\uD83D\uDE05", // Face with Rolling Eyes
                "\uD83D\uDE0E", // Smiling Face with Sunglasses
                "\u2764\uFE0F"   // Heart
                 );
    }
}

