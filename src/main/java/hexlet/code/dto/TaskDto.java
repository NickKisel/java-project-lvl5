package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotBlank
    @Size(min = 3, max = 1000)
    private String name;

    private String description;

    private long executorId;

    @NotNull
    private long taskStatusId;

    private Set<Long> labelIds;
}
