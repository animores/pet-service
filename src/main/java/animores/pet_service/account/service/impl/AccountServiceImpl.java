package animores.pet_service.account.service.impl;

import animores.pet_service.account.entity.Account;
import animores.pet_service.account.service.AccountService;
import animores.pet_service.common.RequestConstants;
import animores.pet_service.common.Response;
import animores.pet_service.common.exception.CustomException;
import animores.pet_service.common.exception.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static animores.pet_service.common.RequestConstants.ACCOUNT_ID;
import static animores.pet_service.common.RequestConstants.ACCOUNT_ROLE;


@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private final String accountServiceUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DEFAULT_ACCOUNT_ID = "13";

    public AccountServiceImpl(@Value("${user-service.url}") String accountServiceUrl,
                              RestTemplate restTemplate) {
        this.accountServiceUrl = accountServiceUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Account getAccount() {
        log.info(accountServiceUrl+ "/api/v1/account");

        HttpHeaders headers = new HttpHeaders();
//        headers.add(ACCOUNT_ID, RequestContextHolder.getRequestAttributes().getAttribute(ACCOUNT_ID, RequestAttributes.SCOPE_REQUEST).toString());
        headers.add(ACCOUNT_ID, DEFAULT_ACCOUNT_ID);
        headers.add(ACCOUNT_ROLE, RequestContextHolder.getRequestAttributes().getAttribute(ACCOUNT_ROLE, RequestAttributes.SCOPE_REQUEST).toString());

        HttpEntity entity = new HttpEntity(headers);

        return objectMapper.convertValue( restTemplate.exchange(accountServiceUrl + "/api/v1/account", HttpMethod.GET, entity, Response.class).getBody().getData(), Account.class);
    }

    @Override
    public Account getAccountFromContext() {
        try {
            return (Account) RequestContextHolder.getRequestAttributes().getAttribute(
                    RequestConstants.ACCOUNT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        } catch (NullPointerException e) {
            throw new CustomException(ExceptionCode.INVALID_USER);
        }
    }
}
