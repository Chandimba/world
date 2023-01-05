package ao.it.chandsoft.world.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Language implements Serializable {
    @JsonProperty(value = "language")
    @Column(name = "Language")
    private String language;
    @JsonProperty(value = "official")
    @Column(name = "IsOfficial")
    private String official;
    @JsonProperty(value = "percentage")
    @Column(name = "Percentage")
    private Double percentage;
}
