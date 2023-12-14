package com.project.model.service.user;

import com.project.model.dao.user.IUserDAO;
import com.project.model.dto.user.UserRegisterDTO;
import com.project.model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceIMPL implements IUserService {
    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public List<User> searchUser(String email) {
        return userDAO.searchUser(email);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public boolean register(UserRegisterDTO user) {
        User user1 = new User();
        user1.setUserName(user.getUserName());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user1.setPassword(hashPassword);
        user1.setAvatar("http://localhost:8080/uploads/avatars/default-avatar.jpg");
        return userDAO.register(user1);
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public void block(Integer id) {
        userDAO.block(id);
    }

    @Override
    public boolean update(User user) {
        return userDAO.update(user);
    }
}
