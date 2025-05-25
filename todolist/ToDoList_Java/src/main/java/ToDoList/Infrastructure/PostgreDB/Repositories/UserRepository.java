package ToDoList.Infrastructure.PostgreDB.Repositories;

import ToDoList.Application.Repositories.Interfaces.IUserRepository;
import ToDoList.Domain.Entities.Task.Task;
import ToDoList.Domain.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

}
