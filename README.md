# doudian2-spring-boot-starter

## 项目简介

doudian2-spring-boot-starter 是一个专为 [抖店开放平台](https://op.jinritemai.com/docs/guide-docs/213/14) 设计的 Spring Boot SDK，旨在简化与抖店 API 的集成。

本项目基于 Spring Boot 3.4.3 和 Spring Cloud 2024.0.1 开发，并采用 OpenFeign 作为 HTTP 客户端。开发者可以直接使用预设的 API 模块，或注册自定义 API 以满足个性化需求。此外，starter 提供了一种便捷的方式，将传统 REST 接口转换为 SPI 接口，并支持内置拦截器以增强访问安全性。

## 主要特性

- **开源且可定制**：支持代码修改与扩展，适用于多种业务场景。
- **高度解耦**：可无缝集成至现有 Spring Boot 应用。
- **简化 Token 认证管理**：内置 Token 管理机制，使业务代码更加简洁。

## 快速开始

### 先决条件

- JDK 17 及以上
- Maven

### 克隆并构建项目

```bash
git clone https://github.com/chrisis58/doudian2-spring-boot.git
cd doudian2-spring-boot
mvn clean install
```

### 添加依赖

在 Spring Boot 3 项目中引入以下依赖：

```xml
<dependency>
    <groupId>cn.teacy</groupId>
    <artifactId>doudian2-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version> <!-- 请根据实际版本替换 -->
</dependency>
```

### 配置应用参数

在 `application.yml` 或 `application.properties` 中添加以下配置项：

```yaml
doudian:
  app-key: ${APP_KEY:}
  app-secret: ${APP_SECRET:}
  additional-packages: com.example.demo

  # 获取 Token 的参数配置，具体的配置请参考：https://op.jinritemai.com/docs/api-docs/162/1600
  token-retrieve:
    code: ${CODE:}
    grant-type: AUTHORIZATION_SELF #  AUTHORIZATION_CODE
    testShop: ${TEST_SHOP:}
    shopId: ${SHOP_ID:}
    auth-id: ${AUTH_ID:}
    auth-subject-type: WU_LIU_SHANG # | YUN_CANG | etc.

  api:
    # 添加自定义的请求头
    #
    # 支持模板替换，`date` 在添加时会被替换为预设的值
    # supplierRegistry.register("date", () -> DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
    # 支持注册 requestHeadersSupplierRegistry Bean 来自定义模板
    request-headers: X-USE-PPE:1,X-TT-ENV:ppe_second_hand_luxury,Origin-From:djt_prod_`date`
```

## 使用示例

### 调用抖店 API

```java
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private LuxuryInspectionClient luxuryInspectionClient;

	@Test
	void test_client_secondLuxury_getInspectionTemplate() {
		CommonResponse<GetInspectionTemplateResponse> inspectionTemplate = luxuryInspectionClient.getInspectionTemplate(
				new GetInspectionTemplateParam("test1236")
		);

		System.out.println(inspectionTemplate);
	}
}
```

> 案例使用到了预设的包 `doudian2-second-hand-luxury-inspection`

### 注册 SPI（Service Provider Interface）

#### 批量注册 SPI

```java
@SpiEndpoint // 该 Controller 下所有接口均注册为 SPI
@RestController
@RequestMapping("/demo")
public class Demo2Controller {

    @GetMapping("/spiLog2")
    public LogisticWmsCrossDockingCancelResponse spiDemo2(LogisticsWmsCrossDockingCancelParam param) {
        SpiContextHolder.setLogId("spiLog2");
        return new LogisticWmsCrossDockingCancelResponse(0L, "success", "01", "CD000001", "384290257", "无");
    }

    @GetMapping("/spiLog3")
    public LogisticWmsCrossDockingCancelResponse spiDemo3(LogisticsWmsCrossDockingCancelParam param) {
        SpiContextHolder.setLogId("spiLog3");
        return new LogisticWmsCrossDockingCancelResponse(0L, "success", "01", "CD000001", "384290257", "无");
    }
}
```

#### 单独注册 SPI

```java
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/spiNoLog")
    @SpiEndpoint(saveLog = false) // 仅该接口注册为 SPI，且不保存日志
    public LogisticWmsCrossDockingCancelResponse spiDemo(LogisticsWmsCrossDockingCancelParam param) {
        SpiContextHolder.setLogId("spiNoLog");
        return new LogisticWmsCrossDockingCancelResponse(0L, "success", "01", "CD000001", "384290257", "无");
    }

    @GetMapping("/spiLog")
    @SpiEndpoint // 该接口注册为 SPI
    public LogisticWmsCrossDockingCancelResponse spiDemo2(LogisticsWmsCrossDockingCancelParam param) {
        SpiContextHolder.setLogId("spiLog");
        return new LogisticWmsCrossDockingCancelResponse(0L, "success", "01", "CD000001", "384290257", "无");
    }
}
```

如果配置正确，应用启动时应能看到类似的日志输出：

```
SpiServiceRegistry  : 4 SPI endpoints registered: [/demo/spiLog2, /demo/spiLog3, /demo/spiNoLog, /demo/spiLog]
SpiServiceRegistry  : 1 response class registered: [class cn.teacy.doudian.domain.spi.response.LogisticWmsCrossDockingCancelResponse]
```

## 许可证

本项目由 [chrisis58](https://github.com/chrisis58) 开发，依据 [Apache License 2.0](https://chatgpt.com/c/LICENSE) 开源发布。