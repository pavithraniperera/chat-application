package lk.ijse.bo;

import lk.ijse.dto.MessageDto;
import lk.ijse.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface ChatBo {
     boolean saveUserData(UserDto dto) throws SQLException;

    boolean checkTheUserName(UserDto dto) throws SQLException;

    List<MessageDto> getAllChat(String clientName) throws  SQLException;

    void saveMessage(String msgToSend, String clientName) throws SQLException;
}
