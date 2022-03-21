package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);

        MemberService memberService = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", memberService.getClass());

        assertThatThrownBy(() -> {
            MemberServiceImpl memberServiceImpl = (MemberServiceImpl) memberService;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglibProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        MemberService memberService = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberService.getClass());
        MemberServiceImpl memberServiceImpl = (MemberServiceImpl) memberService;
    }

}
