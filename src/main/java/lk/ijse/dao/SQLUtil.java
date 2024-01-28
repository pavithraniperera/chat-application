package lk.ijse.dao;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    public static  <T>T execute(String sql,Object...ob) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i=0;i<ob.length;i++){
            preparedStatement.setObject(i+1,ob[i]);
        }
        if (sql.startsWith("SELECT" )||sql.startsWith("WITH")){
            return (T) preparedStatement.executeQuery();
        }else {
            return (T)(Boolean) (preparedStatement.executeUpdate()>0);
        }
    }
}
