package ru.itis.ivavprp.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.ivavprp.dto.SkillDto;
import ru.itis.ivavprp.dto.StudentDto;
import ru.itis.ivavprp.models.Role;
import ru.itis.ivavprp.models.Skill;
import ru.itis.ivavprp.models.Student;
import ru.itis.ivavprp.repositories.SkillsRepository;
import ru.itis.ivavprp.repositories.StudentRepository;
import ru.itis.ivavprp.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends UserService implements StudentService {

    private final StudentRepository studentRepository;
    private final SkillsRepository skillsRepository;
    private final UserRepository userRepository;


    public StudentServiceImpl(StudentRepository studentRepository,
                              SkillsRepository skillsRepository,
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.skillsRepository = skillsRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;

    public boolean save(StudentDto studentDto) {


        if (userRepository.findByEmail(studentDto.getEmail()).isPresent()) {
            return false;
        }
        Student student = new Student();
        student.setEmail(studentDto.getEmail());
        student.setRating(0);
        student.setIsActive(true);
        student.setRoles(Collections.singleton(Role.STUDENT));
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        studentRepository.save(student);
        return true;
    }

    @Override
    public List<StudentDto> findAll(Specification<Student> spec, int page, int size) {
        return studentRepository.findAll(spec, PageRequest.of(page, size)).stream()
                .map(Student::toStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SkillDto> addSkills(StudentDto studentDto, List<Long> skillIds) {
        List<SkillDto> allSkills = addOrRemoveSkills(studentDto, skillIds, 1);
        return allSkills;
    }

    @Override
    public List<SkillDto> removeSkills(StudentDto studentDto, List<Long> skillIds) {
        List<SkillDto> allSkills = addOrRemoveSkills(studentDto, skillIds, 0);
        return allSkills;
    }

    //action = 1 if add, 0 otherwise
    private List<SkillDto> addOrRemoveSkills(StudentDto studentDto, List<Long> skillIds, int action) {
        Optional<Student> optionalStudent = studentRepository.findById(studentDto.getId());
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            List<Skill> oldSkills = student.getAllSkills();
            for (Long id : skillIds) {
                Optional<Skill> optionalSkill = skillsRepository.findById(id);
                if (optionalSkill.isPresent()) {
                    Skill skill = optionalSkill.get();
                    if (action == 1) {
                        if (!oldSkills.contains(skill)) {
                            oldSkills.add(skill);
                        }
                    } else {
                        oldSkills.remove(skill);
                    }
                }
            }
            student.setAllSkills(oldSkills);
            studentRepository.save(student);
            return oldSkills.stream().map(Skill::toSkillDto).collect(Collectors.toList());
        }
        throw new EntityNotFoundException();
    }
}
