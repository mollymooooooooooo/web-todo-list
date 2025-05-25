package ToDoList.Application.Services.Interfaces.Task;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Exceptions.CustomExceptions.NotEnoughAccessException;
import ToDoList.Application.Repositories.ModelsDTO.Enums.TaskSortModel;
import ToDoList.Application.Repositories.ModelsDTO.Enums.UserTaskStatusModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskCreateModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.EditTaskModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskShortModelList;
import org.apache.coyote.BadRequestException;

import java.util.UUID;

public interface ITaskService {
    UUID createTask(UUID userId, TaskCreateModel taskCreateModel) throws KeyNotFoundException, BadRequestException;

    void editTask(UUID taskId, UUID userId, EditTaskModel editTaskModel) throws KeyNotFoundException, BadRequestException, NotEnoughAccessException;

    void deleteTask(UUID taskId, UUID userId) throws KeyNotFoundException, NotEnoughAccessException;

    TaskShortModelList getUserTasks(UUID userId, TaskSortModel taskSortModel) throws KeyNotFoundException;

    TaskModel getTask(UUID taskId, UUID userId) throws KeyNotFoundException, NotEnoughAccessException;
}
