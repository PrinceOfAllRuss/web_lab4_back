package com.webapp.lab4.login;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("select u from User u where name(u.name) = ?1 and password(u.password) = ?2")
    public List getUser(String name, String password);

    default public boolean isUserExist(String name, String password) {
        List list = getUser(name, password);
        return !list.isEmpty();
    }
}
