//package com.gdsc.jmt.domain.user.apple;
//
//import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
//import com.gdsc.jmt.domain.user.oauth.info.impl.AppleOAuth2UserInfo;
//import com.gdsc.jmt.global.exception.ApiException;
//import com.gdsc.jmt.global.messege.AuthMessage;
//import com.gdsc.jmt.global.messege.DefaultMessage;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.spec.RSAPublicKeySpec;
//import java.util.Base64;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AppleUtil {
//    private static AppleRestServerAPI appleRestServerAPI;
//
//    @Autowired
//    public AppleUtil(AppleRestServerAPI appleRestServerAPI) {
//        AppleUtil.appleRestServerAPI = appleRestServerAPI;
//    }
//
//    /**
//     * 1. apple로 부터 공개키 3개 가져옴
//     * 2. 내가 클라에서 가져온 token String과 비교해서 써야할 공개키 확인 (kid,alg 값 같은 것)
//     * 3. 그 공개키 재료들로 공개키 만들고, 이 공개키로 JWT토큰 부분의 바디 부분의 decode하면 유저 정보
//     */
//    public static OAuth2UserInfo getAppleUserInfo(String idToken) {
//        //클라이언트로부터 가져온 identity token String decode
//        String[] decodeArray = idToken.split("\\.");
//        String header = new String(Base64.getDecoder().decode(decodeArray[0]));
//
//        //apple에서 제공해주는 kid값과 일치하는지 알기 위해
//        String kid = ((JsonObject) JsonParser.parseString(header)).get("kid").getAsString();
//        String alg = ((JsonObject) JsonParser.parseString(header)).get("alg").getAsString();
//
//        try {
//            Call<Keys> call = appleRestServerAPI.sendAPI("application/json");
//            Response<Keys> response = call.execute();
//
//            Key key = findCorrectElement(response.body().getKeys(), kid, alg);
//            PublicKey publicKey = getPublicKey(key);
//
//            Claims userInfo = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(idToken).getBody();
//            JsonObject userInfoObject = (JsonObject) JsonParser.parseString(new Gson().toJson(userInfo));
//
//            String userId = userInfoObject.get("sub").getAsString();
//            String appleEmail = userInfoObject.get("email").getAsString();
//            return new AppleOAuth2UserInfo(userId, appleEmail);
//        }
//        catch (IOException e) {
//            throw new ApiException(DefaultMessage.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    private static Key findCorrectElement(List<Key> keys, String kid, String alg) {
//        for(Key key : keys) {
//            String appleKid = key.getKid();
//            String appleAlg = key.getAlg();
//            if(appleKid.equals(kid) && appleAlg.equals(alg)) {
//                return key;
//            }
//        }
//
//        throw new ApiException(AuthMessage.INVALID_TOKEN);
//    }
//
//    private static PublicKey getPublicKey(Key key) {
//        String nStr = key.getN();
//        String eStr = key.getE();
//
//        byte[] nBytes = Base64.getUrlDecoder().decode(nStr);
//        byte[] eBytes = Base64.getUrlDecoder().decode(eStr);
//
//        BigInteger n = new BigInteger(1, nBytes);
//        BigInteger e = new BigInteger(1, eBytes);
//
//        try {
//            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(publicKeySpec);
//        } catch (Exception exception) {
//            throw new ApiException(AuthMessage.INVALID_TOKEN);
//        }
//    }
//}
