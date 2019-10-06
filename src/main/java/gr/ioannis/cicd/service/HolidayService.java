package gr.ioannis.cicd.service;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import gr.ioannis.cicd.model.Holiday;

@Service
public class HolidayService {

  private static final String HOLIDAYS_API = "https://date.nager.at/api/v2/publicholidays/";
  private RestTemplate restTemplate;
  private String year;

  @Autowired
  public HolidayService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
  }

  public List<Holiday> getHolidaysForCountry(String countryName) {
    ResponseEntity<List<Holiday>> holidays = restTemplate.exchange(
      HOLIDAYS_API + year + "/" + countryName,
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<Holiday>>() {
      });

    return holidays.getBody();
  }

}
