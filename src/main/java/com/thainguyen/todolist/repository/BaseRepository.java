package com.thainguyen.todolist.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S save (S entity);

    List<T> findAll();

    Optional<T> findById(Long id);

    void deleteById(Long id);

    List<T> findAllByDone(boolean done);
}
