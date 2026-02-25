package com.rayyan.library.repository;

import com.rayyan.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book,Long>,JpaSpecificationExecutor<Book> {

}
