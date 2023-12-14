package com.project.model.dao.user;

import com.project.model.entity.Category;
import com.project.model.entity.User;
import com.project.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoIMPL implements IUserDAO {
    @Override
    public List<User> findAll() {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_USER}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setFullName(resultSet.getString("fullname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setStatus(resultSet.getBoolean("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return users;
    }

    @Override
    public List<User> searchUser(String email) {
        Connection connection = null;
        List<User> foundList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SEARCH_USER(?)}");
            String emailSearch = email.toLowerCase().trim();
            callableStatement.setString(1, emailSearch);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setFullName(resultSet.getString("fullname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setStatus(resultSet.getBoolean("status"));
                foundList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return foundList;
    }

    @Override
    public User findByEmail(String email) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        User user = new User();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_USER_BY_EMAIL(?)}");
            callableStatement.setString(1, email);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setFullName(resultSet.getString("fullname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean register(User user) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ADD_USER(?,?,?,?)}");
            callableStatement.setString(1, user.getUserName());
            callableStatement.setString(2, user.getEmail());
            callableStatement.setString(3, user.getPassword());
            callableStatement.setString(4, user.getPhone());
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public void block(Integer id) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("{CALL PROC_BLOCK_USER(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public boolean update(User user) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_USER(?,?,?,?,?,?)}");
            callableStatement.setInt(1, user.getUserId());
            callableStatement.setString(2, user.getUserName());
            callableStatement.setString(3, user.getFullName());
            callableStatement.setString(4, user.getAvatar());
            callableStatement.setString(5, user.getAddress());
            callableStatement.setString(6, user.getPhone());
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }
}
