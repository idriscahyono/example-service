package idriscahyono.exampleservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import idriscahyono.exampleservice.repository.DocumentRepository;
import idriscahyono.exampleservice.repository.UserProfileRepository;
import idriscahyono.exampleservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.support.TransactionOperations;

public class AppService {
    @Autowired
    protected TransactionOperations transactionOperations;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected DocumentRepository documentRepository;
    @Autowired
    protected UserProfileRepository userProfileRepository;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MinioServiceImpl minioService;
    @Value("${server.base_url}")
    protected String serviceBaseUrl;
    @Autowired
    protected ProfileService profileService;
}
