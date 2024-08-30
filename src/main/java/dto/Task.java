package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

    private Integer taskID;
    private String taskTitle;
    private String taskDescription;
    private LocalDate date;
    private LocalTime time;

}
