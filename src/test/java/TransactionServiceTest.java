import com.xmarketplace.Entity.Transactions;
import com.xmarketplace.Repository.TransactionsRepository;
import com.xmarketplace.service.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class TransactionServiceTest {

    @InjectMocks
    TransactionsService transactionService;

    @Mock
    TransactionsRepository transactionRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransactions() {
        Transactions transaction1 = new Transactions();
        Transactions transaction2 = new Transactions();
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));
        List<Transactions> transactions = transactionService.getAllTransactionDetails();
        assertEquals(2, transactions.size());
        verify(transactionRepository, times(2)).findAll();
    }
}