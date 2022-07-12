package ruggero.concurrent.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bank {

    private static final int AMOUNT_OF_BANK_ACCOUNTS = 10;

    private static final int INITIAL_AMOUNT = 1000;

    private final int CUMULATIVE_INITIAL_AMOUNT ;

    private Map<UUID,Integer> accounts = new HashMap<>();

    private List<UUID> uuids;

    private Random random = new Random();

    public Bank(int amountOfBankAccounts) {
        CUMULATIVE_INITIAL_AMOUNT = INITIAL_AMOUNT * amountOfBankAccounts;
        IntStream.range(0, amountOfBankAccounts).forEach( i -> accounts.put(UUID.randomUUID(),INITIAL_AMOUNT));
        uuids = new ArrayList<>(accounts.keySet());
    }

    public Map<UUID, Integer> getAccounts() {
        return accounts;
    }

    private UUID pickRandomExistingUuid() {

        return uuids.get(random.nextInt(accounts.size() - 1));
    }

    private List<UUID> getTwoDistinctUuids() {
        List<UUID> twoDistinctUuids = new ArrayList<>();
        UUID uuid1 = pickRandomExistingUuid();
        UUID uuid2;

        do {
            uuid2 = pickRandomExistingUuid();
        } while(uuid1.equals(uuid2));

        twoDistinctUuids.add(uuid1);
        twoDistinctUuids.add(uuid2);

        return twoDistinctUuids;
    }

    public void performRandomTransfer() {
        List<UUID> uuids = getTwoDistinctUuids();
        UUID uuidFrom = uuids.get(0);
        UUID uuidTo = uuids.get(1);



        int sumBeforeTransaction  = accounts.get(uuidFrom) + accounts.get(uuidTo);

        int amountToBeTransferred = random.nextInt(INITIAL_AMOUNT);

        System.out.println("performing transaction from account "  + uuidFrom + " to bank account " + uuidTo
                + "of an amount of " +  amountToBeTransferred);

        accounts.put(uuidFrom, accounts.get(uuidFrom) - amountToBeTransferred);

        accounts.put(uuidTo, accounts.get(uuidTo) + amountToBeTransferred);

        int sumAfterTransatction = accounts.get(uuidFrom) + accounts.get(uuidTo);

        if(sumAfterTransatction != sumBeforeTransaction) {
            throw new RuntimeException("Error in Transaction");
        }
    }

    public boolean isTotalAmountConsistent() {
        return getCurrentCumulativeAmount( ) == CUMULATIVE_INITIAL_AMOUNT ;
    }

    public int getCurrentCumulativeAmount( ){
        return accounts.values().stream().mapToInt(i -> i).sum();
    }

    public void validateIfTotalAmountIsConsistent() {
        if (!isTotalAmountConsistent()) {
            throw  new RuntimeException("Total amount not consistent");
        }
    }




}
