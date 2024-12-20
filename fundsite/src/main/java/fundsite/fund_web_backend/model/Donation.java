package fundsite.fund_web_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 제목
    private String description; // 설명
    private String mainImage; // 주 이미지 URL
    private String subImage; // 부가 이미지 URL
    private Double goalAmount; // 목표 금액
    private Double collectedAmount; // 모인 금액

    // Getters and Setters
}
