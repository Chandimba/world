package ao.it.chandsoft.world.domain.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErroResponse {
    private int codigoHttp;
    private String titulo;
    private Object detelhes;
}