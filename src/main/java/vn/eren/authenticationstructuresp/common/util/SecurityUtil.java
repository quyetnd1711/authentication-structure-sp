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

    public static Long getCurrentAccountId() {
//        return Long.parseLong(Objects.requireNonNull(getClaimByKey("account_id")));
        return 1L;
    }
}
