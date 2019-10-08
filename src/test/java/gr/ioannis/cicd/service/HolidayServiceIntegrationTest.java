package gr.ioannis.cicd.service;

import static org.assertj.core.api.Assertions.assertThat;

import gr.ioannis.cicd.model.Holiday;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayServiceIntegrationTest {

  @Autowired
  private HolidayService holidayService;

  @Test
  public void getHolidaysForCountryTest() {
    List<Holiday> holidays = holidayService.getHolidaysForCountry("GR");
    assertThat(holidays).isNotNull().isNotEmpty();
  }

  @Test
  public void getHolidaysForCountryAndYearTest() {
    List<Holiday> holidays = holidayService.getHolidaysForCountryAndYear("GR", "2019");
    assertThat(holidays).isNotNull().isNotEmpty();
  }

}
