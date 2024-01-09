package idriscahyono.exampleservice.controller;

import idriscahyono.exampleservice.service.DocumentService;
import idriscahyono.exampleservice.service.ProfileService;
import idriscahyono.exampleservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AppController {
    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;

    @Autowired
    DocumentService documentService;
}
