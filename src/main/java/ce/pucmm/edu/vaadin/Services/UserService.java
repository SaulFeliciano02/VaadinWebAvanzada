package ce.pucmm.edu.vaadin.Services;

import ce.pucmm.edu.vaadin.Data.UserRepository;
import ce.pucmm.edu.vaadin.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository ur;

    public User createUser(Integer id, String name, String email, String password) throws Exception {
        try {
            return ur.save(new User(id, name, email, password));
        } catch (PersistenceException exp) {
            throw new PersistenceException("Hubo un error al crear un nuevo usuario.");
        } catch (NullPointerException exp) {
            throw new NullPointerException("Hubo un error de datos nulos al querer crear el usuario");
        } catch (Exception exp) {
            throw new Exception("Hubo un error general al querer crear el usuario");
        }
    }

    public void editUser(User user) throws Exception {
        try {
            ur.save(user);
        } catch (PersistenceException e) {
            throw new PersistenceException("Hubo un error al editar el usuario.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Al editar el usuario hubo un error de datos nulos.");
        } catch (Exception e) {
            throw new Exception("Hubo un error general al editar un usuario.");
        }
    }

    public boolean validate(String email, String password) {
        User user = ur.findByEmailAndPassword(email, password);
        return (user != null);
    }

    public List<User> listUsers() {
        return ur.findAll();
    }


    public List<User> listUsersWithPagination(int offset, int limit){
        return ur.paginate(offset, limit);
    }


    @Transactional
    public Integer totalUsers() {
        return ur.contar()+1;
    }

    @Transactional
    public void removeUser(Integer usuarioID){
        ur.deleteById(usuarioID);
    }

    @Transactional
    public User findUser(Integer usuarioID){
        return ur.getOne(usuarioID);
    }

}


