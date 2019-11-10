package sk.tuke.SensorWebApi.server.security.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable
{
    private static final long serialVersionUID = 44154314231434L;
    private final String JWT;

    public JwtResponse(String JWT) {
        this.JWT = JWT;
    }

    public String getJWT() {
        return JWT;
    }
}
