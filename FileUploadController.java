package com.example.fileupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String index(Model model) {
        File folder = new File(uploadPath);
        if (folder.exists()) {
            model.addAttribute("files", folder.listFiles());
        }
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                File dest = new File(uploadPath + file.getOriginalFilename());
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "File upload failed!");
                return "index";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("filename") String filename) {
        try {
            Files.deleteIfExists(Paths.get(uploadPath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
