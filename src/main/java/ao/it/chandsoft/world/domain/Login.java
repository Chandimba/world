package ao.it.chandsoft.world.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class Login implements Serializable {

    private String username;
    private String password;

}
