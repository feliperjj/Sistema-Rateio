package feliperjj.rateio.service;

import feliperjj.rateio.domain.Event;
import feliperjj.rateio.repository.EventRepository;
import feliperjj.rateio.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    // 1. Criamos "dublês" falsos dos repositórios para não mexermos no banco de dados real
    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    // 2. Injetamos os dublês dentro do serviço real que queremos testar
    @InjectMocks
    private EventService eventService;

    // --- TESTE 1: REGRA DE NEGÓCIO DA EXCEÇÃO ---
    @Test
    void deveLancarExcecaoQuandoTentarFecharEventoJaFechado() {
        // Arrange (Preparar o cenário)
        UUID eventId = UUID.randomUUID();
        Event eventoFalso = new Event();
        eventoFalso.setId(eventId);
        eventoFalso.setClosed(true); // 👈 Atenção aqui: Simulamos que o evento JÁ ESTÁ fechado

        // Ensinamos o dublê: "Quando o service te pedir o ID, devolva o eventoFalso"
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventoFalso));

        // Act & Assert (Executar e Verificar)
        // Verificamos se o service vai atirar a exceção correta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.closeEvent(eventId); // Tentamos fechar!
        });

        // Verificamos se a mensagem de erro é exatamente a que programamos
        assertEquals("Este evento já foi fechado anteriormente.", exception.getMessage());
        
        // Verificamos que o sistema bloqueou a operação e NUNCA chamou o método .save()
        verify(eventRepository, never()).save(any(Event.class));
    }
    
    // --- TESTE 2: CAMINHO FELIZ ---
    @Test
    void deveFecharEventoComSucesso() {
        // Arrange (Preparar o cenário)
        UUID eventId = UUID.randomUUID();
        Event eventoFalso = new Event();
        eventoFalso.setId(eventId);
        eventoFalso.setClosed(false); // 👈 Evento aberto, pronto para ser fechado

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventoFalso));

        // Act (Ação)
        eventService.closeEvent(eventId);

        // Assert (Verificação)
        // Verificamos se o estado do evento mudou para true
        assertTrue(eventoFalso.isClosed());
        
        // Verificamos se o service mandou o repositório salvar exatamente 1 vez
        verify(eventRepository, times(1)).save(eventoFalso);
    }
}