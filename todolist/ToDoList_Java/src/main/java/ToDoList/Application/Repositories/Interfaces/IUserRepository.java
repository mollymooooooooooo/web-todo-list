package ToDoList.Application.Repositories.Interfaces;

import ToDoList.Domain.Entities.User.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface IUserRepository {
    void createUser(User user);
}
