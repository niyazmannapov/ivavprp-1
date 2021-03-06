package ru.itis.ivavprp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.ivavprp.dto.EmploymentType;
import ru.itis.ivavprp.dto.WorkSchedule;
import ru.itis.ivavprp.models.Skill;
import ru.itis.ivavprp.models.Student;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResumeDto {
    private Long id;
    private String name;
    private List<Skill> skills;
    private String text;
    private EmploymentType employmentType;
    private WorkSchedule workSchedule;
    private String workScheduleToShow;
    private String emplTypeToShow;
    private Long studentId;
}
