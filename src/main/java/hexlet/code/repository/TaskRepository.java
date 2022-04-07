package hexlet.code.repository;

import hexlet.code.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    void deleteById(long id);
    Optional<Task> findFirstByAuthorIdOrExecutorId(long authorId, long executorId);
    Optional<Task> findByTaskStatusId(long id);
}
