package com.gisdev.dea.util.general;

import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class FileUtil {

    public static boolean checkIfFileHasExtension(MultipartFile file, String... extensions) {
        String fileExtension = getExtension(file.getOriginalFilename());
        for (String extensionToCheck : extensions) {
            if (fileExtension != null && fileExtension.equalsIgnoreCase(extensionToCheck)) {
                return true;
            }
        }
        return false;
    }

  /*  public static boolean checkIfFileHasExtension(MultipartFile file, FileType... fileTypes) {
        String fileExtension = getExtension(file.getOriginalFilename());
        for (FileType fileType : fileTypes) {
            if (fileExtension != null && fileExtension.equalsIgnoreCase(fileType.extension())) {
                return true;
            }
        }
        return false;
    }*/

    private static String getExtension(String filename) {
        if (filename == null) {
            return null;
        } else {
            int index = indexOfExtension(filename);
            return index == -1 ? "" : filename.substring(index + 1);
        }
    }

    private static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }

    private static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }
    }

    public static HttpHeaders buildHttpHeaderForFile(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        return headers;
    }

    public static String encodeToString(byte[] byteData) {
        if (byteData != null) {
            return Base64.getEncoder().encodeToString(byteData);
        }
        return null;
    }

    public static byte[] decodeToBytes(String byteData) {
        if (byteData != null) {
            return Base64.getDecoder().decode(byteData);
        }
        return null;
    }

    public static boolean isFileNotEmpty(MultipartFile file) {
        return file != null && !file.isEmpty();
    }
}
