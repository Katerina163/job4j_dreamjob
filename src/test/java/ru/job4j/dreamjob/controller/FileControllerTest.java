package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class FileControllerTest {
    private FileService fileService;
    private MultipartFile testFile;
    private FileController fileController;

    @BeforeEach
    public void initService() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile(
                "testFile.img", new byte[] {1, 2, 3});
    }
    
    @Test
    public void whenCorrect() throws Exception {
        var file = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.getFileById(1)).thenReturn(Optional.of(file));
        var view = fileController.getById(1);
        assertThat(view).isEqualTo(ResponseEntity.ok(file.getContent()));
    }

    @Test
    public void whenEmpty() {
        when(fileService.getFileById(1)).thenReturn(Optional.empty());
        var view = fileController.getById(1);
        assertThat(view).isEqualTo(ResponseEntity.notFound().build());
    }
}