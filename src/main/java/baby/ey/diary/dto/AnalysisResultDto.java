package baby.ey.diary.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data

public class AnalysisResultDto {

    // Getters and Setters
    @JsonProperty("meal")
    private String meal;

    @JsonProperty("poo")
    private String poo;

    @JsonProperty("sleep")
    private String sleep;

    @JsonProperty("growth")
    private String growth;

    // Default constructor
    public AnalysisResultDto() {
    }

    // Parameterized constructor
    public AnalysisResultDto(String meal, String poo, String sleep, String growth) {
        this.meal = meal;
        this.poo = poo;
        this.sleep = sleep;
        this.growth = growth;
    }

    @Override
    public String toString() {
        return "AnalysisResultDto{" +
                "meal='" + meal + '\'' +
                ", poo='" + poo + '\'' +
                ", sleep='" + sleep + '\'' +
                ", growth='" + growth + '\'' +
                '}';
    }


}