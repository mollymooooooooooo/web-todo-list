package ToDoList.Application.Services.Interfaces.User;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Repositories.ModelsDTO.Token.TokenResponseModel;
import ToDoList.Application.Repositories.ModelsDTO.User.UserCreateModel;
import ToDoList.Application.Repositories.ModelsDTO.User.UserLoginDataModel;
import ToDoList.Domain.Entities.User.User;

import java.util.UUID;

public interface IUserService {
    TokenResponseModel createUser(UserCreateModel userModel);
    TokenResponseModel authorizeUser(UserLoginDataModel userLoginDataModel) throws KeyNotFoundException;
    User getUserById(UUID userId) throws KeyNotFoundException;
    User getUserByEmail(String email) throws KeyNotFoundException;
}
