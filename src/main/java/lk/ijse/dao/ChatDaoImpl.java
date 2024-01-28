package lk.ijse.dao;

import lk.ijse.entity.MessageEntity;
import lk.ijse.entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatDaoImpl implements ChatDao {
    public boolean saveUserData(UserEntity entity) throws SQLException {
        String sql = "INSERT INTO users (username)VALUES (?)";
        return SQLUtil.execute(sql, entity.getUsername());

    }

    @Override
    public List<UserEntity> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        ResultSet resultSet = SQLUtil.execute(sql);
        List<UserEntity> entities = new ArrayList<>();
        while (resultSet.next()){
            entities.add(new UserEntity(resultSet.getString(2)));
        }
        return entities;
    }

    @Override
    public List<MessageEntity> getAllMessages(String name) throws SQLException {
        String sql = "SELECT * FROM chat_history WHERE sender =? OR receiver =?";
        ResultSet resultSet = SQLUtil.execute(sql,name,name);
        List<MessageEntity> entities = new ArrayList<>();
        while (resultSet.next()){
            entities.add(new MessageEntity(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTime(5)));
        }
        return entities;

    }

    @Override
    public boolean saveMessage(String msgToSend, String clientName) throws SQLException {
       String sql = "INSERT INTO chat_history (sender,message,timestamp)VALUES (?,?,?)";
        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
       return SQLUtil.execute(sql,clientName,msgToSend, currentTimestamp);
    }
}
