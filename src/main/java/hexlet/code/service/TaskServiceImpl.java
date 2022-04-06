package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Task createTask(TaskDto taskDto) {
        final Task task = new Task();

        final User author = userService.getCurrentUser();
        final TaskStatus taskStatus = taskStatusRepository.getById(taskDto.getTaskStatusId());
        final List<Label> labels = labelRepository.findAllById(taskDto.getLabelIds());
        final User executor = userRepository.getById(taskDto.getExecutorId());

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setAuthor(author);
        task.setExecutor(executor);
        task.setLabels(labels);
        task.setTaskStatus(taskStatus);

        return taskRepository.save(task);
//        final Long executorId = taskData.getExecutorId();
//        if (executorId != null) {
//            task.setExecutor(userRepository.getById(executorId));
//        }
    }

    @Override
    public Task updateTask(long id, TaskDto taskDto) {
        final Task task = taskRepository.getById(id);

        final TaskStatus taskStatus = taskStatusRepository.getById(taskDto.getTaskStatusId());
        final List<Label> labels = labelRepository.findAllById(taskDto.getLabelIds());
        final User executor = userRepository.getById(taskDto.getExecutorId());

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setExecutor(executor);
        task.setLabels(labels);
        task.setTaskStatus(taskStatus);

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(long id) {
        final Task task = taskRepository.getById(id);
        taskRepository.delete(task);
    }
}
