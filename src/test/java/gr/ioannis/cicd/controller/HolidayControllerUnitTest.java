package gr.ioannis.cicd.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.ioannis.cicd.model.Holiday;
import gr.ioannis.cicd.service.HolidayService;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayControllerUnitTest {

  private MockMvc mockMvc;

  @Autowired
  private HolidayController holidayController;

  @MockBean
  private HolidayService holidayService;

  private ObjectMapper objectMapper;

  private String mockedJson = "[{\"date\":\"2019-01-01\",\"localName\":\"Πρωτοχρονιά\",\"name\":\"New Year's Day\",\"countryCode\":\"GR\",\"fixed\":true,\"global\":true,\"counties\":null,\"launchYear\":null,\"type\":\"Public\"},{\"date\":\"2019-01-06\",\"localName\":\"Θεοφάνεια\",\"name\":\"Epiphany\",\"countryCode\":\"GR\",\"fixed\":true,\"global\":true,\"counties\":null,\"launchYear\":null,\"type\":\"Public\"},{\"date\":\"2019-03-11\",\"localName\":\"Καθαρά Δευτέρα\",\"name\":\"Clean Monday\",\"countryCode\":\"GR\",\"fixed\":false,\"global\":true,\"counties\":null,\"launchYear\":null,\"type\":\"Public\"}]";

  @Before
  public void init() {
    mockMvc = standaloneSetup(this.holidayController).build();
    objectMapper = new ObjectMapper();
    objectMapper
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void getCurrentHolidaysForCountryTest() throws Exception {
    List<Holiday> mockedHolidays = Arrays
        .asList(objectMapper.readValue(mockedJson, Holiday[].class));

    when(holidayService.getHolidaysForCountry("GR")).thenReturn(mockedHolidays);

    mockMvc.perform(get("/holidays/GR").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("New Year's Day")))
        .andExpect(jsonPath("$[1].name", is("Epiphany")))
        .andExpect(jsonPath("$[2].name", is("Clean Monday")));
  }

  @Test
  public void getCurrentHolidaysForCountryAndYearTest() throws Exception {
    List<Holiday> mockedHolidays = Arrays
        .asList(objectMapper.readValue(mockedJson, Holiday[].class));

    when(holidayService.getHolidaysForCountryAndYear("GR", "2019")).thenReturn(mockedHolidays);

    mockMvc.perform(get("/holidays/GR/2019").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("New Year's Day")))
        .andExpect(jsonPath("$[1].name", is("Epiphany")))
        .andExpect(jsonPath("$[2].name", is("Clean Monday")));
  }

}
