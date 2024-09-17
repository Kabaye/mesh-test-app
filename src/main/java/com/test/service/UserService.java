package com.test.service;

import com.test.entity.User;
import com.test.model.SearchUserRequest;
import com.test.model.UpdateUserRequest;
import com.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User userToPatch = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (CollectionUtils.isNotEmpty(updateUserRequest.getNewEmailData())) {
            userToPatch.setEmails(updateUserRequest.getNewEmailData());
        }
        if (CollectionUtils.isNotEmpty(updateUserRequest.getNewPhoneData())) {
            userToPatch.setPhones(updateUserRequest.getNewPhoneData());
        }
        return userRepository.save(userToPatch);
    }

    public Page<User> search(SearchUserRequest searchUserRequest) {
        if (Objects.isNull(searchUserRequest.getDate())
            && Objects.isNull(searchUserRequest.getEmail())
            && Objects.isNull(searchUserRequest.getPhone())
            && Objects.isNull(searchUserRequest.getName())) {
            return userRepository.findAll(PageRequest.of(searchUserRequest.getPage(), searchUserRequest.getSize()));
        } else {
            Specification<User> spec = Specification.where(withDateOfBirth(searchUserRequest.getDate()))
                    .and(withName(searchUserRequest.getName()))
                    .and(withEmail(searchUserRequest.getEmail()))
                    .and(withPhone(searchUserRequest.getPhone()));

            return userRepository.findAll(spec, PageRequest.of(searchUserRequest.getPage(), searchUserRequest.getSize()));
        }
    }

    public static Specification<User> withDateOfBirth(LocalDate dateOfBirth) {
        return (root, query, cb) -> Objects.isNull(dateOfBirth) ? null : cb.greaterThan(root.get("dateOfBirth"), dateOfBirth);
    }

    public static Specification<User> withName(String name) {
        return (root, query, cb) -> Objects.isNull(name) ? null : cb.like(root.get("name"), name + "%");
    }

    public static Specification<User> withPhone(String phone) {
        return (root, query, cb) -> Objects.isNull(phone) ? null : cb.equal(root.join("phones").get("phone"), phone);
    }

    public static Specification<User> withEmail(String email) {
        return (root, query, cb) -> Objects.isNull(email) ? null : cb.equal(root.join("emails").get("email"), email);
    }
}
