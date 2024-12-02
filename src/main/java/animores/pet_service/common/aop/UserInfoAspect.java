package animores.pet_service.common.aop;

import animores.pet_service.account.service.AccountService;
import animores.pet_service.common.RequestConstants;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@RequiredArgsConstructor
@Component
public class UserInfoAspect {
    private final AccountService accountService;
    @Pointcut("@annotation(UserInfo) || @within(UserInfo)")
    public void callAt(){

    }

    @Before("callAt()")
    private void saveUserInfo() {
        RequestContextHolder.getRequestAttributes().setAttribute(
                RequestConstants.ACCOUNT_ATTRIBUTE,
                accountService.getAccount(),
                RequestAttributes.SCOPE_REQUEST);
    }
}
