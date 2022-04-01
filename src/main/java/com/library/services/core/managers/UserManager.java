package com.library.services.core.managers;

import com.library.services.db.dto.User;

public interface UserManager {
    Object viewUser(Integer user_id);
    int addUser(User user);
}
