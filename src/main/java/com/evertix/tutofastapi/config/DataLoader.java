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
        this.registerTeacher();
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
    private void registerTeacher() {

        SignUpRequest userTeacher1 = new SignUpRequest("albert.teacher","password","albert@gmail.com","ROLE_TEACHER","Albert",
                "Mayta","09987745","999666555");

        SignUpRequest userTeacher2 = new SignUpRequest("roberto.teacher","password","roberto@gmail.com","ROLE_TEACHER","Roberto",
                "Villanueva","09822145","987456123");

        SignUpRequest userTeacher3 = new SignUpRequest("pilar.teacher","password","pilar@gmail.com","ROLE_TEACHER","Pilar",
                "Lopez","09422448","987456883");

        SignUpRequest userTeacher4 = new SignUpRequest("junnior.teacher","password","junnior@gmail.com","ROLE_TEACHER","Junnior",
                "Quispe","07422882","967896258");

        this.authenticationService.registerUser(userTeacher1);
        this.authenticationService.registerUser(userTeacher2);
        this.authenticationService.registerUser(userTeacher3);
        this.authenticationService.registerUser(userTeacher4);
    }
}
