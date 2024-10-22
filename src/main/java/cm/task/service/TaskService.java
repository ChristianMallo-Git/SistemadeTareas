package cm.task.service;

import cm.task.model.Task;
import cm.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task searchTaskByID(Integer idTask) {
        Task task = taskRepository.findById(idTask).orElse(null);
        return task;
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
