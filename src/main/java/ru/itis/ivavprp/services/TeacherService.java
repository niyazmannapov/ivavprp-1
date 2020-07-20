package ru.itis.ivavprp.services;

import ru.itis.ivavprp.dto.SkillDto;
import ru.itis.ivavprp.dto.TeacherDto;
import ru.itis.ivavprp.dto.TeacherInfoDto;

import java.util.List;

public interface TeacherService {
    boolean save(TeacherDto teacherDto);

    TeacherInfoDto findTeacherById(Long id);

    TeacherInfoDto update(Long id, TeacherInfoDto info);

    List<SkillDto> confirmSkills(TeacherDto teacherDto, Long studentId, List<Long> skillsIds);
}
