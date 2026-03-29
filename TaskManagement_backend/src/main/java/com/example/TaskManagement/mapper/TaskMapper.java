package com.example.TaskManagement.mapper;

import com.example.TaskManagement.dto.TaskDto;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.UserLogin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public Task mappedToTask(UserLogin owner, TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setCompleted(false);
        task.setOwner(owner);
        return task;
    }

    public TaskDto mappedToTaskDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDueDate(task.getDueDate());
        return taskDto;
    }

    public List<TaskDto> mappedToListTaskDto(List<Task> taskList) {
        return taskList.stream().map(this::mappedToTaskDto)
                .collect(Collectors.toList());
    }
}
