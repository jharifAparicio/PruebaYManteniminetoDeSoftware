package com.code.component.service;

import com.code.component.dao.ApplicationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    @Mock
    private ApplicationDao dao;

    @InjectMocks
    private ApplicationService service;

    private List<Double> grades;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        grades = Arrays.asList(90.0, 80.0, 70.0);
    }

    @Test
    void testAddGradeResultsForSingleClass() {
        when(dao.addGradeResultsForSingleClass(grades)).thenReturn(240.0);

        double result = service.addGradeResultsForSingleClass(grades);

        assertEquals(240.0, result);
        verify(dao, times(1)).addGradeResultsForSingleClass(grades);
    }

    @Test
    void testFindGradePointAverage() {
        when(dao.findGradePointAverage(grades)).thenReturn(80.0);

        double result = service.findGradePointAverage(grades);

        assertEquals(80.0, result);
        verify(dao).findGradePointAverage(grades);
    }

    @Test
    void testCheckNullNotNull() {
        Object obj = "Hello";
        when(dao.checkNull(obj)).thenReturn(obj);

        Object result = service.checkNull(obj);

        assertEquals("Hello", result);
        verify(dao).checkNull(obj);
    }

    @Test
    void testCheckNullNull() {
        when(dao.checkNull(null)).thenReturn(null);

        Object result = service.checkNull(null);

        assertEquals(null, result);
        verify(dao).checkNull(null);
    }
}
