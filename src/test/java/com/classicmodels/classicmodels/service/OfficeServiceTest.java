package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.dto.OfficeDTO;
import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.mapper.EntityMapper;
import com.classicmodels.classicmodels.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfficeServiceTest {

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private OfficeService officeService;

    @Test
    void getAllOffices_ShouldReturnList_WhenOfficesExist() {
        // ARRANGE
        Office office = new Office();
        OfficeDTO dto = new OfficeDTO();
        when(officeRepository.findAll()).thenReturn(List.of(office));
        when(mapper.toOfficeDTO(office)).thenReturn(dto);

        // ACT
        List<OfficeDTO> result = officeService.getAllOffices();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(officeRepository, times(1)).findAll();
    }

    @Test
    void getAllOffices_ShouldReturnEmptyList_WhenNoOfficesExist() {
        // ARRANGE
        when(officeRepository.findAll()).thenReturn(List.of());

        // ACT
        List<OfficeDTO> result = officeService.getAllOffices();

        // ASSERT
        assertTrue(result.isEmpty());
        verify(officeRepository, times(1)).findAll();
    }

    @Test
    void getOfficeByCode_ShouldReturnOffice_WhenFound() {
        // ARRANGE
        Office office = new Office();
        OfficeDTO dto = new OfficeDTO();
        when(officeRepository.findById("1")).thenReturn(Optional.of(office));
        when(mapper.toOfficeDTO(office)).thenReturn(dto);

        // ACT
        OfficeDTO result = officeService.getOfficeByCode("1");

        // ASSERT
        assertNotNull(result);
        verify(officeRepository, times(1)).findById("1");
    }

    @Test
    void getOfficeByCode_ShouldThrowException_WhenNotFound() {
        // ARRANGE
        when(officeRepository.findById("1")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> officeService.getOfficeByCode("1"));
        verify(officeRepository, times(1)).findById("1");
    }

    @Test
    void createOffice_ShouldSaveAndReturnOffice_WhenValid() {
        // ARRANGE
        Office office = new Office();
        office.setOfficeCode("1");
        OfficeDTO dto = new OfficeDTO();
        when(officeRepository.existsById("1")).thenReturn(false);
        when(officeRepository.save(office)).thenReturn(office);
        when(mapper.toOfficeDTO(office)).thenReturn(dto);

        // ACT
        OfficeDTO result = officeService.createOffice(office);

        // ASSERT
        assertNotNull(result);
        verify(officeRepository, times(1)).save(office);
    }
    
    @Test
    void updateOffice_ShouldUpdateAndReturnOffice_WhenFound() {
        // ARRANGE
        Office existing = new Office();
        Office updated = new Office();
        OfficeDTO dto = new OfficeDTO();
        when(officeRepository.findById("1")).thenReturn(Optional.of(existing));
        when(officeRepository.save(existing)).thenReturn(existing);
        when(mapper.toOfficeDTO(existing)).thenReturn(dto);

        // ACT
        OfficeDTO result = officeService.updateOffice("1", updated);

        // ASSERT
        assertNotNull(result);
        verify(officeRepository, times(1)).save(existing);
    }

    @Test
    void updateOffice_ShouldThrowException_WhenMissing() {
        // ARRANGE
        Office updated = new Office();
        when(officeRepository.findById("1")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> officeService.updateOffice("1", updated));
    }

    @Test
    void deleteOffice_ShouldDeleteSuccessfully_WhenFound() {
        // ARRANGE
        Office office = new Office();
        when(officeRepository.findById("1")).thenReturn(Optional.of(office));

        // ACT
        officeService.deleteOffice("1");

        // ASSERT
        verify(officeRepository, times(1)).delete(office);
    }

    @Test
    void deleteOffice_ShouldThrowException_WhenMissing() {
        // ARRANGE
        when(officeRepository.findById("1")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> officeService.deleteOffice("1"));
    }
}