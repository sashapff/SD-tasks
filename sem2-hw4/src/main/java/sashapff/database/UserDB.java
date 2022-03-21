package sashapff.database;

import org.springframework.data.repository.CrudRepository;
import sashapff.utils.User;

public interface UserDB extends CrudRepository<User, Integer> {
}
