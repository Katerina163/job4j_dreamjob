package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private UserController userController;
    private UserService userService;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRegisterSuccess() {
        User user = new User(1, "email", "name", "password");
        when(userService.save(user)).thenReturn(Optional.of(user));
        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenRegisterFailed() {
        User user = null;
        when(userService.save(user)).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        var message = model.getAttribute("message");
        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("Пользователь с такой почтой уже существует");
    }

    @Test
    public void whenLoginSuccess() {
        User user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("user", user);
        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        assertThat(view).isEqualTo("redirect:/vacancies");
    }

    @Test
    public void whenLoginFailed() {
        User user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword("", ""))
                .thenReturn(Optional.empty());
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("user", null);
        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        assertThat(view).isEqualTo("users/login");
    }
}