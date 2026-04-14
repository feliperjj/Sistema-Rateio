package feliperjj.rateio.dto;

import java.math.BigDecimal;

public record SettlementResponseDTO(
        String debtorName,   // Quem deve o dinheiro
        String creditorName, // Para quem o dinheiro deve ser pago
        BigDecimal amount    // O valor exato
) {
}