package feliperjj.rateio.service;

import feliperjj.rateio.domain.Expense;
import feliperjj.rateio.domain.Participant;
import feliperjj.rateio.domain.User;
import feliperjj.rateio.dto.SettlementResponseDTO;
import feliperjj.rateio.repository.EventRepository;
import feliperjj.rateio.repository.ExpenseRepository;
import feliperjj.rateio.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    // 1. Precisamos do EventRepository para a validação do existsById
    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private SettlementService settlementService;

    @Test
    void deveCalcularRateioCorretamente() {
        // --- 1. ARRANGE (Preparar o cenário) ---
        UUID eventId = UUID.randomUUID();

        // Criando os utilizadores COM IDs (Vital para o HashMap funcionar)
        User felipe = new User(); felipe.setId(UUID.randomUUID()); felipe.setName("Felipe");
        User jurema = new User(); jurema.setId(UUID.randomUUID()); jurema.setName("Jurema");
        User joao = new User(); joao.setId(UUID.randomUUID()); joao.setName("João");

        // Criando os participantes do evento
        Participant p1 = new Participant(); p1.setUser(felipe);
        Participant p2 = new Participant(); p2.setUser(jurema);
        Participant p3 = new Participant(); p3.setUser(joao);

        // Criando as despesas (Felipe pagou 90, Jurema pagou 30, João pagou 0)
        Expense exp1 = new Expense();
        exp1.setPayer(felipe);
        exp1.setAmount(new BigDecimal("90.00"));

        Expense exp2 = new Expense();
        exp2.setPayer(jurema);
        exp2.setAmount(new BigDecimal("30.00"));

        // Treinando os Mocks
        when(eventRepository.existsById(eventId)).thenReturn(true); // O evento existe!
        when(participantRepository.findByEventId(eventId)).thenReturn(List.of(p1, p2, p3));
        when(expenseRepository.findByEventId(eventId)).thenReturn(List.of(exp1, exp2));

        // --- 2. ACT (Ação) ---
        List<SettlementResponseDTO> settlements = settlementService.calculateSettlement(eventId);

        // --- 3. ASSERT (Verificação) ---
        // Se o total é 120 e são 3 pessoas, a média é 40.
        // Felipe (pagou 90, deve receber 50)
        // Jurema (pagou 30, deve pagar 10)
        // João (pagou 0, deve pagar 40)
        
        assertNotNull(settlements);
        assertEquals(2, settlements.size(), "Devem ser geradas exatamente 2 transferências (João -> Felipe e Jurema -> Felipe)");

        System.out.println("\n--- RESULTADO DO RATEIO (TESTE MOCKADO) ---");
        settlements.forEach(System.out::println);
        System.out.println("-------------------------------------------\n");
    }
}