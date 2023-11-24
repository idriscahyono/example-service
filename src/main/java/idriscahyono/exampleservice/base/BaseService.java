package idriscahyono.exampleservice.base;

import idriscahyono.exampleservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected UserRepository userRepository;

}
