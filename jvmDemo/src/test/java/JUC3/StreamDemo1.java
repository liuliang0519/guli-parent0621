package JUC3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
class User{
   String name;
   Integer age;

}


public class StreamDemo1 {
    public static void main(String[] args) {

        User user = new User("lucy",12);
        User user1 = new User("tom",14);
        User user2 = new User("tony",17);

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user2);

        users.stream()
                .filter((p)->{ return p.getAge()%2 == 0; })
                .map((t)->{ return t.getName().toUpperCase(); })
                .limit(1)
                .forEach(System.out::println);


    }
}
