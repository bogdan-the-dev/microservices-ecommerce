package ro.bogdansoftware.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.clients.file.PhotoDeleteDTO;
import ro.bogdansoftware.clients.file.PhotoUploadDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "photos/upload")
    public ResponseEntity<List<String>> uploadPhotos(@RequestBody PhotoUploadDTO dto) throws IOException {
        return ResponseEntity.ok(fileUploadService.uploadPhoto(dto.getPhotos(), dto.getId()));
    }

    @PutMapping("photos/delete")
    public ResponseEntity<Void> deletePhotos(@RequestBody PhotoDeleteDTO dto) throws MalformedURLException {
        fileUploadService.deletePhotos(dto.urls(), dto.folder());
        return ResponseEntity.ok().build();
    }

}
