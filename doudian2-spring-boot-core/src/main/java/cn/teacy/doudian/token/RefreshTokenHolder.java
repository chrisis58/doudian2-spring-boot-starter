package cn.teacy.doudian.token;

import cn.teacy.common.interfaces.TokenHolder;

public interface RefreshTokenHolder extends TokenHolder<String> {

    class DEFAULT implements RefreshTokenHolder {

        private static String refreshToken;

        @Override
        public String getToken() {
            return refreshToken;
        }

        @Override
        public void clear() {
            refreshToken = null;
        }

        @Override
        public void setToken(String token) {
            refreshToken = token;
        }
    }

}
