package cn.teacy.doudian.client;

import cn.teacy.common.annotation.SaveInteractLog;
import cn.teacy.common.doudian.api.CommonResponse;
import cn.teacy.doudian.annotation.DoudianApiClient;
import cn.teacy.doudian.domain.request.CreateTokenParam;
import cn.teacy.doudian.domain.request.RefreshTokenParam;
import cn.teacy.doudian.domain.response.CreateTokenResponse;
import cn.teacy.doudian.domain.response.RefreshTokenResponse;
import org.springframework.web.bind.annotation.PostMapping;

@DoudianApiClient("common-apis")
public interface CommonClient {

    /**
     * 生成token API
     * 生成token API。创建token成功即可，可以定期调/token/refresh接口来刷新token避免过期，请不要频繁的调用！
     *
     * @see <a href="https://connect.douyinec.com/view/doc/-1000/-1000/-1000">生成token API</a>
     */
    @PostMapping("/token/create")
    @SaveInteractLog(true)
    CommonResponse<CreateTokenResponse> createToken(CreateTokenParam createTokenParam);


    /**
     * 使用refresh_token刷新access_token
     * 使用场景：
     * 1、在 access_token过期时，使用 refresh_token可以获取新的access_token 和 refresh_token；可以保证一直是有效的access_token。
     * 2、access_token有效期7天，refresh_token用于刷新access_token的刷新令牌，有效期：14 天；
     * 注意点：
     * 1.、在 access_token 过期前1h之前，服务商使用 refresh_token 刷新时，会返回原来的 access_token 和 refresh_token，但是二者有效期不会变；
     * 2、在 access_token 过期前1h之内，服务商使用 refresh_token 刷新时，会返回新的 access_token 和 refresh_token，但是原来的 access_token 和 refresh_token 继续有效一个小时；
     * 3、在 access_token 过期后，服务商使用 refresh_token 刷新时，将获得新的 access_token 和 refresh_token，同时原来的 access_token 和 refresh_token 失效；
     *
     * @see <a href="https://connect.douyinec.com/view/doc/-1000/-1000/-1001">使用refresh_token刷新access_token</a>
     */
    @PostMapping("/token/refresh")
    @SaveInteractLog(true)
    CommonResponse<RefreshTokenResponse> refreshToken(RefreshTokenParam refreshTokenParam);

}
