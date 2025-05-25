package ToDoList.Presentation.Controllers;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Repositories.ModelsDTO.Token.TokenResponseModel;
import ToDoList.Application.Repositories.ModelsDTO.User.UserCreateModel;
import ToDoList.Application.Repositories.ModelsDTO.User.UserLoginDataModel;
import ToDoList.Domain.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @PostMapping("auth")
    public ResponseEntity<TokenResponseModel> AuthorizeUser(@RequestBody UserLoginDataModel userLoginDataModel) throws KeyNotFoundException {

        return ResponseEntity.ok(_userService.authorizeUser(userLoginDataModel));
    }

    @PostMapping
    public ResponseEntity<TokenResponseModel> CreateUser(@RequestBody UserCreateModel userCreateModel){

        return ResponseEntity.ok(_userService.createUser(userCreateModel));

    }
}
