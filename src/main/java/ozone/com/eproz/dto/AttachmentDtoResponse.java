package ozone.com.eproz.dto;

public record AttachmentDtoResponse(
        String fileName,
        String filePath,
        String contentType,
        long size
) {
}