package webly.bookstore.backend.Service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.persistence.EntityNotFoundException;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.BaseModel.UserModel;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User create(UserModel user){
        User userToSave = User.builder()
                            .role(user.getRole())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .coursesTaught(null)
                            .enrollments(null)
                            .fees(null)
                            .marks(null)
                            .build();

        return repository.save(userToSave);
    }

    public User findById(long id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll(){
        return repository.findAll().stream().sorted(Comparator.comparing(User::getId)).toList();
    }

    public void updateCoursesTaughtById(long id, UserModel user){
        if(repository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        repository.updateCoursesTaughtById(id, user);
    }

    public void updateEnrollmentsById(long id, UserModel user){
        if(repository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        repository.updateEnrollmentsById(id, user);
    }

    public void updateFeesById(long id, UserModel user){
        if(repository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        repository.updateFeesById(id, user);
    }

    public void updateMarksById(long id, UserModel user){
        if(repository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        repository.updateMarksById(id, user);
    }

    public User patchOne(long id, JsonPatch patch){
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        User userPatched = applyPatchToUser(patch, user);

        return repository.save(userPatched);
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    private User applyPatchToUser(JsonPatch patch, User user){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
            return objectMapper.treeToValue(patched, User.class);
        }catch( JsonPatchException | JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
