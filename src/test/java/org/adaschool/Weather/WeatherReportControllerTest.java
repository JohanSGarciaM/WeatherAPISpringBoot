package org.adaschool.Weather;

import org.adaschool.Weather.controller.WeatherReportController;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherReportService weatherReportService;

    @InjectMocks
    private WeatherReportController weatherReportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherReportController).build();
    }

    @Test
    void getWeatherReport_ValidRequest_ReturnsWeatherReport() throws Exception {
        // Arrange
        double latitude = 37.8267;
        double longitude = -122.4233;
        WeatherReport mockWeatherReport = new WeatherReport(); // Crea un objeto mock de WeatherReport
        mockWeatherReport.setTemperature(22.5); // Agrega los valores necesarios al mock

        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(mockWeatherReport);

        // Act & Assert
        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'temperature': 22.5}")); // Compara los valores que esperas
    }

    @Test
    void getWeatherReport_NoData_ReturnsEmptyResponse() throws Exception {
        // Arrange
        double latitude = 37.8267;
        double longitude = -122.4233;

        // Simulamos que el servicio devuelve null
        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // El estado HTTP será 200 OK, ya que el controlador no maneja el caso de null
                .andExpect(content().string("")); // El cuerpo de la respuesta debe estar vacío
    }
}
