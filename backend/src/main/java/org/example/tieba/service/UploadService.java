package org.example.tieba.service;

import org.example.tieba.constants.ErrorCode;
import org.example.tieba.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    private final static String UPLOAD_DIR = "/home/yoshino/dev/java/tieba/backend/uploads";
    private final static int MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    public String image(MultipartFile file) {
        if(file == null || file.isEmpty())
            throw new BusinessException(ErrorCode.MISSING_REQUIRED_FIELD, "文件不能为空");

        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/"))
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, "图片格式错误");

        if(file.getSize() > MAX_IMAGE_SIZE)
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, "图片大小不能超过 5MB");

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + ".jpg";
        Path path = Paths.get(UPLOAD_DIR, fileName);

        try {
            file.transferTo(path.toFile());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "文件上传失败，请稍后重试");
        }
        return uuid.toString();
    }
}
