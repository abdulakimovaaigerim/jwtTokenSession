package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/{userId}/save")
    public SimpleResponse saveComment(@PathVariable Long productId, @PathVariable Long userId,
                                      @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.createComment(productId, userId, commentRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{productId}/{userId}/getAll")
    public List<CommentResponse> getAll(@PathVariable Long productId, @PathVariable Long userId) {
        return commentService.getAllComments(productId, userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public CommentResponse getById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.updateComment(id, commentRequest);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return commentService.deleteById(id);
    }

}
