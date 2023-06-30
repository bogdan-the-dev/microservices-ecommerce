package ro.bogdansoftware.file;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class Base64Converter {
    public static MockMultipartFile convertToFile(String base64String, String fileName) {
        String[] parts = base64String.split(",");
        String metadata = parts[0];

        // Extract the file extension from the metadata
        String fileExtension = metadata.substring(metadata.indexOf("/") + 1, metadata.indexOf(";"));

        // Decode the Base64 data part
        byte[] fileData = Base64.getDecoder().decode(parts[1]);

        // Create the MockMultipartFile using the file data and extension

        return new MockMultipartFile("file", fileName + '.' + fileExtension,
                metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(";")), fileData);

    }

}
