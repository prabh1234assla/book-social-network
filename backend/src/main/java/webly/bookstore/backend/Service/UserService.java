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
import webly.bookstore.backend.Models.UserModel;
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
                            .build();
        
        return repository.save(userToSave);
    }

    public User findById(long id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll(){
        return repository.findAll().stream().sorted(Comparator.comparing(User::getId)).toList();
    }

    public void updateOne(long id, UserModel user){
        if(repository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        User userToUpdate = repository.getReferenceById(id);
        
        userToUpdate.setRole(user.getRole());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());

        repository.save(userToUpdate);
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
