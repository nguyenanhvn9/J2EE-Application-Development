package leducanh.name.vn.leducanh_2280600056.repositories;

import leducanh.name.vn.leducanh_2280600056.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
