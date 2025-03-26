package cn.teacy.doudian.token;

import cn.teacy.common.interfaces.TokenHolder;

public interface AccessTokenHolder extends TokenHolder<String> {

    class DEFAULT implements AccessTokenHolder {

        private static String accessToken;

        @Override
        public String getToken() {
            return accessToken;
        }

        @Override
        public void clear() {
            accessToken = null;
        }

        @Override
        public void setToken(String token) {
            accessToken = token;
        }
    }

}
