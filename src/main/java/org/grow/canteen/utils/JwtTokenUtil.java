package org.grow.canteen.utils;


import org.grow.canteen.commons.exceptions.RestException;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007188L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 *60;

//    @Value("${jwt.secret}")
//    private String secret;
    private   final String secret="MWFoMEc3YmpqaHY=";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public  String getByKey(String token,String key){
     Claims allClaimsFromToken=getAllClaimsFromToken(token);
     return (String) allClaimsFromToken.get(key);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            throw new RestException("A001","Invalid token");
        }
    }
    private boolean isTokenExpired(String token)throws RestException{
        final Date expiration=getExpirationDateFromToken(token);
        return  expiration.before(new Date());
    }

    public String generateToken(Map<String, Object> claims){
        return doGenerateToken(claims,claims.get("userName").toString());
    }

    private String doGenerateToken(Map<String,Object> claims,String subject){
        System.out.println(claims);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public boolean validateToken(String token){
        return (!isTokenExpired(token));
    }
}
