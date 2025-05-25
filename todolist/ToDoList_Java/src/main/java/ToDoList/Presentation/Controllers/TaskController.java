package ToDoList.Presentation.Controllers;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Exceptions.CustomExceptions.NotEnoughAccessException;
import ToDoList.Application.Repositories.ModelsDTO.Enums.TaskSortModel;
import ToDoList.Application.Repositories.ModelsDTO.Enums.UserTaskStatusModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.EditTaskModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskCreateModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskShortModelList;
import ToDoList.Application.Services.Interfaces.Task.ITaskService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private ITaskService _taskService;

    public TaskController(ITaskService taskService) {
        _taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<UUID> CreateTask(@RequestBody TaskCreateModel taskCreateModel)
            throws BadRequestException, KeyNotFoundException {

        UUID userId  = GetUserIdFromSecurityContext();

        return ResponseEntity.ok(_taskService.createTask(userId,taskCreateModel));

    }

    @PutMapping("{taskId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<?> EditTask(@RequestBody EditTaskModel editTaskModel, @PathVariable("taskId") UUID taskId)
            throws BadRequestException, KeyNotFoundException, NotEnoughAccessException {

        UUID userId  = GetUserIdFromSecurityContext();

        _taskService.editTask(taskId, userId, editTaskModel);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{taskId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<?> DeleteTask(@PathVariable("taskId") UUID taskId)
            throws BadRequestException, KeyNotFoundException, NotEnoughAccessException {

        UUID userId  = GetUserIdFromSecurityContext();

        _taskService.deleteTask(taskId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{taskId}")
    public ResponseEntity<TaskModel> GetTask(@PathVariable("taskId") UUID taskId)
            throws BadRequestException, KeyNotFoundException, NotEnoughAccessException {

        UUID userId  = GetUserIdFromSecurityContext();

        return ResponseEntity.ok(_taskService.getTask(taskId, userId));

    }

    @GetMapping
    public ResponseEntity<TaskShortModelList> GetUserTasks(@RequestParam(name = "taskSort", required = false) TaskSortModel taskSortModel) throws BadRequestException, KeyNotFoundException {

        UUID userId  = GetUserIdFromSecurityContext();

        return ResponseEntity.ok(_taskService.getUserTasks(userId, taskSortModel));

    }
    private UUID GetUserIdFromSecurityContext(){
        return  (UUID.fromString((String)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()));
    }
}
