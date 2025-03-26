package cn.teacy.common.doudian.spi;


/**
 * SPI 响应接口
 * 所有 SPI 响应类都需要实现该接口，以便于 SPI 框架进行统一处理
 *
 */
public interface ISpiResponse {

    Long getCode();

    String getMsg();

}
