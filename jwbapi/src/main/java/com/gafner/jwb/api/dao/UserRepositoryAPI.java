package com.gafner.jwb.api.dao;

import com.gafner.jwb.api.model.UserAPI;


import java.util.List;

public interface UserRepositoryAPI<T extends UserAPI> {

    T findByFirstName(String firstName);

    List<T> findByLastName(String lastName);

    T findByEmail(String email);

}
