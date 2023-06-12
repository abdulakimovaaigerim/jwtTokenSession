package peaksoft.service;

import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CommentService {

    SimpleResponse createComment(Long productId, Long userId, CommentRequest commentRequest);

    List<CommentResponse> getAllComments(Long productId, Long userId);

    CommentResponse getCommentById(Long id);

    SimpleResponse updateComment(Long id, CommentRequest commentRequest);

    SimpleResponse deleteById(Long id);
}
