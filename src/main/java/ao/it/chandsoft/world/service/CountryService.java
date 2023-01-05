package ao.it.chandsoft.world.service;

import ao.it.chandsoft.world.domain.Country;
import ao.it.chandsoft.world.domain.Language;
import ao.it.chandsoft.world.exceptions.ResourceNotFoundException;
import ao.it.chandsoft.world.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    public Country findByCode(String code) {
        return countryRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with code " + code));
    }

    public List<Country> findAllCountry() {
        return countryRepository.findAll();
    }


    public List<Language> findAllCountryLanguages(String code) {
        List<Language> languages = countryRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with country code " + code))
                .getLanguages();

        return languages;
    }
}
