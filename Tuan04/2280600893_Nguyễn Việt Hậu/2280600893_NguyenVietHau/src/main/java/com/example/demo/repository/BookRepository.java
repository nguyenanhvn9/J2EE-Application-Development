package com.example.demo.repository;

import com.example.demo.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Books, UUID> {

    // Tìm tất cả sách
    List<Books> findAll();

    // Tìm sách theo ID (đã có sẵn trong JpaRepository nhưng có thể override)
    Optional<Books> findById(UUID id);

    // Tìm sách theo tên tác giả
    List<Books> findByAuthor(String author);

    // Tìm sách theo tiêu đề
    List<Books> findByTitle(String title);

    // Lưu hoặc cập nhật sách
    <S extends Books> S save(S book);

    // Xóa sách theo ID
    void deleteById(UUID id);

    // Kiểm tra tồn tại theo ID
    boolean existsById(UUID id);

    // Đếm tổng số bản ghi
    long count();
}
