package com.evertix.tutofastapi.config;

import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.Role;
import com.evertix.tutofastapi.model.enums.ERole;
import com.evertix.tutofastapi.repository.CourseRepository;
import com.evertix.tutofastapi.repository.RoleRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.security.request.SignUpRequest;
import com.evertix.tutofastapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader {
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    //private final TutorshipRepository tutorshipRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository,CourseRepository courseRepository,AuthenticationService authenticationService,UserRepository userRepository){
            //,TutorshipRepository tutorshipRepository){
        this.roleRepository=roleRepository;
        this.courseRepository=courseRepository;
        this.authenticationService=authenticationService;
        this.userRepository=userRepository;
        //this.tutorshipRepository=tutorshipRepository;
        this.loadData();
    }

    private void loadData() {

        this.addRoles();
        this.addCourses();
        this.registerUserStudent();
        //this.registerTeacher();
        //this.setTeacherCourses();
        //this.addTutorships();

    }

    private void addCourses() {
        this.courseRepository.saveAll(Arrays.asList(
                new Course("Francés", "Descripción de Francés avanzado"),
                new Course("Inglés","Descripción de Inglés"),
                new Course("Alemán","Descripción de Alemán"),
                new Course("Portugués","Descripción de Portugués"),
                new Course("Aritmética", "Descripción de Aritmética"),
                new Course("Geometría", "Descripción de Geometría"),
                new Course("Álgebra", "Descripción de Álgebra"),
                new Course("Trigonometría", "Descripción de Trigonometría"),
                new Course("Geografía", "Descripción de Geografía"),
                new Course("Historia Universal", "Descripción de Historia Universal"),
                new Course("Historia del Perú", "Descripción de Historia del Peru"),
                new Course("Química", "Descripción de Química"),
                new Course("Física", "Descripción de Física"),
                new Course("Biología", "Descripción de Biología")
        ));
    }

    private void addRoles() {
        //User Roles
        this.roleRepository.saveAll(Arrays.asList(
                new Role(ERole.ROLE_STUDENT),
                new Role(ERole.ROLE_TEACHER),
                new Role(ERole.ROLE_ADMIN)
        ));
    }

    private void registerUserStudent() {

        SignUpRequest userStudent1 = new SignUpRequest("jesus.student","password","jesus@gmail.com","ROLE_STUDENT","Jesus",
                "Duran","77332215","994093796");

        SignUpRequest userStudent2 = new SignUpRequest("maria.student","password","maria@gmail.com","ROLE_STUDENT","Maria",
                "Suarez","88552215","986578231");

        this.authenticationService.registerUser(userStudent1);
        this.authenticationService.registerUser(userStudent2);
    }
}
