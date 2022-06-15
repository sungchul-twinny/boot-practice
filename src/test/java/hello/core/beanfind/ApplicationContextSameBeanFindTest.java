package hello.core.beanfind;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import java.util.*;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByType() {
        // NoUniqueBeanDefinitionException
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemoryMemberRepository.class));
    }

    @Test
    @DisplayName("빈 타입이 같으면 이름을 넣어주면 된다")
    void findBeanByName() {
        // NoUniqueBeanDefinitionException
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemoryMemberRepository.class);
        Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "  value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
    }

    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
