package lk.ijse.dao;

import lk.ijse.entity.MessageEntity;
import lk.ijse.entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface ChatDao {
    boolean saveUserData(UserEntity entity) throws SQLException;

    List<UserEntity> getAllUsers() throws SQLException;

    List<MessageEntity> getAllMessages(String name) throws SQLException;

    boolean saveMessage(String msgToSend, String clientName) throws SQLException;
}
