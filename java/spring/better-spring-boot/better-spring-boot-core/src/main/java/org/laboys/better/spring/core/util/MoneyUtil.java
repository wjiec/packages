package org.laboys.better.spring.core.util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MoneyUtil {

    private MoneyUtil() {}

    /**
     * 人民币
     */
    public static final CurrencyUnit CNY = CurrencyUnit.of("CNY");

    /**
     * 默认金额
     */
    public static final Money ZERO = Money.of(CNY, 0);

    /**
     * 人民币转换
     */
    public static Money of(@NotNull BigDecimal amount) {
        return Money.of(CNY, amount);
    }

    /**
     * 从字符串类型转换
     */
    public static Money of(String amount) {
        return of(new BigDecimal(amount));
    }

    /**
     * 从分构建金额
     */
    public static Money ofMinor(long amount) {
        return Money.ofMinor(CNY, amount);
    }

}
