package ToDoList.Application.Repositories.ModelsDTO.Task;

import ToDoList.Domain.Entities.User.User;
import ToDoList.Domain.Enums.TaskPriority;
import ToDoList.Domain.Enums.TaskStatus;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class TaskModel {
    public TaskModel(){

    }

    public TaskModel(UUID id, String title, LocalDate deadline, TaskPriority priority, TaskStatus status,
                     String description, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    private UUID id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate deadline;

    private Date createTime;

    private Date updateTime;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
