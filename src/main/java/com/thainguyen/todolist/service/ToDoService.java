package com.thainguyen.todolist.service;

import com.thainguyen.todolist.model.ToDo;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface ToDoService {

    ToDo createToDo(ToDo toDo);
    ToDo updateToDo(ToDo toDo);
    List<ToDo> findAllToDoList();
    List<ToDo> findAllByDone(boolean done);
    Optional<ToDo> findToDoById(Long id);
    void deleteToDo(Long id);
    void markedDone(Long id);
    void markedNotDone(Long id);
}
