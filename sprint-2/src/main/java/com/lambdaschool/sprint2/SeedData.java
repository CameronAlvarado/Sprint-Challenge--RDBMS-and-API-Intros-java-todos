package com.lambdaschool.sprint2;

import com.lambdaschool.sprint2.models.*;
import com.lambdaschool.sprint2.services.RoleService;
import com.lambdaschool.sprint2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                r1));
        admins.add(new UserRoles(new User(),
                r2));
        admins.add(new UserRoles(new User(),
                r3));
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local",
                admins);
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@mymail.local"));
        u1.getTodos().add(new Todo("Finish java-orders-swagger", new Date(), u1));
        u1.getTodos().add(new Todo("Feed the turtles", new Date(), u1));
        u1.getTodos().add(new Todo("Complete the sprint challenge", new Date(), u1));

        userService.save(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                r3));
        datas.add(new UserRoles(new User(),
                r2));
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local",
                datas);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        u2.getTodos().add(new Todo("Walk the dogs", new Date(), u2));
        u2.getTodos().add(new Todo("provide feedback to my instructor", new Date(), u2));
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local",
                users);
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda",
                users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda",
                users);
        userService.save(u5);
    }
}
