package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CommentResponse;
import peaksoft.entiti.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new peaksoft.dto.response.CommentResponse(c.id, c.comment, c.createAt) FROM Comment c WHERE c.product.id = :productId AND c.user.id = :userId")
    List<CommentResponse> getAllComments(Long productId, Long userId);

    @Query("select new peaksoft.dto.response.CommentResponse(c.id, c.comment, c.createAt) from Comment c where c.id=:id")
    CommentResponse getCommentById(Long id);
}
