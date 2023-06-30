package ro.bogdansoftware.clients.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@FeignClient(
        value = "file",
        path = "api/v1/file"
)
public interface IFileClient {
    @PostMapping(value = "photos/upload")
    ResponseEntity<List<String>> uploadPhotos(@ModelAttribute PhotoUploadDTO dto) throws IOException;

    @PutMapping("photos/delete")
    ResponseEntity<Void> deletePhotos(@RequestBody PhotoDeleteDTO dto) throws MalformedURLException;

}
