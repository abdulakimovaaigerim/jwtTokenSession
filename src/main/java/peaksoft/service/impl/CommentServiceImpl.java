package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entiti.Comment;
import peaksoft.entiti.Product;
import peaksoft.entiti.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CommentRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse createComment(Long productId, Long userId, CommentRequest commentRequest) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id: " + productId + " is not found!"));

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " is not found!"));

        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreateAt(commentRequest.createAt());
        product.addComment(comment);
        user.addComment(comment);
        comment.setUser(user);
        comment.setProduct(product);
        userRepository.save(user);
        productRepository.save(product);
        commentRepository.save(comment);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Comment with id: " + comment.getId() + " is saved!"))
                .build();
    }

    @Override
    public List<CommentResponse> getAllComments(Long productId, Long userId) {
        return commentRepository.getAllComments(productId, userId);
    }

    @Override
    public CommentResponse getCommentById(Long id) {
        return commentRepository.getCommentById(id);
    }

    @Override
    public SimpleResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id: " + id + " is not found"));

        comment.setComment(commentRequest.comment());
        comment.setCreateAt(commentRequest.createAt());
        commentRepository.save(comment);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Comment with id: " + id + " is updated!"))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id: " + id + " is not found!"));
        comment.setUser(null);
        comment.setProduct(null);
        commentRepository.delete(comment);


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Comment with id: " + id + " is deleted!"))
                .build();
    }
}
