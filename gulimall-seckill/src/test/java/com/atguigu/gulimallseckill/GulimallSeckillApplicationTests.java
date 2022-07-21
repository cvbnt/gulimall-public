package com.atguigu.gulimallseckill;

import org.junit.Test;

import java.time.LocalDate;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class GulimallSeckillApplicationTests {

	@Test
	public void contextLoads() {
		LocalDate now = LocalDate.now();
		LocalDate plus = now.plusDays(1);
		LocalDate plus2 = now.plusDays(2);
		System.out.println(now);
		System.out.println(plus);
		System.out.println(plus2);
	}

}
