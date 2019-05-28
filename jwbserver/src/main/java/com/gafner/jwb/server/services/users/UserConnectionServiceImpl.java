package com.gafner.jwb.server.services.users;

import com.gafner.jwb.api.service.users.UserConnectionService;
import com.gafner.jwb.server.dao.UserRepository;
import com.gafner.jwb.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserConnectionServiceImpl implements UserConnectionService<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean authenticate(String userEmail, String password) {

        User user = this.findByEmail(userEmail);
        if (user == null) {
            return false;
        } else {
            return password.equals(user.getPassword());
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**
     * This method validate the user field patterns and that the email does not already exists in db
     *
     * @param firstName      -
     * @param lastName       -
     * @param emailSignup    -
     * @param passwordSignup -
     * @return True if user was added successfully to db
     */
    @Override
    public boolean saveUserParameters(String firstName, String lastName, String emailSignup, String passwordSignup) {

        if (validate("First Name", firstName, "[a-zA-Z]+") &&
                validate("Last Name", lastName, "[a-zA-Z]+") &&
                validate("Email", emailSignup, "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")) {

            //Check email is not already registered
            if (userRepository.findByEmail(emailSignup) != null) {
                return false;
            } else {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(emailSignup);
                user.setPassword(passwordSignup);
                User save = userRepository.save(user);
                return save != null;

            }
        } else {
            return false;
        }
    }

    /**
     * Basic validation - each client should also validate
     *
     * @param field   -
     * @param value   -
     * @param pattern -
     * @return -
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            return m.find() && m.group().equals(value);
        } else {
            return false;
        }
    }

    @Override
    public String getUserByEmail(String email) {
        User user = findByEmail(email);
        if (user != null) {
            return user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0);
        }
        return null;
    }
}
