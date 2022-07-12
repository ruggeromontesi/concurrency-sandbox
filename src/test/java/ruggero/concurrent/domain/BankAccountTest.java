package ruggero.concurrent.domain;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

public class BankAccountTest {

    @Test
    public void shouldCreateBank() {
        Bank bank = new Bank(10);
        System.out.println(bank.getAccounts());
    }

    @Test
    public void shouldGetTwoDifferentUUIDs() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Bank bank = new Bank(10);
        Method method = Bank.class.getDeclaredMethod("getTwoDistinctUuids");

        method.setAccessible(true);
        List<UUID> uuids  = (List<UUID>) method.invoke(bank );
        System.out.println(uuids);
        Assert.assertTrue(uuids.stream().distinct().count() == 2L );
        Assert.assertEquals(2L, uuids.stream().distinct().count());
    }

    @Test
    public void shouldPerformRandomTransfer() {
        Bank bank = new Bank(10);
        bank.performRandomTransfer();
        System.out.println(bank.getAccounts());
    }


}
