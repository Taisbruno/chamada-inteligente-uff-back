package br.com.smartroll.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "roll")
public class RollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "initial_date", nullable = false)
    public String initialDate;
    @Column(name = "final_date", nullable = false)
    public String finalDate;
    @Column(name = "longitude", nullable = false)
    public String longitude;
    @Column(name = "latitude", nullable = false)
    public String latitude;

    // Construtor padrão
    public RollEntity() {}

    // Construtor com parâmetros
    public RollEntity(String initialDate, String finalDate, String longitude, String latitude) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
