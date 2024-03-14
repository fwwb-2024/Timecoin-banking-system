package yswy.timesystem.backend.Util;

import java.security.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper; // 需要添加Jackson库依赖
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class TokenUtil {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private final ObjectMapper objectMapper = new ObjectMapper(); // 用于JSON序列化与反序列化

    public TokenUtil() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public String generateToken(String username) throws Exception {
        // 生成一个UUID作为token的一部分，确保每次生成的token都是唯一的
        String uuidPart = UUID.randomUUID().toString();

        // 创建token内容，包含username和过期时间
        Map<String, Object> tokenData = new java.util.HashMap<>();
        tokenData.put("username", username);
        tokenData.put("uuid", uuidPart);
        tokenData.put("exp", Instant.now().plusSeconds(5 * 60).getEpochSecond()); // 设置5分钟后过期

        // 将token内容转换为JSON字符串
        String jsonTokenData = objectMapper.writeValueAsString(tokenData);

        // 使用私钥签名token内容
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(jsonTokenData.getBytes("UTF-8"));
        byte[] signatureBytes = privateSignature.sign();

        // 编码token内容和签名，并拼接成最终的token
        String encodedTokenData = Base64.getEncoder().encodeToString(jsonTokenData.getBytes("UTF-8"));
        String encodedSignature = Base64.getEncoder().encodeToString(signatureBytes);
        return encodedTokenData + "." + encodedSignature;
    }

    public boolean verifyToken(String token) throws Exception {
        // 分割token内容和签名
        String[] parts = token.split("\\.");
        if (parts.length != 2) {
            return false;
        }

        // 解码token内容和签名
        byte[] decodedTokenData = Base64.getDecoder().decode(parts[0]);
        byte[] decodedSignature = Base64.getDecoder().decode(parts[1]);

        // 将token内容转换为JSON对象
        String jsonTokenData = new String(decodedTokenData, "UTF-8");
        Map<String, Object> tokenData = objectMapper.readValue(jsonTokenData, java.util.Map.class);

        // 检查token是否过期
        long expirationTime = (Long) tokenData.get("exp");
        if (Instant.now().getEpochSecond() > expirationTime) {
            return false;
        }

        // 使用公钥验证签名
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(decodedTokenData);
        return publicSignature.verify(decodedSignature);
    }


    public static String tokenServiceOne(String stringa) throws Exception {// 生成一个token,可以由账号、身份证号、电话生成
        TokenUtil tokenService = new TokenUtil();

        String token = tokenService.generateToken(stringa);
        return token;
    }

    public static void tokenServiceTwo(HttpServletRequest request, HttpServletResponse resp) throws Exception {// 验证token的有效性
        TokenUtil tokenService = new TokenUtil();

        String token = request.getHeader("Authorization");

        boolean isValid = tokenService.verifyToken(token);
        if(!isValid) {
            resp.setStatus(201);
        }
    }
}