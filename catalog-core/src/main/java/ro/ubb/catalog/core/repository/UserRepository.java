package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.User;


public interface UserRepository extends InterfaceRepository<User, Integer> {

    @Query("select u from User u where u.userName=?1")
    User getUserByUserName(String userName);
}
