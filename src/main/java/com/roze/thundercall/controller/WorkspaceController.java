package com.roze.thundercall.controller;

import com.roze.thundercall.dto.OnboardingStep;
import com.roze.thundercall.dto.WorkspaceResponse;
import com.roze.thundercall.dto.WorkspaceSetupRequest;
import com.roze.thundercall.entity.User;
import com.roze.thundercall.repository.UserRepository;
import com.roze.thundercall.service.WorkspaceService;
import com.roze.thundercall.utils.ApiResponse;
import com.roze.thundercall.utils.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkspaceController extends BaseController {
    private final WorkspaceService workspaceService;
    private final UserRepository userRepository;

    @PostMapping("/setup")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> setupWorkspace(@Valid @RequestBody WorkspaceSetupRequest request, Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal()).getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        WorkspaceResponse response = workspaceService.setupInitialWorkspace(user, request);
        return created(response, "Workspace created successfully");
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<WorkspaceResponse>>> getUserWorkspaces(Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal()).getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<WorkspaceResponse> workspaceResponses = workspaceService.getUserWorkspaces(user);
        return ok(workspaceResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> getWorkspace(@PathVariable Long id, Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal()).getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        WorkspaceResponse response = workspaceService.getWorkspaceById(id, user);
        return ok(response);
    }

    @GetMapping("/tutorial/status")
    public ResponseEntity<ApiResponse<Boolean>> getTutorialStatus(Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal()).getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean completed = workspaceService.hasCompletedOnboarding(user);
        return ok(completed);
    }

    @PostMapping("/tutorial/complete")
    public ResponseEntity<ApiResponse<Void>> markTutorialComplete(@RequestParam String stepId, Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal()).getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        workspaceService.markTutorialComplete(user, stepId);
        return noContent("Tutorial step completed");
    }

    @GetMapping("/tutorial/steps")
    public ResponseEntity<ApiResponse<List<OnboardingStep>>> getOnboardingSteps(Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal()).getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<OnboardingStep> steps = workspaceService.getOnboardingSteps(user);
        return ok(steps);
    }

}
