package gr.ioannis.cicd.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayControllerIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private HolidayController holidayController;

  @Before
  public void init() {
    mockMvc = standaloneSetup(this.holidayController).build();
  }

  @Test
  public void getCurrentHolidaysForCountryTest() throws Exception {
    mockMvc.perform(get("/holidays/GR").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("New Year's Day")))
        .andExpect(jsonPath("$[1].name", is("Epiphany")))
        .andExpect(jsonPath("$[2].name", is("Clean Monday")));
  }

  @Test
  public void getCurrentHolidaysForCountryAndYearTest() throws Exception {
    mockMvc.perform(get("/holidays/GR/2019").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("New Year's Day")))
        .andExpect(jsonPath("$[1].name", is("Epiphany")))
        .andExpect(jsonPath("$[2].name", is("Clean Monday")));
  }

}
