package com.atguigu.commonutils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author
 */
public class JwtUtils {

    public static void main(String[] args) {
        //测试  根据id和昵称 得到加密的token
        String liuliang = getJwtToken("519", "liuliang");
//        System.out.println(liuliang);

        //根据token 得到用户id
        String id = getMemberIdByJwtToken(liuliang);
        System.out.println(id);
    }

    public static final long EXPIRE = 1000 * 60 * 60 * 24; //设置token有效时间  毫秒单位
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //加密秘钥


    //根据传入的参数  生成token字符串
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder() //构建
                //头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //主体
                .setSubject("guli-user")
                //设置过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //主体（key ：value）
                .claim("id", id)
                .claim("nickname", nickname)
                //签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            //通过 秘钥进行解码 查看token是否有效
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param jwtToken
     * @return
     */
    public static String getMemberIdByJwtToken(String jwtToken) {

        if(StringUtils.isEmpty(jwtToken)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

        Claims claims = claimsJws.getBody();//token字符串获取主体
        return (String)claims.get("id");
    }

//    /**
//     * 判断token是否存在与有效
//     * @param request
//     * @return
//     */
//    public static boolean checkToken(HttpServletRequest request) {
//        try {
//            String jwtToken = request.getHeader("token");
//            if(StringUtils.isEmpty(jwtToken)) return false;
//            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}