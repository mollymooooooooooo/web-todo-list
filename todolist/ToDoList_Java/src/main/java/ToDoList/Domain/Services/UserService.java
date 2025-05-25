package ToDoList.Domain.Services;

import ToDoList.Application.Exceptions.CustomExceptions.KeyNotFoundException;
import ToDoList.Application.Repositories.ModelsDTO.Token.TokenResponseModel;
import ToDoList.Application.Repositories.ModelsDTO.User.UserLoginDataModel;
import ToDoList.Application.Services.Interfaces.User.IUserService;
import ToDoList.Application.Repositories.ModelsDTO.User.UserCreateModel;
import ToDoList.Domain.Entities.User.User;
import ToDoList.Infrastructure.PostgreDB.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private UserRepository _userRepository;
    private JwtService _jwtService;
    private PasswordEncoder _passwordEncoder;

    public UserService(UserRepository userRepository, JwtService jwtService,PasswordEncoder passwordEncoder) {
        _userRepository = userRepository;
        _jwtService = jwtService;
        _passwordEncoder = passwordEncoder;

    }
    @Override
    public TokenResponseModel createUser(UserCreateModel userCreateModel){
        String encodedPassword = _passwordEncoder.encode(userCreateModel.getPassword());

        User user = new User(UUID.randomUUID(),
                userCreateModel.getEmail(),
                encodedPassword);

        _userRepository.save(user);

        var jwt = _jwtService.generateToken(user.getId());

        TokenResponseModel tokenResponseModel = new TokenResponseModel(jwt);

        return tokenResponseModel;
    }
    @Override
    public User getUserByEmail(String email) throws KeyNotFoundException {
        User user = _userRepository.findByEmail(email);

        if(user==null){
            throw  new KeyNotFoundException("User is not found");
        }

        return user;
    }
    @Override
    public User getUserById(UUID userId) throws KeyNotFoundException {
        Optional<User> user = _userRepository.findById(userId);

        if(user.isEmpty()){
            throw  new KeyNotFoundException("User is not found");
        }

        return user.get();
    }
    @Override
    public TokenResponseModel authorizeUser(UserLoginDataModel userLoginDataModel) throws KeyNotFoundException {

        User user = getUserByEmail(userLoginDataModel.getEmail());

        if (!_passwordEncoder.matches(userLoginDataModel.getPassword(), user.getPassword())){
            throw  new KeyNotFoundException("Password is not correct");
        }

        var jwt = _jwtService.generateToken(user.getId());

        TokenResponseModel tokenResponseModel = new TokenResponseModel(jwt);

        return tokenResponseModel;
    }
}
