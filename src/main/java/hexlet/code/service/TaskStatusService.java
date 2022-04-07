package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;

public interface TaskStatusService {
    TaskStatus createStatus(TaskStatusDto taskStatusDto);
    TaskStatus updateTaskStatus(long id, TaskStatusDto taskStatusDto);
    void deleteTaskStatus(long id);
}
