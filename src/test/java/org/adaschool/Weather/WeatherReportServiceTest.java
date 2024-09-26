package org.adaschool.Weather;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class WeatherReportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWeatherReport_ValidResponse_ReturnsWeatherReport() {
        // Arrange
        double latitude = 37.8267;
        double longitude = -122.4233;

        // Crear el mock de WeatherApiResponse y Main
        WeatherApiResponse mockApiResponse = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();

        // Configurar valores para la simulación
        main.setTemperature(22.5);  // Valor esperado para la temperatura
        main.setHumidity(60);       // Valor esperado para la humedad
        mockApiResponse.setMain(main); // Establecer el objeto Main en el ApiResponse

        // Simular el comportamiento del RestTemplate
        when(restTemplate.getForObject(anyString(), eq(WeatherApiResponse.class))).thenReturn(mockApiResponse);

        // Act
        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        // Debug: imprimir los valores obtenidos para verificar
        System.out.println("Temperatura obtenida: " + report.getTemperature());
        System.out.println("Humedad obtenida: " + report.getHumidity());

        // Assert
        assertEquals(22.5, report.getTemperature(), 0.01);  // Verificar la temperatura con un delta (margen de error)
        assertEquals(60, report.getHumidity());              // Verificar la humedad
    }
}