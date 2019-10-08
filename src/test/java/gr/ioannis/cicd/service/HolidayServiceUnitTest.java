package gr.ioannis.cicd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.ioannis.cicd.model.Holiday;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayServiceUnitTest {

  private static final String HOLIDAYS_API = "https://date.nager.at/api/v2/publicholidays/";
  @Autowired
  private HolidayService holidayService;
  @MockBean
  private RestTemplate restTemplate;
  @Mock
  private ResponseEntity<List<Holiday>> mockedRestEntity;
  private String year;

  private ObjectMapper objectMapper;

  private String mockedJson = "[{\"date\":\"2019-01-01\",\"localName\":\"Πρωτοχρονιά\",\"name\":\"New Year's Day\",\"countryCode\":\"GR\",\"fixed\":true,\"global\":true,\"counties\":null,\"launchYear\":null,\"type\":\"Public\"},{\"date\":\"2019-01-06\",\"localName\":\"Θεοφάνεια\",\"name\":\"Epiphany\",\"countryCode\":\"GR\",\"fixed\":true,\"global\":true,\"counties\":null,\"launchYear\":null,\"type\":\"Public\"},{\"date\":\"2019-03-11\",\"localName\":\"Καθαρά Δευτέρα\",\"name\":\"Clean Monday\",\"countryCode\":\"GR\",\"fixed\":false,\"global\":true,\"counties\":null,\"launchYear\":null,\"type\":\"Public\"}]";

  @Before
  public void init() {
    year = String
        .valueOf(Calendar.getInstance().get(Calendar.YEAR));
    objectMapper = new ObjectMapper();
    objectMapper
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void getHolidaysForCountryTest() throws IOException {

    List<Holiday> mockedHolidays = Arrays
        .asList(objectMapper.readValue(mockedJson, Holiday[].class));

    when(restTemplate
        .exchange(HOLIDAYS_API + year + "/GR", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Holiday>>() {
            })).thenReturn(mockedRestEntity);
    when(mockedRestEntity.getBody()).thenReturn(mockedHolidays);

    List<Holiday> holidays = holidayService.getHolidaysForCountry("GR");
    assertThat(holidays).isNotNull().isNotEmpty();
  }

  @Test
  public void getHolidaysForCountryAndYearTest() throws IOException {

    String year = "2019";

    List<Holiday> mockedHolidays = Arrays
        .asList(objectMapper.readValue(mockedJson, Holiday[].class));

    when(restTemplate
        .exchange(HOLIDAYS_API + year + "/GR", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Holiday>>() {
            })).thenReturn(mockedRestEntity);
    when(mockedRestEntity.getBody()).thenReturn(mockedHolidays);

    List<Holiday> holidays = holidayService.getHolidaysForCountryAndYear("GR", year);
    assertThat(holidays).isNotNull().isNotEmpty();
  }

}
