package utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static String saveUploadFileLocally(MultipartFile uploadFile) throws IOException {
        File temp_file = new File(System.getenv("UPLOADED_FILE_DIR") + uploadFile.getOriginalFilename());
        uploadFile.transferTo(temp_file);

        return temp_file.getAbsolutePath();
    }
}
