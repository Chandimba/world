package ao.it.chandsoft.world.controller;


import ao.it.chandsoft.world.domain.Country;
import ao.it.chandsoft.world.domain.Language;
import ao.it.chandsoft.world.exceptions.ResourceNotFoundException;
import ao.it.chandsoft.world.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService){
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> findAllCountry() {
        return countryService.findAllCountry();
    }

    @GetMapping("/{code}")
    public Country findByCode(@PathVariable("code") String code) {
        return countryService.findByCode(code);
    }

    @GetMapping("/{code}/languages")
    public List<Language> findAllCountryLanguages(@PathVariable("code") String code) {
        return countryService.findAllCountryLanguages(code);
    }

}
