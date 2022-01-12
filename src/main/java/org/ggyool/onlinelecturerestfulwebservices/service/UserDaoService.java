package org.ggyool.onlinelecturerestfulwebservices.service;

import org.ggyool.onlinelecturerestfulwebservices.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static final List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "aaa", new Date(), "pass1", "111111-1111111"));
        users.add(new User(2, "bbb", new Date(), "pass2", "222222-2222222"));
        users.add(new User(3, "ccc", new Date(), "pass3", "333333-3333333"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User update(int id, User user) {
        for (User storedUser : users) {
            if (storedUser.getId() == id) {
                storedUser.setName(user.getName());
                return storedUser;
            }
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
