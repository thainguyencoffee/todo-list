package com.thainguyen.todolist.service;

import com.thainguyen.todolist.model.ToDo;
import com.thainguyen.todolist.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repo;

    public ToDoServiceImpl(ToDoRepository repo) {
        this.repo = repo;
    }

    @Override
    public ToDo createToDo(ToDo toDo) {
        toDo.setDone(false);
        return repo.save(toDo);
    }

    @Override
    public ToDo updateToDo(ToDo toDo) {
        return null;
    }

    @Override
    public List<ToDo> findAllToDoList() {
        return repo.findAll();
    }

    @Override
    public List<ToDo> findAllByDone(boolean done) {
        return repo.findAllByDone(done);
    }

    @Override
    public Optional<ToDo> findToDoById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteToDo(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void markedDone(Long id) {
        Optional<ToDo> toDoOpt = repo.findById(id);
        if (toDoOpt.isPresent()) {
            ToDo toDo = toDoOpt.get();
            toDo.setDone(true);
            repo.save(toDo);
        }
    }

    @Override
    public void markedNotDone(Long id) {
        Optional<ToDo> toDoOpt = repo.findById(id);
        if (toDoOpt.isPresent()) {
            ToDo toDo = toDoOpt.get();
            toDo.setDone(false);
            repo.save(toDo);
        }
    }
}


