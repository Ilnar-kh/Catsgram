package service;

import exception.ConditionsNotMetException;
import exception.DuplicatedDataException;
import exception.NotFoundException;
import model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User create(User user) {
        // проверяем выполнение необходимых условий
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }

        for (User existing : users.values()) {
            // сравниваем e-mail новой записи и каждой существующей
            if (user.getEmail().equals(existing.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }
        // формируем дополнительные данные
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        // сохраняем нового пользователя в памяти приложения
        users.put(user.getId(), user);
        return user;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public User update(User newUser) {
        // 1) Проверяем, что пришёл ID
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("ID должен быть указан");
        }
        // 2) Проверяем, что пользователь с таким ID есть
        User oldUser = users.get(newUser.getId());
        if (oldUser == null) {
            throw new NotFoundException("Пользователь с таким ID не найден");
        }

        // 3) Обновляем email только если он не null
        if (newUser.getEmail() != null) {
            // а) проверяем, что строка не пустая
            if (newUser.getEmail().isBlank()) {
                throw new ConditionsNotMetException("Имейл должен быть указан");
            }
            // б) проверяем дубликат у других пользователей
            for (User other : users.values()) {
                if (!other.getId().equals(newUser.getId())
                        && newUser.getEmail().equals(other.getEmail())) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
            }
            // в) если всё ок — ставим новый email
            oldUser.setEmail(newUser.getEmail());
        }

        // 4) Обновляем username только если он не null
        if (newUser.getUsername() != null) {
            oldUser.setUsername(newUser.getUsername());
        }

        // 5) Обновляем password только если он не null
        if (newUser.getPassword() != null) {
            oldUser.setPassword(newUser.getPassword());
        }

        // 6) Возвращаем обновлённый объект (в Map он уже изменён по ссылке)
        return oldUser;
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

}
