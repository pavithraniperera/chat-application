package lk.ijse.bo;

import lk.ijse.dao.ChatDao;
import lk.ijse.dao.ChatDaoImpl;
import lk.ijse.dto.MessageDto;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.MessageEntity;
import lk.ijse.entity.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoImpl implements ChatBo {
    private ChatDao chatDao = new ChatDaoImpl();
    public boolean saveUserData(UserDto dto) throws SQLException {

        return chatDao.saveUserData(new UserEntity(dto.getUsername()));

    }

    @Override
    public boolean checkTheUserName(UserDto dto) throws SQLException {
        List<UserEntity> entities = chatDao.getAllUsers();
         for (UserEntity entity : entities){
             if (entity.getUsername().equals(dto.getUsername())){
                 return false;
             }
         }
        return true;
    }

    @Override
    public List<MessageDto> getAllChat(String clientName) throws SQLException {
          List<MessageDto> dtoList = new ArrayList<>();
          List<MessageEntity> entities = chatDao.getAllMessages(clientName);
          for (MessageEntity entity : entities){
              dtoList.add(new MessageDto(entity.getSenderName(),entity.getReciverName(),entity.getMessage(),entity.getTime()));
          }
          return dtoList;

    }

    @Override
    public void saveMessage(String msgToSend, String clientName) throws SQLException {
       chatDao.saveMessage(msgToSend,clientName);
    }
}
