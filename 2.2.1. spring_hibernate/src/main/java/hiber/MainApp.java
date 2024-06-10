package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);


        userService.addCar(new Car("Volga", 1973));
        userService.addCar(new Car("BMW", 2005));

        List<Car> cars = userService.listCars();

        userService.add(new User("Pavel", "Nosov", "nosov@mail.ru", cars.get(0)));
        userService.add(new User("Vladimir", "Morozov", "morozov@mail.ru", cars.get(1)));
        List<User> users = userService.listUsers();

        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getUserCar());
            System.out.println();
        }


        System.out.println("------------------------");


        User user = userService.findUserByCarModelAndSeries("Volga", 1973);

        // Проверяем результат
        if (user != null) {
            System.out.println("Найден пользователь: " + user.getFirstName() + " " + user.getLastName());
        } else {
            System.out.println("Пользователь не найден");
        }


        context.close();
    }
}
