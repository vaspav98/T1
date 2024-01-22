package t1.code.T1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputDTO {

    @NotNull
    private String text;

}
