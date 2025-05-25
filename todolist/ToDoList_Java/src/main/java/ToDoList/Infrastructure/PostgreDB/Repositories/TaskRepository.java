package ToDoList.Infrastructure.PostgreDB.Repositories;

import ToDoList.Domain.Entities.Task.Task;
import ToDoList.Domain.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {


    List<Task> findByUserId(UUID userId);
}
