//package idriscahyono.exampleservice.repository;
//
//import idriscahyono.exampleservice.entity.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Example;
//
//import java.util.List;
//
//@SpringBootTest
//class UserRepositoryTest {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void testCreateUser(){
//        User user = new User();
//        user.setUsername("DC3582");
//        user.setName("Idris Cahyono");
//        user.setEmail("idris@idriscahyono.com");
//        user.setPhone("+62895338083582");
//
//        userRepository.save(user);
//    }
//
//    @Test
//    void updateUser(){
//        User user = userRepository.findFirstByUsername("DC3582").orElse(null);
//        Assertions.assertNotNull(user);
//
//        user.setUsername("DC3583");
//        userRepository.save(user);
//
//        user = userRepository.findFirstByUsername("DC3583").orElse(null);
//        Assertions.assertNotNull(user);
//        Assertions.assertEquals("DC3583", user.getUsername());
//    }
//
//    @Test
//    void testGetUser(){
//        User user = new User();
//        user.setUsername("DC3583");
//
//        Example<User> example = Example.of(user);
//
//        List<User> users = userRepository.findAll(example);
//        Assertions.assertEquals(1, users.size());
//    }
//
//    @Test
//    void testDeleteUser(){
//        int deletUser = userRepository.deleteUserByUsername("DC3583");
//        Assertions.assertEquals(1, deletUser);
//    }
//}