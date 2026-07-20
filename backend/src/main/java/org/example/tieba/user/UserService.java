package org.example.tieba.user;

import org.example.tieba.infra.security.SecurityUtil;
import org.example.tieba.upload.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final SecurityUtil securityUtil;
    private final UploadService uploadService;

    public UserService(UserMapper userMapper, SecurityUtil securityUtil, UploadService uploadService) {
        this.userMapper = userMapper;
        this.securityUtil = securityUtil;
        this.uploadService = uploadService;
    }

    public UserDetailResponse updateProfile(UpdateUserRequest req) {
        UserDetailResponse userDetailResponse = userMapper.selectDetailById(securityUtil.getCurrentUserId());
        if(req.getNickname() != null && !req.getNickname().isEmpty())
            userDetailResponse.setNickname(req.getNickname());
        if(req.getEmail() != null && !req.getEmail().isEmpty())
            userDetailResponse.setEmail(req.getEmail());
        userMapper.update(userDetailResponse);
        return userDetailResponse;
    }

    @Transactional
    public String updateAvatar(MultipartFile file) {
        String fileName = uploadService.image(file);
        userMapper.updateAvatar(fileName, securityUtil.getCurrentUserId());
        return fileName;
    }
}
