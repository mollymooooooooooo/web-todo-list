package ToDoList.Domain.Entities.Task;

import ToDoList.Domain.Entities.User.User;
import ToDoList.Domain.Enums.TaskPriority;
import ToDoList.Domain.Enums.TaskStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "task")
public class Task {

    public Task(){

    }

    public Task(UUID id, UUID userId, String title, String description, TaskStatus status,
                TaskPriority priority,  LocalDate deadline, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private UUID id;

    //@ManyToOne()
    //@NotNull
    //private User user;

    @NotNull
    private UUID userId;

    @NotNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", length = 1024)
    private String description;

    @NotNull
    @Column(name = "status", nullable = false,columnDefinition = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority", columnDefinition = "task_priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "deadline")
    private LocalDate deadline;

    @NotNull
    @Column(name = "createTime", nullable = false)
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID user) {
        this.userId = user;
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
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
