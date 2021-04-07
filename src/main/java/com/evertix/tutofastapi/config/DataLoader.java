package com.evertix.tutofastapi.config;

import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.Role;
import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.enums.ERole;
import com.evertix.tutofastapi.model.enums.EStatus;
import com.evertix.tutofastapi.repository.CourseRepository;
import com.evertix.tutofastapi.repository.RoleRepository;
import com.evertix.tutofastapi.repository.TutorshipRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.security.request.SignUpRequest;
import com.evertix.tutofastapi.service.AuthenticationService;
import com.evertix.tutofastapi.service.TutorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader {
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final TutorshipRepository tutorshipRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository, CourseRepository courseRepository,
                      AuthenticationService authenticationService, UserRepository userRepository,
                      TutorshipRepository tutorshipRepository){
        this.roleRepository=roleRepository;
        this.courseRepository=courseRepository;
        this.authenticationService=authenticationService;
        this.userRepository=userRepository;
        this.tutorshipRepository=tutorshipRepository;
        this.loadData();
    }

    private void loadData() {

        this.addRoles();
        this.addCourses();
        this.registerUserStudent();
        this.registerTeacher();
        //this.setTeacherCourses();
        this.addTutorships();

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

    private void addTutorships() {
        Optional<User> student1 = this.userRepository.findByUsername("jesus.student");
        Optional<Course> course1 = this.courseRepository.findByName("Historia Universal");


        Tutorship tutorship = new Tutorship(LocalDateTime.of(2021, Month.APRIL, 14, 18, 30),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30), EStatus.OPEN,"Segunda Guerra Mundial",student1.get(),course1.get());


        this.tutorshipRepository.save(tutorship);


        Optional<User> student2 = this.userRepository.findByUsername("maria.student");
        Optional<Course> course2 = this.courseRepository.findByName("Inglés");


        Tutorship tutorship2 = new Tutorship(LocalDateTime.of(2021, Month.APRIL, 15, 8, 0),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Advanced English Topics",student2.get(),course2.get());


        this.tutorshipRepository.save(tutorship2);


        Optional<User> student3 = this.userRepository.findByUsername("jesus.student");
        Optional<Course> course3 = this.courseRepository.findByName("Trigonometría");


        Tutorship tutorship3 = new Tutorship(LocalDateTime.of(2021, Month.APRIL, 14, 13, 0),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Triángulos Notables",student3.get(),course3.get());


        this.tutorshipRepository.save(tutorship3);


        Optional<User> student4 = this.userRepository.findByUsername("maria.student");
        //Optional<User> teacher4 = this.userRepository.findByUsername("junnior.teacher");
        Optional<Course> course4 = this.courseRepository.findByName("Biología");


        Tutorship tutorship4 = new Tutorship(LocalDateTime.of(2021, Month.APRIL, 15, 15, 00),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Sistema Respiratorio",student4.get(),course4.get());

        this.tutorshipRepository.save(tutorship4);


        Optional<User> student5 = this.userRepository.findByUsername("maria.student");
        //Optional<User> teacher4 = this.userRepository.findByUsername("junnior.teacher");
        Optional<Course> course5 = this.courseRepository.findByName("Biología");


        Tutorship tutorship5 = new Tutorship(LocalDateTime.of(2021, Month.APRIL, 15, 15, 00),
                LocalDateTime.of(2020, Month.NOVEMBER, 30, 20, 30),EStatus.OPEN,"Sistema cOLOS",student4.get(),course4.get());

        this.tutorshipRepository.save(tutorship5);
    }
}
