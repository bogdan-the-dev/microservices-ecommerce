package ro.bogdansoftware.file;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class FileUploadService {

    private final Storage storage;
    public FileUploadService() throws IOException {
        byte[] keyFileBytes = new FileInputStream(ResourceUtils.getFile("classpath:just-palace-389214-4e5e3520dc7f.json")).readAllBytes();
        String keyFileContent = new String(keyFileBytes);

        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(keyFileContent.getBytes())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));;


        this.storage = StorageOptions.newBuilder().setProjectId("just-palace-389214")
                .setCredentials(credentials).build().getService();

    }

    public List<String> uploadPhoto(List<String> photos, String id) throws IOException {
        List<String> links = new LinkedList<>();
        int i = 1;
        for(String photo: photos) {
            MockMultipartFile file = Base64Converter.convertToFile(photo, "photo-" + i);
            i++;
            String filename = file.getOriginalFilename();
            String bucketName = "product-photo-storage-bucket";
            String filePath = id + "/" + filename;

            BlobId blobId = BlobId.of(bucketName, filePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Blob blob = storage.create(
                    BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build(), file.getBytes()
            );
            links.add(blob.getMediaLink());
        }
        return links;
    }

    public void deletePhotos(List<String> urls, String folder) throws MalformedURLException {
        for(String mediaLink: urls) {
            String fileName = Paths.get(new URL(mediaLink).getPath()).getFileName().toString();
            BlobId blobId = BlobId.of("photo-storage-bucket", folder + "/" + fileName);
            if (!storage.delete(blobId)) {
                log.warn("File with name: " + fileName + ", and mediaLink: " + mediaLink + " couldn't be deleted");
            }
        }
    }

}
