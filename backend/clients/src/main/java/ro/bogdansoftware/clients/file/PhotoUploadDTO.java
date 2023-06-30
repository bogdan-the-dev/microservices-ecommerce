package ro.bogdansoftware.clients.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PhotoUploadDTO {
    private String id;
    private List<String> photos;
}
