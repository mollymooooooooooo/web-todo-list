package ToDoList.Application.Exceptions.CustomExceptions;

public class KeyNotFoundException extends Exception{
    public KeyNotFoundException(String message) {
        super(message);
    }
}
