import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/videos")
public class VideoStreamingController {

    @GetMapping("/stream/{videoId}")
    public ResponseEntity<StreamingResponseBody> streamVideo(@PathVariable String videoId) {
        // Define the path to the video file
        String videoPath = "path/to/your/videos/" + videoId + ".m3u8"; // Change extension if needed
        File videoFile = new File(videoPath);

        if (!videoFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Create the StreamingResponseBody to stream the video
        StreamingResponseBody responseBody = outputStream -> {
            try (InputStream inputStream = new FileInputStream(videoFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + videoId + ".m3u8\"")
                .body(responseBody);
    }
}
