package t1.code.T1.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import t1.code.T1.dto.InputDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @Operation(summary = "Calculate the frequency of occurrence of characters in a given string")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Number of each character in a line"),
            @ApiResponse(responseCode = "400", description = "Invalid input format")
    })
    @PostMapping("/api")
    public Map<Character, Integer> getFrequency(
            @Parameter(description = "Input string")
            @RequestBody @Valid InputDTO input) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : input.getText().toCharArray()) {
            int frequency = map.getOrDefault(ch, 0);
            map.put(ch, frequency + 1);
        }

        List<Integer> frequencies = new ArrayList<>(map.values());
        frequencies.sort(Comparator.reverseOrder());

        Map<Character, Integer> result = new LinkedHashMap<>();
        for (int frequency : frequencies) {
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                if (entry.getValue() == frequency) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }
}
