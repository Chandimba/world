package ao.it.chandsoft.world.service;

import ao.it.chandsoft.world.domain.UserModel;
import ao.it.chandsoft.world.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UtilizadorRepository utilizadorRepository;

    public UserService(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }


    public List<UserModel> findAllUsers() {
        return utilizadorRepository.findAll();
    }
}
