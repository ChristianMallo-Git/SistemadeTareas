package cm.task.service;

import cm.task.model.Task;

import java.util.List;

public interface ITaskService {

    public List<Task> listTask();

    public Task searchTaskByID (Integer task);

    public void saveTask (Task task);

    public void deleteTask (Task task);

}
