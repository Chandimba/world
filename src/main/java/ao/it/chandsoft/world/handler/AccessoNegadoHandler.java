package ao.it.chandsoft.world.handler;

import ao.it.chandsoft.world.domain.error.ErroResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessoNegadoHandler  implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException, ServletException {

        ErroResponse error = ErroResponse.builder()
                .codigoHttp(HttpStatus.FORBIDDEN.value())
                .titulo("Access Denied")
                .detelhes(ex.getMessage())
                .build();

        response.getOutputStream()
                .write(new ObjectMapper().writeValueAsString(error).getBytes());
    }
}
