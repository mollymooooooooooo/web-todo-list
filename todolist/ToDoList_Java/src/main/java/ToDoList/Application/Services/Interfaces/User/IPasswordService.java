package ToDoList.Application.Services.Interfaces.User;

public interface IPasswordService {
    String CreateHashFromPassword(String password);

    Boolean IsPasswordEqualToHash(String password, String passwordHash);
}
