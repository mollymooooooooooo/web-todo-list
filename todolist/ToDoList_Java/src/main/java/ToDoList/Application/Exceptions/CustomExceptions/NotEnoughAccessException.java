package ToDoList.Application.Exceptions.CustomExceptions;

public class NotEnoughAccessException extends Exception{
    public NotEnoughAccessException(String message) {
        super(message);
    }
}
