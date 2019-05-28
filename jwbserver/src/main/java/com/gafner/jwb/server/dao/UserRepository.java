package com.gafner.jwb.server.dao;

import com.gafner.jwb.api.dao.UserRepositoryAPI;
import com.gafner.jwb.api.model.UserAPI;
import com.gafner.jwb.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryAPI<User> {

    User findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByEmail(String email);
}
