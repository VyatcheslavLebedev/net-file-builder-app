package ru.impfields.netfilebuilderapp.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Constants {
     private double alpha;
     private double beta;
     private double sigma;
     private double delta;
}
