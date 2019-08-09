# helloSpringMVC

## Spring MVC Framework
- **Model** : POJO
```java
@Getter
@Setter
@NoArgsConstructor //인자가 없는 생성자
@ToString
public class Offer {

	private int id;
	private String name;
	private String email;
	private String text;
}
```

- **View** : HTML output
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	${offer.name}가 새로운 제안을 하였습니다. 감사합니다.<br/>
	<a href="${pageContext.request.contextPath}/offers"> Click here to view current offers</a>
	
</body>
</html>
```

- **Controller** : 모델을 다루고 적절한 View를 rendering 해줌  
```java
@Controller
public class OfferController {

	@Autowired // 의존 주입
	private OfferService offerService;

	@RequestMapping("/offers")
	public String showOffers(Model model) {
		List<Offer> offers = offerService.getCurrent(); // offers에 저장
		model.addAttribute("offers", offers); // model에 저장 // 이부분을 생략하면 offer객체를 view가 못 받아와서 오류가 뜬다

		return "offers";

	}

	@RequestMapping("/createoffer")
	public String createOffers(Model model) {

		model.addAttribute("offer", new Offer()); // offer를 생성 각필드 값은 초기화가 되있음

		return "createoffer";

	}

	@RequestMapping("/docreate")
	public String doCreate(Model model, @Valid Offer offer, BindingResult result) { // 데이터 binding한 내용을 검증 검증결과가 result에 들어간다
		if (result.hasErrors()) { // error가 있는지 check
			System.out.println("======form data does not validated");
			List<ObjectError> errors = result.getAllErrors(); // 모든에러
			for (ObjectError error : errors) { //
				System.out.println(error.getDefaultMessage());
			}
			return "createoffer"; // createoffer jsp로 넘어가짐
		}
		// System.out.println(offer); //offer에 tostring내용이넘어옴
		offerService.insert(offer);
		return "offercreated";

	}

}

```


![1](https://user-images.githubusercontent.com/32935365/62798081-5c14ff80-bb18-11e9-832e-3fed980b3fcd.PNG)

- **Dispatcher servlet**  
  -front controller의 역활로써 "/"이하의 모든 request를 처음 받는 주체
- **Handler Mapping**  
  -request URL과 controller class를 XML 설정과 annotation을 보고 mapping 시켜줌
- **Controller**  
  -request를 받아서 다른 business/service class를 호출하며 model object를 view로 보내줌
- **View Resolver**  
  -view의 logical name을 토대로 physical view file을 찾음
- **View**  
  -physical view file

## Required Configuration
- **Maven Configuration**  
  -pom.xml
- **Web deployment descriptor**  
  -web.xml
- **Spring MVC Configuration**  
  -root-context.xml  
  -servlet-context.xml  
  
### Maven Configuration  
- **pom.xml**
```xml
 <!-- application 식별자 역활-->
<groupId>kr.ac.hansung</groupId>
<artifactId>helloSpringMVC</artifactId>
<name>helloSpringMVC</name>
<packaging>war</packaging>
<version>1.0.0-BUILD-SNAPSHOT</version>
```

```xml
<!-- Spring -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>${org.springframework-version}</version>
  <exclusions>
    <!-- Exclude Commons Logging in favor of SLF4j -->
    <exclusion>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

```xml
<!-- Spring MVC -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-webmvc</artifactId>
  <version>${org.springframework-version}</version>
</dependency>
```

```xml
<!-- spring jdbc -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-jdbc</artifactId>
  <version>${org.springframework-version}</version>
</dependency>

<!-- commons-dbcp2 -->
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-dbcp2</artifactId>
  <version>2.1.1</version>
</dependency>

<!-- mysql-connector-java -->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.11</version>
</dependency>
```

### Web deployment descriptor
- **web.xml**

```xml
<!-- 공유되는 bean... contextloadlistener가 읽어서 container를 구성 -->
<context-param>
  <param-name>contextConfigLocation</param-name>
  <param-value> <!-- 각 xml 설정파일을 등록해야지만 사용 가능-->
  /WEB-INF/spring/appServlet/dao-context.xml
  /WEB-INF/spring/appServlet/service-context.xml
  /WEB-INF/spring/appServlet/security-context.xml
  </param-value>
</context-param>

<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<servlet>
  <servlet-name>appServlet</servlet-name>
  <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class> <!-- 1. DispatcherServlet 동작-->
  <init-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value> <!-- 2. servlet-context.xml이라는 설정파일 읽음-->
  </init-param>
  <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
  <servlet-name>appServlet</servlet-name>
  <url-pattern>/</url-pattern>
</servlet-mapping>
```

### **Spring MVC Configuration**
- **root-context.xml**
- **servlet-context.xml**
-Dispatcher Servlet이 읽어들이는 설정파일
```xml
<!-- DispatcherServlet Context: defines this servlet's request-processing infrastructure -->

<!-- annotation 기능 활성화 -->
<annotation-driven />

<!-- Handles HTTP GET requests for /resources/** by efficiently serving up static resources in the ${webappRoot}/resources directory -->
<resources mapping="/resources/**" location="/resources/" /> <!-- 정적인page정의 어차피 controller가 관여안하니깐 중요하지않다 -->

<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
<!-- View Resolver-->
<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
  <beans:property name="prefix" value="/WEB-INF/views/" />
  <beans:property name="suffix" value=".jsp" />
</beans:bean>

<!-- 패키지를 scan 해서 controller, component와 같은 것을 자동으로 bean으로 등록해줌-->
<context:component-scan base-package="kr.ac.hansung.controller" />
```
