package idriscahyono.exampleservice.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import idriscahyono.exampleservice.repository.DocumentRepository;
import idriscahyono.exampleservice.repository.UserProfileRepository;
import idriscahyono.exampleservice.repository.UserRepository;
import idriscahyono.exampleservice.service.MinioServiceImpl;
import idriscahyono.exampleservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionOperations;

public abstract class BaseService {
    @Autowired
    protected TransactionOperations transactionOperations;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DocumentRepository documentRepository;

    @Autowired
    protected UserProfileRepository userProfileRepository;

    @Autowired
    protected ProfileService profileService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MinioServiceImpl minioService;
}
