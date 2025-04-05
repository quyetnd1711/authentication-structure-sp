package vn.eren.authenticationstructuresp.common.util;

public class SecurityUtil {

//    private static String getClaimByKey(String key) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            return jwt.getClaims().get(key).toString();
//        }
//        return null;
//    }

    public static String getCurrentAccountId() {
//        return Long.parseLong(Objects.requireNonNull(getClaimByKey("account_id")));
        return "663f343b-3eaa-4eba-aed8-6a03ea1f5613";
    }
}
