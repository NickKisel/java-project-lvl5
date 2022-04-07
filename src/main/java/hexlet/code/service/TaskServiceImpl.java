package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task createTask(TaskDto taskDto) {
        final Task task = new Task();

        final User author = userService.getCurrentUser();
        final TaskStatus taskStatus = taskStatusRepository.getById(taskDto.getTaskStatusId());

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setAuthor(author);
        task.setTaskStatus(taskStatus);
        task.setLabels(taskDto.getLabels());
        final long executorId = taskDto.getExecutorId();
        if (executorId != 0) {
            task.setExecutor(userRepository.getById(executorId));
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(long id, TaskDto taskDto) {
        final Task task = taskRepository.getById(id);

        final TaskStatus taskStatus = taskStatusRepository.getById(taskDto.getTaskStatusId());

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setTaskStatus(taskStatus);
        final Long executorId = taskDto.getExecutorId();
        if (executorId != null) {
            task.setExecutor(userRepository.getById(executorId));
        }
        return taskRepository.save(task);
    }
}
