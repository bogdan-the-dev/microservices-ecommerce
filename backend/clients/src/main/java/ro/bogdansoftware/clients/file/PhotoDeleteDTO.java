package ro.bogdansoftware.clients.file;

import java.util.List;

public record PhotoDeleteDTO(
        String folder,
        List<String> urls
) {
}
