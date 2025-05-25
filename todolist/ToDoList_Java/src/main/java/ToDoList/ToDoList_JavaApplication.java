package ToDoList;

import ToDoList.Domain.Entities.User.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ToDoList_JavaApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ToDoList_JavaApplication.class, args);
    }
}
