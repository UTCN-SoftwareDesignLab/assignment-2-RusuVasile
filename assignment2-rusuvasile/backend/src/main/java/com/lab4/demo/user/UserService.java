package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private User  findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public  UserDTO create(UserDTO userDTO){
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.CUSTOMER).orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
        roles.add(defaultRole);
        User user=User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return userDTO;
    }

    public  UserDTO edit (UserDTO userDTO){
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.CUSTOMER).orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
        roles.add(defaultRole);
        User user=User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return userDTO;
    }

    public void delete(Long id){
        userRepository.delete(userRepository.findById(id).get());
    }
}
