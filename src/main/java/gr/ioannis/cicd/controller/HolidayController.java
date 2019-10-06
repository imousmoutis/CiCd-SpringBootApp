package gr.ioannis.cicd.controller;

import gr.ioannis.cicd.service.HolidayService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import gr.ioannis.cicd.model.Holiday;

@RestController
@RequestMapping(value = "/holidays")
public class HolidayController {

  private HolidayService holidayService;

  @Autowired
  public HolidayController(HolidayService holidayService) {
    this.holidayService = holidayService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{countryName}")
  public List<Holiday> getCurrentHolidaysForCountry(
    @PathVariable(name = "countryName") String countryName) {
    return holidayService.getHolidaysForCountry(countryName);
  }

}
