package ce.pucmm.edu.vaadin.Data;

import ce.pucmm.edu.vaadin.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select user from User user where user.email = :email and user.password = :contrasena")
    User findByEmailAndPassword(@Param("email") String email, @Param("contrasena") String contrasena);

    @Query("select count(user) from User user")
    Integer contar();

    @Query(value = "SELECT * FROM user m offset(?1) limit(?2)", nativeQuery = true)
    List<User> paginate(int offset, int limit);
}