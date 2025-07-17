package com.example.course;

import com.example.course.dto.CourseDTO;
import com.example.course.service.CourseService;
import com.example.course.vo.CourseVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CourseServiceTest {
    @Autowired
    CourseService service;

    @Test
    void create_ok(){
        CourseVO vo = service.create(new CourseDTO("t","d",null,60));
        assertNotNull(vo.getId());
    }
}
