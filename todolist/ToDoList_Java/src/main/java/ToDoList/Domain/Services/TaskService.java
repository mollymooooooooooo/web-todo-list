package ToDoList.Domain.Services;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Exceptions.CustomExceptions.NotEnoughAccessException;
import ToDoList.Application.Repositories.ModelsDTO.Enums.TaskSortModel;
import ToDoList.Application.Repositories.ModelsDTO.Enums.UserTaskStatusModel;
import ToDoList.Application.Repositories.ModelsDTO.Task.*;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskShortModelComparators.TaskShortModelAscCreateTimeComparator;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskShortModelComparators.TaskShortModelAscDeadlineComparator;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskShortModelComparators.TaskShortModelDescCreateTimeComparator;
import ToDoList.Application.Repositories.ModelsDTO.Task.TaskShortModelComparators.TaskShortModelDescDeadlineComparator;
import ToDoList.Application.Services.Interfaces.Task.ITaskService;
import ToDoList.Domain.Entities.Task.Task;
import ToDoList.Domain.Entities.User.User;
import ToDoList.Domain.Enums.TaskPriority;
import ToDoList.Domain.Enums.TaskStatus;
import ToDoList.Infrastructure.PostgreDB.Repositories.TaskRepository;
import ToDoList.Infrastructure.PostgreDB.Repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService implements ITaskService {

    private UserRepository _userRepository;
    private TaskRepository _taskRepository;


    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        _taskRepository = taskRepository;
        _userRepository = userRepository;
    }

    public UUID createTask(UUID userId, TaskCreateModel taskCreateModel) throws KeyNotFoundException, BadRequestException {

        Optional<User> userO = _userRepository.findById(userId);

        if(!userO.isPresent()){
            throw new KeyNotFoundException("User is not found");
        }

        TaskPriority taskPriority = taskCreateModel.getPriority();
        LocalDate deadline = taskCreateModel.getDeadline();

        if (taskCreateModel.getPriority() == null){
            String[] titleRef = new String[]{taskCreateModel.getTitle()};
            taskPriority =
                    calculateTaskPriorityByTitleMacros(titleRef, TaskPriority.Medium);

            taskCreateModel.setTitle(titleRef[0]);

        }
        if (taskCreateModel.getDeadline() == null){
            String[] titleRef = new String[]{taskCreateModel.getTitle()};
            deadline =
                    calculateTaskDeadlineByTitleMacros(titleRef);
            taskCreateModel.setTitle(titleRef[0]);
        }

        Task task = new Task(
                UUID.randomUUID(),
                userO.get().getId(),
                taskCreateModel.getTitle(),
                taskCreateModel.getDescription(),
                TaskStatus.Active,
                taskPriority,
                deadline,
                Calendar.getInstance().getTime(),
                null
        );
        _taskRepository.save(task);
        return task.getId();
    }

    private TaskPriority calculateTaskPriorityByTitleMacros(String[] titleRef, TaskPriority defaultPriority) {

        String title = titleRef[0];
        Pattern pattern = Pattern.compile("![1-4]");
        Matcher matcher = pattern.matcher(title);

        TaskPriority taskPriority = defaultPriority;

        if (matcher.find()) {
            String findedResult = title.substring(matcher.start(), matcher.end());
            titleRef[0] = title.replace(findedResult, "");
            switch (findedResult) {
                case "!1":
                    taskPriority = TaskPriority.Low;
                    break;
                case "!2":
                    taskPriority = TaskPriority.Medium;
                    break;
                case "!3":
                    taskPriority = TaskPriority.High;
                    break;
                case "!4":
                    taskPriority = TaskPriority.Critical;
                    break;
            }
        }
        return taskPriority;
    }

    private LocalDate calculateTaskDeadlineByTitleMacros(String[] titleRef) {

        String title = titleRef[0];
        Pattern pattern = Pattern.compile("!before ((0[1-9]|1[0-9]|2[0-9]|3[0-1])[.-](0[1-9]|1[012])[.-][0-9]{4})");
        Matcher matcher = pattern.matcher(title);

        LocalDate deadline = null;

        if (matcher.find()) {
            String findedResult = title.substring(matcher.start(), matcher.end());
            titleRef[0] = title.replace(findedResult, "");

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            String dateGroup = matcher.group(1);
            dateGroup = dateGroup.replace(".", "-");

            deadline = LocalDate.parse(dateGroup, inputFormatter);

        }
        return deadline;
    }

    public void editTask(UUID taskId, UUID userId, EditTaskModel editTaskModel)
            throws KeyNotFoundException, BadRequestException, NotEnoughAccessException {
        Optional<User> userO = _userRepository.findById(userId);
        Optional<Task> taskO = _taskRepository.findById(taskId);

        if(!taskO.isPresent()){
            throw new KeyNotFoundException("Task is not found");
        }
        if(!userO.isPresent()){
            throw new KeyNotFoundException("User is not found");
        }


        Task task = taskO.get();

        TaskPriority taskPriority = editTaskModel.getPriority();
        LocalDate deadline = editTaskModel.getDeadline();

        if (editTaskModel.getPriority() == null){
            String[] titleRef = new String[]{editTaskModel.getTitle()};
            taskPriority =
                    calculateTaskPriorityByTitleMacros(titleRef, TaskPriority.Medium);

            editTaskModel.setTitle(titleRef[0]);

        }
        if (editTaskModel.getDeadline() == null){
            String[] titleRef = new String[]{editTaskModel.getTitle()};
            deadline =
                    calculateTaskDeadlineByTitleMacros(titleRef);
            editTaskModel.setTitle(titleRef[0]);
        }

        if(!task.getUserId().equals(userId)){
            throw new NotEnoughAccessException("You are trying to get not your task");
        }

        task.setTitle(editTaskModel.getTitle());
        task.setDescription(editTaskModel.getDescription());
        task.setDeadline(deadline);
        task.setPriority(taskPriority);
        task.setUpdateTime(Calendar.getInstance().getTime());

        if(editTaskModel.getStatus().equals(TaskStatus.Active) || editTaskModel.getStatus().equals(TaskStatus.Overdue)){

            if (deadline != null && deadline.isBefore(LocalDate.now())){
                task.setStatus(TaskStatus.Overdue);
            }else{
                task.setStatus(TaskStatus.Active);
            }

        }else{

            if (deadline != null && deadline.isBefore(LocalDate.now())){
                task.setStatus(TaskStatus.Late);
            }else {
                task.setStatus(TaskStatus.Completed);
            }

        }

        _taskRepository.save(task);
    }

    public void deleteTask(UUID taskId, UUID userId) throws KeyNotFoundException, NotEnoughAccessException {
        Optional<Task> taskO = _taskRepository.findById(taskId);
        Optional<User> userO = _userRepository.findById(userId);

        if(!taskO.isPresent()){
            throw new KeyNotFoundException("Task is not found");
        }
        if(!userO.isPresent()){
            throw new KeyNotFoundException("User is not found");
        }

        Task task = taskO.get();

        if(!task.getUserId().equals(userId)){
            throw new NotEnoughAccessException("You are trying to get not your task");
        }

        _taskRepository.deleteById(taskId);
    }

    private void changeTaskStatus(Task task){
        TaskStatus status = task.getStatus();
        LocalDate deadline = task.getDeadline();
        
        if(deadline != null && status == TaskStatus.Active && deadline.isBefore(LocalDate.now())){
            task.setStatus(TaskStatus.Overdue);
        }
    }

    public TaskModel getTask(UUID taskId, UUID userId) throws KeyNotFoundException, NotEnoughAccessException {
        Optional<Task> taskO = _taskRepository.findById(taskId);
        Optional<User> userO = _userRepository.findById(userId);

        if(!taskO.isPresent()){
            throw new KeyNotFoundException("Task is not found");
        }
        if(!userO.isPresent()){
            throw new KeyNotFoundException("User is not found");
        }

        Task task = taskO.get();

        if(!task.getUserId().equals(userId)){
            throw new NotEnoughAccessException("You are trying to get not your task");
        }

        changeTaskStatus(task);
        _taskRepository.save(task);

        TaskModel taskModel = new TaskModel(task.getId(),
                task.getTitle(),
                task.getDeadline(),
                task.getPriority(),
                task.getStatus(),
                task.getDescription(),
                task.getCreateTime(),
                task.getUpdateTime()
        );

        return taskModel;
    }

    public TaskShortModelList getUserTasks(UUID userId, TaskSortModel taskSortModel) throws KeyNotFoundException {

        Optional<User> userO = _userRepository.findById(userId);
        if(!userO.isPresent()){
            throw  new KeyNotFoundException("User is not found");
        }

        List<Task> taskList = _taskRepository.findByUserId(userId);
        List<TaskShortModel> taskShortModels = new ArrayList<TaskShortModel>();

        taskList.forEach(task -> {
            taskShortModels.add(new TaskShortModel(task.getId(),
                    task.getTitle(),
                    task.getDeadline(),
                    task.getPriority(),
                    task.getStatus(),
                    task.getCreateTime())
            );
        });

        if (taskSortModel != null){
            Comparator taskShortModelComparator;

            switch (taskSortModel){
                case AscCreationTime:
                    taskShortModelComparator =  new TaskShortModelAscCreateTimeComparator();
                    break;
                case DescCreationTime:
                    taskShortModelComparator =  new TaskShortModelDescCreateTimeComparator();
                    break;
                case DescDeadline:
                    taskShortModelComparator =  new TaskShortModelDescDeadlineComparator();
                    break;
                case AscDeadline:
                    taskShortModelComparator =  new TaskShortModelAscDeadlineComparator();
                    break;
                default:
                    taskShortModelComparator =  new TaskShortModelDescCreateTimeComparator();
            }

            taskShortModels.sort(taskShortModelComparator);
        }

        TaskShortModelList taskShortModelList = new TaskShortModelList(taskShortModels);

        return taskShortModelList;
    }
}
