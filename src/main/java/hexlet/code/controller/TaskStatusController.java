package hexlet.code.controller;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.TaskStatusServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static hexlet.code.controller.TaskStatusController.TASK_STATUS_PATH;
import static hexlet.code.controller.UserController.ID;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("${base-url}" + TASK_STATUS_PATH)
@AllArgsConstructor
public class TaskStatusController {

    public static final String TASK_STATUS_PATH = "/statuses";

    @Autowired
    private TaskStatusServiceImpl taskStatusService;

    @Autowired
    private TaskStatusRepository statusRepository;

    @GetMapping(ID)
    public TaskStatus getTaskStatus(@PathVariable long id) {
        return statusRepository.getById(id);
    }

    @PutMapping(ID)
    @PreAuthorize("hasAuthority('USER')")
    public TaskStatus updateTaskStatus(
            @PathVariable long id,
            @RequestBody TaskStatusDto taskStatusDto
    ) {
        return taskStatusService.updateTaskStatus(id, taskStatusDto);
    }

    @DeleteMapping(ID)
    @PreAuthorize("hasAuthority('USER')")
    public void deleteTaskStatus(@PathVariable long id) {
        final TaskStatus taskStatus = statusRepository.getById(id);
        statusRepository.delete(taskStatus);
    }

    @GetMapping
    public List<TaskStatus> getStatuses() {
        return statusRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public TaskStatus createTaskStatus(@RequestBody TaskStatusDto taskStatusDto) {
        return taskStatusService.createStatus(taskStatusDto);
    }
}
