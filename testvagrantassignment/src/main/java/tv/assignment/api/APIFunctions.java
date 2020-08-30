package tv.assignment.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class APIFunctions {

    private final RequestSpecBuilder specBuilder;
    private RequestSpecification requestSpecification;

    public APIFunctions(String baseURI, String basePath) {
        specBuilder = new RequestSpecBuilder().setBaseUri(baseURI).setBasePath(basePath);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public APIFunctions set_base_path(String base_path) {
        if (StringUtils.isNotBlank(base_path))
            specBuilder.setBasePath(base_path);
        else
            throw new RuntimeException("base path supplied is blank!");

        return this;
    }

    public APIFunctions set_contentType_and_body(final ContentType contentType, final String body) {
        return set_content_type(contentType).set_body(body);
    }

    public APIFunctions set_content_type(final String content_type) {
        specBuilder.setContentType(content_type);
        return this;
    }

    public APIFunctions set_content_type(final ContentType content_type) {
        return set_content_type(content_type.toString());
    }

    public APIFunctions set_body(final String body) {
        specBuilder.setBody(body);
        return this;
    }

    public RequestSpecification build_request_spec() {
        requestSpecification = specBuilder.build().given();
        return requestSpecification;
    }

    public APIFunctions set_path_params(final String param_name, final String param_value) {
        specBuilder.addPathParam(param_name, param_value);
        return this;
    }

    public APIFunctions set_query_params(final Map<String, ?> params) {
        specBuilder.addQueryParams(params);
        return this;
    }

    public APIFunctions set_cookie(Cookie cookie) {
        specBuilder.addCookie(cookie);
        return this;
    }

    public APIFunctions set_cookies(Cookies cookies) {
        specBuilder.addCookies(cookies);
        return this;
    }

    public Response get_response(final Method method, final String end_point) throws NullPointerException {
        Response response = null;
        if (requestSpecification == null && specBuilder != null) {
            requestSpecification = build_request_spec();
        }

        switch (method.toString()) {
            case "GET":
                response = RestAssured.given().spec(requestSpecification).when().get(end_point);
                break;
            case "POST":
                response = RestAssured.given().spec(requestSpecification).when().post(end_point);
                break;
            case "PUT":
                response = RestAssured.given().spec(requestSpecification).when().put(end_point);
                break;
            case "DELETE":
                response = RestAssured.given().spec(requestSpecification).when().delete(end_point);
                break;
        }

        return response;
    }
}
