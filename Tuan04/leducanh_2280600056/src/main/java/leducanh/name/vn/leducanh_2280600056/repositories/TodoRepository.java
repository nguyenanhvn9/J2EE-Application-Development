package leducanh.name.vn.leducanh_2280600056.repositories;

import leducanh.name.vn.leducanh_2280600056.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompleted(boolean completed);
    List<Todo> findByTitleContainingIgnoreCase(String keyword);
}
