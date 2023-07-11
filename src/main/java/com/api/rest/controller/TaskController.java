package com.api.rest.controller;

import com.api.rest.domain.Task;
import com.api.rest.enums.TaskStatus;
import com.api.rest.model.TaskDto;
import com.api.rest.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity<Long> createTask(@RequestBody TaskDto taskDto) {
        Task task = new Task(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        taskRepository.save(task);
        return ResponseEntity.ok(task.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return ResponseEntity.ok(task.toDto());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());

            String status = taskDto.getStatus();
            if (TaskStatus.isValidStatus(status)) {
                task.setTaskStatus(TaskStatus.valueOf(status));
                taskRepository.save(task);
                return ResponseEntity.ok(task.toDto());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/describe/{id}")
    public ResponseEntity<String> describeTask(@PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return ResponseEntity.ok("Description of Task [" + task.getId() + ": " + task.toDto().getTitle() + "] is: " + task.toDto().getDescription());
        } else {
            return ResponseEntity.ok("Task with id = " + id + " does not exist");
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        Iterable<Task> tasks = taskRepository.findAll();
        List<TaskDto> listDto = new ArrayList<>();
        tasks.forEach(task -> listDto.add(task.toDto()));
        return ResponseEntity.ok(listDto);
    }

    @GetMapping("/describe")
    public ResponseEntity<List<String>> describeAllTasks() {
        Iterable<Task> tasks = taskRepository.findAll();
        List<String> descriptions = new ArrayList<>();
        tasks.forEach(task -> descriptions.add("Description of Task [" + task.getId() + ": " + task.toDto().getTitle() + "] is: " + task.toDto().getDescription()));
        return ResponseEntity.ok(descriptions);
    }
}
